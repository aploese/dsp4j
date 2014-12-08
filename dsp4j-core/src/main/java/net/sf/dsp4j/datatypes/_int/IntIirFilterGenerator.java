/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._int;

import net.sf.dsp4j.datatypes.CascadedIirFilter;
import net.sf.dsp4j.datatypes.DirectIirFilter;
import net.sf.dsp4j.datatypes.IirFilterGenerator;

/**
 *
 * @author aploese
 */
public class IntIirFilterGenerator extends IirFilterGenerator {

    public final static int Q_DOT_31 = 1 << 31;
    
    public IntIirFilterGenerator(double sampleRate) {
        super(sampleRate);
    }

    @Override
    public <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz) {
        int[] aInt = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            aInt[i] = (int)Math.round(a[i] * Q_DOT_31);
        }
        int[] bInt = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            bInt[i] = (int)Math.round(b[i] * Q_DOT_31);
        }
        return (DF) new GenericDirectIntIirFilter().setCoeff(aInt, bInt);
    }

    @Override
    public <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
