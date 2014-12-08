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
public final class Cascaded1DoubleIirFilter extends AbstractCascadedDoubleIirFilter {

    public Cascaded1DoubleIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }
    
    @In
    @Override
    public final void setX(final double sample) {
        y = biquads[0].doCalc(sample);
    }
}
