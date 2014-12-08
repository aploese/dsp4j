/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.datatypes.CascadedIirFilter;
import net.sf.dsp4j.datatypes.DirectIirFilter;
import net.sf.dsp4j.datatypes.IirFilterGenerator;
import net.sf.dsp4j.datatypes._double.iirfilter.GenericCascadedDoubleIirFilter;

/**
 *
 * @author aploese
 */
public class ShortIirFilterGenerator extends IirFilterGenerator {

    public ShortIirFilterGenerator(double sampleRate) {
        super(sampleRate);
    }

    @Override
    public <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz) {
        if (a.length == 2) {
                return (DF)new Direct1stOrderShortIirFilter(a, b);
        } else if (a.length == 3) {
                return (DF)new Direct2ndOrderShortIirFilter(a, b);
        } else {
                return (DF)new GenericDirectShortIirFilter(a, b);
            
        }
    }

    @Override
    public <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz) {
        switch (sos.length) {
            case 1:
                return (CF) new Cascaded1ShortIirFilter(sos, gain);
            case 2:
                return (CF) new Cascaded2ShortIirFilter(sos, gain);
            case 3:
                return (CF) new Cascaded3ShortIirFilter(sos, gain);
            case 4:
                return (CF) new Cascaded4ShortIirFilter(sos, gain);
            default:
                return (CF) new GenericCascadedDoubleIirFilter(sos, gain);
        }
    }


}