package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class Direct2ndOrderShortIirFilter implements DirectShortIirFilter {

    private final int a1;
    private final int a2;
    private final int b0;
    private final int b1;
    private final int b2;
    private int si0;
    private int si1;
    private int y;

    public Direct2ndOrderShortIirFilter(double [] a, double [] b) {
        if (a[0] != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        this.a1 = (int)Math.round(a[1] * Q_DOT_15_VALUE);
        this.a2 = (int)Math.round(a[2] * Q_DOT_15_VALUE);
        this.b0 = (int)Math.round(b[0] * Q_DOT_15_VALUE);
        this.b1 = (int)Math.round(b[1] * Q_DOT_15_VALUE);
        this.b2 = (int)Math.round(b[2] * Q_DOT_15_VALUE);
    }

    @Override
    @In
    public void setX(int x) {
        y = (b0 * x + si0) >> Q_DOT_15_EXP;
        si0 = b1 * x - a1 * y + si1;
        si1 = b2 * x - a2 * y;
    }

    @Override
    @Out
    public int getY() {
        return y;
    }

    @Override
    public int [] getA() {
        return new int[]{1, a1, a2};
    }

    @Override
    public int [] getB() {
        return new int[]{b0, b1, b2};
    }

    @Override
    public int [] getSi() {
        return new int[]{si0, si1};
    }

    @Override
    public void reset() {
        si0 = 0;
        si1 = 0;
    }

}