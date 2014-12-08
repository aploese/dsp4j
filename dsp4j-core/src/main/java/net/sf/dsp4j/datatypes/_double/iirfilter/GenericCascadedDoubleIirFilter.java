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
public final class GenericCascadedDoubleIirFilter extends AbstractCascadedDoubleIirFilter {

    public GenericCascadedDoubleIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }
    
    @In
    @Override
    public final void setX(double sample) {
        for (BiQuad b : biquads) {
            sample = b.doCalc(sample);
        }
        y = sample;
    }
}
