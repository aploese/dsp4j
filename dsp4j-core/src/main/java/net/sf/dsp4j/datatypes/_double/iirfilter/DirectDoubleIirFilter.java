package net.sf.dsp4j.datatypes._double.iirfilter;

import net.sf.dsp4j.datatypes.DirectIirFilter;
import net.sf.dsp4j.datatypes._double.DoubleBlock;

/**
 *
 * @author aploese
 */
public interface DirectDoubleIirFilter extends DirectIirFilter, DoubleBlock {

    double[] getA();

    double[] getB();

    double[] getSi();

}
