/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.datatypes._double.iirfilter.*;
import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public class Cascaded4ShortIirFilter extends AbstractCascadedShortIirFilter {

    public Cascaded4ShortIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }
    
    @In
    @Override
    public void setX(int sample) {
        sample = biquads[0].doCalc(sample);
        sample = biquads[1].doCalc(sample);
        sample = biquads[2].doCalc(sample);
        y = biquads[3].doCalc(sample);
    }
}
