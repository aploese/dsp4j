/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double.iirfilter;

import net.sf.dsp4j.datatypes.CascadedIirFilter;
import net.sf.dsp4j.datatypes.DirectIirFilter;
import net.sf.dsp4j.datatypes.IirFilterGenerator;

/**
 *
 * @author aploese
 */
public class DoubleIirFilterGenerator extends IirFilterGenerator {

    public DoubleIirFilterGenerator(double samplerate) {
        super(samplerate);
    }

    @Override
    public <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz) {
        if (a.length == 2) {
            return (DF) new Direct1stOrderDoubleIirFilter(a, b);
        } else if (a.length == 3) {
            return (DF) new Direct2ndOrderDoubleIirFilter(a, b);
        } else {
            return (DF) new GenericDirectDoubleIirFilter(a, b);
        }
    }

    @Override
    public <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz) {
        switch (sos.length) {
            case 1:
                return (CF) new Cascaded1DoubleIirFilter(sos, gain);
            case 2:
                return (CF) new Cascaded2DoubleIirFilter(sos, gain);
            case 3:
                return (CF) new Cascaded3DoubleIirFilter(sos, gain);
            case 4:
                return (CF) new Cascaded4DoubleIirFilter(sos, gain);
            default:
                return (CF) new GenericCascadedDoubleIirFilter(sos, gain);
        }
    }
}
