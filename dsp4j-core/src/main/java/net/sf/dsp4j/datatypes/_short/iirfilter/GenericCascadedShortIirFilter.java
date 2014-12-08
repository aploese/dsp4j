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
public class GenericCascadedShortIirFilter extends AbstractCascadedShortIirFilter {

    public GenericCascadedShortIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }
    
    @In
    @Override
    public void setX(int sample) {
        for (BiQuad b : biquads) {
            sample = b.doCalc(sample);
        }
    }
}
