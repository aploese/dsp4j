/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double.iirfilter;

import net.sf.dsp4j.octave.packages.signal_1_0_11.Bilinear;
import net.sf.dsp4j.octave.packages.signal_1_0_11.SfTrans;
import static net.sf.dsp4j.octave_3_2_4.OctaveBuildIn.real;
import net.sf.dsp4j.octave_3_2_4.m.polynomial.Poly;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public class PoleZeroGainResponse {
    private String filterName;
    int n;
    double[] f;
    double[] w;
    double sampleRate;
    boolean stop;
    Complex[] pole;
    Complex[] zeros;
    double gain;
    Complex[] sPole;
    Complex[] sZeros;
    double sGain;
    Complex[] zPole;
    Complex[] zZeros;
    double zGain;
    double[] a;
    double[] b;
    private Complex[] H;
    private double[] W;
    
//        public PoleZeroGainResponse getPZGResp(Complex[] zero, Complex[] pole, double gain, double T) {
//        PoleZeroGainResponse result = new PoleZeroGainResponse(this);
//        result.n = n;
//        result.w = getW();
//        result.f = frequencies;
//        result.stop = stop;
//        result.sampleRate = getSampleRate();
//
//        result.zeros = zero;
//        result.pole = pole;
//        result.gain = gain;
//
//        SfTrans sfTrans = new SfTrans(zero, pole, gain, getW(), stop);
//        result.sZeros = sfTrans.getSZero();
//        result.sPole = sfTrans.getSPole();
//        result.sGain = sfTrans.getSGain();
//
//        Bilinear bilinear = new Bilinear(sfTrans.getSZero(), sfTrans.getSPole(), sfTrans.getSGain(), T);
//        result.zZeros = bilinear.getZZero();
//        result.zPole = bilinear.getZPole();
//        result.zGain = bilinear.getZGain();
//
//        result.a = real(Poly.poly(bilinear.getZPole()));
//
//        Complex[] bCplx = Poly.poly(bilinear.getZZero());
//        for (int i= 0; i< bCplx.length; i++) {
//            bCplx[i] = bCplx[i].multiply(bilinear.getZGain());
//        }
//
//        result.b = real(bCplx);
//
////Response        Fre
//
//        return result;
//    }
//

    
}
