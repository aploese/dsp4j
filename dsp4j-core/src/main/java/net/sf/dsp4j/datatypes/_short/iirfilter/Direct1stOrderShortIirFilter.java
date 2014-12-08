package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class Direct1stOrderShortIirFilter implements DirectShortIirFilter {

    private final int a1;
    private final int b0;
    private final int b1;
    private int si0;
    private int y;

    public Direct1stOrderShortIirFilter(double[] a, double[] b) {
        this.a1 = (int)Math.round(a[1] * Q_DOT_15_VALUE);
        this.b0 = (int)Math.round(b[0] * Q_DOT_15_VALUE);
        this.b1 = (int)Math.round(b[1] * Q_DOT_15_VALUE);
    }

    @Override
    @In
    public void setX(int x) {
        y = (b0 * x + si0) >> Q_DOT_15_EXP;
        si0 = b1 * x - a1 * y;
    }

    @Override
    @Out
    public int getY() {
        return y;
    }

    @Override
    public int[] getA() {
        return new int[]{1, a1};
    }

    @Override
    public int[] getB() {
        return new int[]{b0, b1};
    }

    @Override
    public int[] getSi() {
        return new int[]{si0};
    }

    @Override
    public void reset() {
        si0 = 0;
    }

}