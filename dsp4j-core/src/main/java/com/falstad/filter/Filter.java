package com.falstad.filter;

import java.util.Arrays;

public abstract class Filter {

    protected double[] state;

    protected void createStateBuff(int size) {
        state = new double[size];
    }

    public void reset() {
        Arrays.fill(state, 0);
    }


    public abstract void run(double[] in, double[] out, int bp, int mask, int count);

    public double[] run(double[] in, int bp, int mask, int count) {
        final double[] out = new double[in.length];
        run(in, out, bp, mask, count);
        return out;
    }

//    public abstract double run(double x);
    public abstract void evalTransfer(Complex c);

    public abstract int getImpulseOffset();

    public abstract int getStepOffset();

    public abstract int getLength();

    public boolean useConvolve() {
        return false;
    }

    public double[] getImpulseResponse(int offset) {
        reset();
        final int pts = 1000;
        double[] inbuf = new double[offset + pts];
        inbuf[offset] = 1;
        return run(inbuf, offset, ~0, pts);
    }

    public double[] getStepResponse(int offset) {
        reset();
        final int pts = 1000;
        double[] inbuf = new double[offset + pts];
        for (int i = offset; i != inbuf.length; i++) {
            inbuf[i] = 1;
        }
        return run(inbuf, offset, ~0, pts);
    }

    public int getImpulseLen(int offset, double buf[]) {
        return countPoints(buf, offset);
    }

    public int getStepLen(int offset, double buf[]) {
        return countPoints(buf, offset);
    }

    protected int countPoints(double buf[], int offset) {
        double max = 0;
        int result = 0;
        double last = 123;
        for (int i = offset; i < buf.length; i++) {
            double qa = Math.abs(buf[i]);
            if (qa > max) {
                max = qa;
            }
            if (Math.abs(qa - last) > max * .003) {
                result = i - offset + 1;
                //System.out.println(qa + " " + last + " " + i + " " + max);
            }
            last = qa;
        }
        return result;
    }
}
