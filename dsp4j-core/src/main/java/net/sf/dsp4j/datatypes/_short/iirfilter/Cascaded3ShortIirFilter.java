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
public class Cascaded3ShortIirFilter extends AbstractCascadedShortIirFilter {

    public Cascaded3ShortIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }
    
    @In
    @Override
    public void setX(int sample) {
        sample = biquads[0].doCalc(sample);
        sample = biquads[1].doCalc(sample);
        y = biquads[2].doCalc(sample);
    }
}
