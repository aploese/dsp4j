package net.sf.dsp4j.datatypes._short.iirfilter;

import java.util.Arrays;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class GenericDirectShortIirFilter implements DirectShortIirFilter {

    private final int[] a;
    private final int[] b;
    private final int[] si;
    private int y;

    public GenericDirectShortIirFilter(double [] a, double [] b) {
        if (a[0] != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        this.a = new int[a.length];
        this.b = new int[b.length];
        for (int i = 0; i < a.length; i++) {
            this.a[i] = (int)Math.round(a[i] * Q_DOT_15_VALUE);
            this.b[i] = (int)Math.round(b[i] * Q_DOT_15_VALUE);
        }
        if (a.length > 1) {
            si = new int[a.length - 1];
        } else {
            si = new int[1];
        }
    }

    @In
    @Override
    public void setX(int x) {
        y = (b[0] * x + si[0]) >> Q_DOT_15_EXP;
        for (int i = 0; i < si.length - 1; i++) {
            si[i] = b[i + 1] * x - a[i + 1] * y + si[i + 1];
        }
        si[si.length - 1] = b[b.length - 1] * x - a[a.length - 1] * y;
    }

    @Out
    @Override
    public int getY() {
        return y;
    }

    @Override
    public int[] getA() {
        return Arrays.copyOf(a, a.length);
    }

    @Override
    public int[] getB() {
        return Arrays.copyOf(b, b.length);
    }

    @Override
    public int[] getSi() {
        return null; //TODO Arrays.copyOf(si, si.length);
    }

    @Override
    public void reset() {
        for (int i = 0; i < si.length; i++) {
            si[i] = 0;
        }
    }

}
