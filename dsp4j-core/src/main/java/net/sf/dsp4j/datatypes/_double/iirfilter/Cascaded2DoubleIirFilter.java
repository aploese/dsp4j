/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double.iirfilter;

import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public final class Cascaded2DoubleIirFilter extends AbstractCascadedDoubleIirFilter {

    public Cascaded2DoubleIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }

    @In
    @Override
    public final void setX(double sample) {
        sample = biquads[0].doCalc(sample);
        y = biquads[1].doCalc(sample);
    }
}
