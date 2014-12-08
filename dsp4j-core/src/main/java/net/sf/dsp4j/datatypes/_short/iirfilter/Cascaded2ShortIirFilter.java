/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public class Cascaded2ShortIirFilter extends AbstractCascadedShortIirFilter {

    public Cascaded2ShortIirFilter(double[][] sos, double gain) {
        super(sos, gain);
    }

    @In
    @Override
    public void setX(int sample) {
        sample = biquads[0].doCalc(sample);
        y = biquads[1].doCalc(sample);
    }
}
