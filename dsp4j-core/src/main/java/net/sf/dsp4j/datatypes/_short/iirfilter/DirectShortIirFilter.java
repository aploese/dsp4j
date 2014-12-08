package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.datatypes.DirectIirFilter;

/**
 *
 * @author aploese
 */
public interface DirectShortIirFilter extends DirectIirFilter, ShortFilter {

    int[] getA();

    int[] getB();

    int[] getSi();
    
}
