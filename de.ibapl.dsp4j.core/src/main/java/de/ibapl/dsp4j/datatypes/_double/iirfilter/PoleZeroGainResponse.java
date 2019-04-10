/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.datatypes._double.iirfilter;

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
