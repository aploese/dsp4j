package com.falstad.filter;

public class CascadeFilter extends Filter {

    public CascadeFilter(int s) {
        size = s;
        createStateBuff(size * 3);
        a1 = new double[size];
        a2 = new double[size];
        b0 = new double[size];
        b1 = new double[size];
        b2 = new double[size];
        for (int i = 0; i != size; i++) {
            b0[i] = 1;
        }
    }
    private double[] a1;
    private double[] a2;
    private double[] b0;
    private double[] b1;
    private double[] b2;
    private int size;

    public void setAStage(double x1, double x2) {
        for (int i = 0; i != size; i++) {
            if (a1[i] == 0 && a2[i] == 0) {
                a1[i] = x1;
                a2[i] = x2;
                return;
            }
            if (a2[i] == 0 && x2 == 0) {
                a2[i] = -a1[i] * x1;
                a1[i] += x1;
                //System.out.println("setastate " + i + " " + a1[i] + " " + a2[i]);
                return;
            }
        }
        System.out.println("setAStage failed");
    }

    public void setBStage(double x0, double x1, double x2) {
        //System.out.println("setting b " + i + " "+ x0 + " "+ x1 + " "+ x2 + " " + size);
        for (int i = 0; i != size; i++) {
            if (b1[i] == 0 && b2[i] == 0) {
                b0[i] = x0;
                b1[i] = x1;
                b2[i] = x2;
                //System.out.println("setbstage " + i + " " + x0 + " " + x1 + " " + x2);
                return;
            }
            if (b2[i] == 0 && x2 == 0) {
                // (b0 + z b1)(x0 + z x1) = (b0 x0 + (b1 x0+b0 x1) z + b1 x1 z^2)
                b2[i] = b1[i] * x1;
                b1[i] = b1[i] * x0 + b0[i] * x1;
                b0[i] *= x0;
                //System.out.println("setbstage " + i + " " + b0[i]+" "+b1[i] + " " + b2[i]);
                return;
            }
        }
        System.out.println("setBStage failed");
    }

    @Override
    public void run(double inBuf[], double outBuf[], int bp, int mask, int count) {
        for (int i = 0; i < count; i++) {
            final int fi2 = bp + i;
            final int i20 = fi2 & mask;
            double in = inBuf[i20];
            for (int j = 0; j < size; j++) {
                int j3 = j * 3;
                final double d2 = state[j3 + 2] = state[j3 + 1];
                final double d1 = state[j3 + 1] = state[j3];
                final double d0 = state[j3] = in + a1[j] * d1 + a2[j] * d2;
                in = b0[j] * d0 + b1[j] * d1 + b2[j] * d2;
            }
            outBuf[i20] = in;
        }
    }

    @Override
    public void evalTransfer(Complex c) {
        final Complex cm1 = new Complex(c);
        cm1.recip();
        final Complex cm2 = new Complex(cm1);
        cm2.square();
        c.set(1);

        Complex top = new Complex();
        Complex bottom = new Complex();

        for (int i = 0; i != size; i++) {
            top.set(b0[i]);
            top.addMult(b1[i], cm1);
            top.addMult(b2[i], cm2);
            bottom.set(1);
            bottom.addMult(-a1[i], cm1);
            bottom.addMult(-a2[i], cm2);
            c.mult(top);
            c.div(bottom);
        }
    }

    @Override
    public int getImpulseOffset() {
        return 0;
    }

    @Override
    public int getStepOffset() {
        return 0;
    }

    @Override
    public int getLength() {
        return 1;
    }

    public int getSize() {
        return size;
    }

    /**
     * @return the a1
     */
    public double getA1(int i) {
        return a1[i];
    }

    /**
     * @return the a2
     */
    public double getA2(int i) {
        return a2[i];
    }

    /**
     * @return the b0
     */
    public double getB0(int i) {
        return b0[i];
    }

    /**
     * @return the b1
     */
    public double getB1(int i) {
        return b1[i];
    }

    /**
     * @return the b2
     */
    public double getB2(int i) {
        return b2[i];
    }

    public void setB0(int i, double value) {
        b0[i] = value;
    }

    public void setB1(int i, double value) {
        b1[i] = value;
    }

    public void setB2(int i, double value) {
        b2[i] = value;
    }
}
