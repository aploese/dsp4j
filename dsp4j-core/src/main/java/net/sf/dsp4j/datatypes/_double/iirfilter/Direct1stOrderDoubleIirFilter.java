package net.sf.dsp4j.datatypes._double.iirfilter;

import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public final class Direct1stOrderDoubleIirFilter implements DirectDoubleIirFilter {

    private final double a1;
    private final double b0;
    private final double b1;
    private double si0;
    private double y;

    public Direct1stOrderDoubleIirFilter(double[] a, double[] b) {
        if (a[0] != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        this.a1 = a[1];
        this.b0 = b[0];
        this.b1 = b[1];
    }

    @Override
    @In
    public final void setX(final double x) {
        y = b0 * x + si0;
        si0 = b1 * x - a1 * y;
    }

    @Override
    @Out
    public final double getY() {
        return y;
    }

    @Override
    public double[] getA() {
        return new double[]{1, a1};
    }

    @Override
    public double[] getB() {
        return new double[]{b0, b1};
    }

    @Override
    public double[] getSi() {
        return new double[]{si0};
    }

    @Override
    public void reset() {
        si0 = 0;
    }

}