/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import static net.sf.dsp4j.DspConst.TWO_PI;
import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public class LPll extends AbstractSampleProcessingBlock {

    //State of the pll
    private double u2_n;
    private double phi2_n;
    private double ud_n_M_1;
    private double uf_n_M_1;
    
    
    //Config of the pll
    private double kd;
    private double k0;
    private double a1;
    private double b0;
    private double b1;
    private double w0;
    private double T;

    @In
    public double setX(double sample) {
        final double ud_n = kd * sample * u2_n;
        final double uf_n = -a1 * uf_n_M_1 + b0 * ud_n + b1 * ud_n_M_1;
        double phi2_n_P_1 = phi2_n + (w0 + k0 * uf_n) * T;
        if (phi2_n_P_1 > Math.PI) {
            phi2_n_P_1 -= TWO_PI;
        }
        double u2_n_P_1;
        if (phi2_n_P_1 > 0) {
            u2_n_P_1 = 1;
        } else {
            u2_n_P_1 = -1;
        }
        ud_n_M_1 = ud_n;
        uf_n_M_1 = uf_n;
        phi2_n = phi2_n_P_1;
        u2_n = u2_n_P_1;
        return uf_n;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        T = 1 / sampleRate;
        //TODO use samplerate ???
        super.setSampleRate(sampleRate);
    }

    @Override
    public void reset() {
        u2_n = 0;
        phi2_n = 0;
        ud_n_M_1 = 0;
        uf_n_M_1 = 0;
    }

}
