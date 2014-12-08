package net.sf.dsp4j.datatypes._int;

import java.util.Arrays;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class GenericDirectIntIirFilter implements DirectIntIirFilter<GenericDirectIntIirFilter> {

    private int[] a;
    private int[] b;
    private int[] si;
    private int y;
    public final static int Q_DOT_31 = 31;

    GenericDirectIntIirFilter() {
    }

    public GenericDirectIntIirFilter setCoeff(int[] a, int[] b) {
        if (a.length == b.length) {
        this.a = a;
        this.b = b;
        } else if (a.length < b.length) {
            this.a = Arrays.copyOf(a, b.length);
            this.b = b;
        } else {
            this.a = a;
            this.b = Arrays.copyOf(b, a.length);
        }
        if (a.length > 1) {
            si = new int[a.length - 1];
        } else {
            si = new int[1];
        }
        return this;
    }

    @In
    @Override
    public int setX(int x) {
        y = (int)(((long)b[0] * x) >> Q_DOT_31) + si[0];
        for (int i = 0; i < si.length - 1; i++) {
            si[i] = (int)(((long)b[i + 1] * x) >> Q_DOT_31) - (int)(((long)a[i + 1] * y) >> Q_DOT_31) + si[i + 1];
        }
        si[si.length - 1] = (int)(((long)b[b.length - 1] * x) >> Q_DOT_31) - (int)(((long)a[a.length - 1] * y) >> Q_DOT_31);
        return y;
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
        return Arrays.copyOf(si, si.length);
    }

    @Override
    public void reset() {
       for (int i = 0; i < si.length; i++) {
           si[i] = 0;
       }
    }

}
