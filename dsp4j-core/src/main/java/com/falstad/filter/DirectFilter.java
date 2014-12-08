package com.falstad.filter;

import java.util.Arrays;

public class DirectFilter extends Filter {

    private double a[];
    private double b[];
    private int n[];
    private Complex czn;
    private Complex top;
    private Complex bottom;

    public DirectFilter() {
        createCoeffArrays(1, 0, 1);
    }

    @Override
    public int getLength() {
        return a.length;
    }

    @Override
    public boolean useConvolve() {
        return b.length == 0 && a.length > 25;
    }

    void dump() {
        System.out.print("a ");
        dump(a);
        if (b.length != 0) {
            System.out.print("b ");
            dump(b);
        }
    }

    void dump(double x[]) {
        int i;
        for (i = 0; i != x.length; i++) {
            System.out.print(x[i] + " ");
        }
        System.out.println("");
    }

    @Override
    public void evalTransfer(Complex c) {
        if (czn == null) {
            czn = new Complex();
            top = new Complex();
            bottom = new Complex();
        }
        czn.set(1);
        top.set(0);
        bottom.set(0);
        int n = 0;
        for (int i = 0; i != a.length; i++) {
            int n1 = this.n[i];
            while (n < n1) {
                if (n + 3 < n1) {
                    czn.set(c);
                    czn.pow(-n1);
                    n = n1;
                    break;
                }
                czn.div(c);
                n++;
            }
            top.addMult(a[i], czn);
            if (b.length != 0) {
                bottom.addMult(b[i], czn);
            }
        }
        if (b.length != 0) {
            top.div(bottom);
        }
        c.set(top);
    }

    @Override
    public void run(double[] inBuf, double[] outBuf, int bp, int mask, int count) {

        for (int i = 0; i < count; i++) {
            int fi2 = bp + i;
            int i20 = fi2 & mask;

            double q = inBuf[i20] * a[0];
            if (b.length == 0) {
                for (int j = 1; j < a.length; j++) {
                    int ji = (fi2 - n[j]) & mask;
                    q += inBuf[ji] * a[j];
                }
            } else {
                for (int j = 1; j < a.length; j++) {
                    int ji = (fi2 - n[j]) & mask;
                    q += inBuf[ji] * a[j]
                            - outBuf[ji] * b[j];
                }
            }
            outBuf[i20] = q;
        }
    }

    boolean isSimpleAList() {
        if (b.length != 0) {
            return false;
        }
        return n[n.length - 1] == n.length - 1;
    }

    @Override
    public int getImpulseOffset() {
        if (isSimpleAList()) {
            return 0;
        }
        return getStepOffset();
    }

    @Override
    public int getStepOffset() {
        int i;
        int offset = 0;
        for (i = 0; i != a.length; i++) {
            if (n[i] > offset) {
                offset = n[i];
            }
        }
        return offset;
    }

    @Override
    public double[] getImpulseResponse(int offset) {
        if (isSimpleAList()) {
            return a;
        }
        return super.getImpulseResponse(offset);
    }

    @Override
    public int getImpulseLen(int offset, double buf[]) {
        if (isSimpleAList()) {
            return a.length;
        }
        return countPoints(buf, offset);
    }

    public int aLength() {
        return a.length;
    }

    public double getA(int i) {
        return a[i];
    }

    public void setA(int i, double value) {
        a[i] = value;
    }

    public int bLength() {
        return b.length;
    }

    public double getB(int i) {
        return b[i];
    }

    public void setB(int i, double value) {
        b[i] = value;
    }

    public int nLength() {
        return n.length;
    }

    public int getN(int i) {
        return n[i];
    }

    public void setN(int i, int value) {
        n[i] = value;
    }

    public void createCoeffArrays(int aLength, int bLength, int nLength) {
        a = new double[aLength];
        b = new double[bLength];
        n = new int[nLength];
        for (int i = 0; i < n.length; i++) {
            n[i] = i;
        }
    }

    public void createN(int nLength) {
        n = new int[nLength];
    }

    public double[] copyA() {
        return Arrays.copyOf(a, a.length);
    }

}
