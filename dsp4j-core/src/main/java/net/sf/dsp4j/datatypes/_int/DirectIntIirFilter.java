package net.sf.dsp4j.datatypes._int;

import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;
import net.sf.dsp4j.datatypes.DirectIirFilter;

/**
 *
 * @author aploese
 */
public interface DirectIntIirFilter<T extends DirectIntIirFilter> extends DirectIirFilter {

    T setCoeff(int[] a, int[] b);

    @In
    int setX(int x);

    @Out
    int getY();

    int[] getA();

    int[] getB();

    int[] getSi();

    void reset();
    
}
