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
package de.ibapl.dsp4j.octave.packages.signal_1_0_11;

import java.util.Arrays;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexFormat;

import static de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn.neg;
import static de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn.prod;

/** Copyright (C) 1999 Paul Kienzle
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.

 * usage: [Sz, Sp, Sg] = sftrans(Sz, Sp, Sg, W, stop)
 *
 * Transform band edges of a generic lowpass filter (cutoff at W=1)
 * represented in splane zero-pole-gain form.  W is the edge of the
 * target filter (or edges if band pass or band stop). Stop is true for
 * high pass and band stop filters or false for low pass and band pass
 * filters. Filter edges are specified in radians, from 0 to pi (the
 * nyquist frequency).
 *
 * Theory: Given a low pass filter represented by poles and zeros in the
 * splane, you can convert it to a low pass, high pass, band pass or
 * band stop by transforming each of the poles and zeros individually.
 * The following table summarizes the transformation:
 *
 * Transform         Zero at x                  Pole at x
 * ----------------  -------------------------  ------------------------
 * Low Pass          zero: Fc x/C               pole: Fc x/C
 * S -> C S/Fc       gain: C/Fc                 gain: Fc/C
 * ----------------  -------------------------  ------------------------
 * High Pass         zero: Fc C/x               pole: Fc C/x
 * S -> C Fc/S       pole: 0                    zero: 0
 *                   gain: -x                   gain: -1/x
 * ----------------  -------------------------  ------------------------
 * Band Pass         zero: b ± sqrt(b^2-FhFl)   pole: b ± sqrt(b^2-FhFl)
 *        S^2+FhFl   pole: 0                    zero: 0
 * S -> C --------   gain: C/(Fh-Fl)            gain: (Fh-Fl)/C
 *        S(Fh-Fl)   b=x/C (Fh-Fl)/2            b=x/C (Fh-Fl)/2
 * ----------------  -------------------------  ------------------------
 * Band Stop         zero: b ± sqrt(b^2-FhFl)   pole: b ± sqrt(b^2-FhFl)
 *        S(Fh-Fl)   pole: ±sqrt(-FhFl)         zero: ±sqrt(-FhFl)
 * S -> C --------   gain: -x                   gain: -1/x
 *        S^2+FhFl   b=C/x (Fh-Fl)/2            b=C/x (Fh-Fl)/2
 * ----------------  -------------------------  ------------------------
 * Bilinear          zero: (2+xT)/(2-xT)        pole: (2+xT)/(2-xT)
 *      2 z-1        pole: -1                   zero: -1
 * S -> - ---        gain: (2-xT)/T             gain: (2-xT)/T
 *      T z+1
 * ----------------  -------------------------  ------------------------
 *
 * where C is the cutoff frequency of the initial lowpass filter, Fc is
 * the edge of the target low/high pass filter and [Fl,Fh] are the edges
 * of the target band pass/stop filter.  With abundant tedious algebra,
 * you can derive the above formulae yourself by substituting the
 * transform for S into H(S)=S-x for a zero at x or H(S)=1/(S-x) for a
 * pole at x, and converting the result into the form:
 *
 *    H(S)=g prod(S-Xi)/prod(S-Xj)
 *
 * The transforms are from the references.  The actual pole-zero-gain
 * changes I derived myself.
 *
 * Please note that a pole and a zero at the same place exactly cancel.
 * This is significant for High Pass, Band Pass and Band Stop filters
 * which create numerous extra poles and zeros, most of which cancel.
 * Those which do not cancel have a "fill-in" effect, extending the
 * shorter of the sets to have the same number of as the longer of the
 * sets of poles and zeros (or at least split the difference in the case
 * of the band pass filter).  There may be other opportunistic
 * cancellations but I will not check for them.
 *
 * Also note that any pole on the unit circle or beyond will result in
 * an unstable filter.  Because of cancellation, this will only happen
 * if the number of poles is smaller than the number of zeros and the
 * filter is high pass or band pass.  The analytic design methods all
 * yield more poles than zeros, so this will not be a problem.
 *
 * References:
 *
 * Proakis & Manolakis (1992). Digital Signal Processing. New York:
 * Macmillan Publishing Company.

 * Author: Paul Kienzle <pkienzle@users.sf.net>

 * 2000-03-01 pkienzle@kienzle.powernet.co.uk
 *       leave transformed Sg as a complex value since cheby2 blows up
 *       otherwise (but only for odd-order low-pass filters).  bilinear
 *       will return Zg as real, so there is no visible change to the
 *       user of the IIR filter design functions.
 * 2001-03-09 pkienzle@kienzle.powernet.co.uk
 *       return real Sg; don't know what to do for imaginary filters
 */
public class SfTrans {

    private Complex[] sZero, sPole;
    private double sGain;
    final private int C;

    public SfTrans(Complex[] zero, Complex[] pole, double gain, double[] W, boolean stop) {

        C = 1;
        if (zero.length > pole.length || pole.length == 0) {
            throw new RuntimeException("sftrans: must have at least as many poles as zeros in s-plane");
        }

        if (W.length == 2) {
            if (stop) {
                bandStop(zero, pole, W[0], W[1], gain);
            } else {
                bandPass(zero, pole, W[0], W[1], gain);
            }
        } else {
            if (stop) {
                highPass(zero, pole, W[0], gain);
            } else {
                lowPass(zero, pole, W[0], gain);
            }
        }
    }

    /** ----------------  -------------------------  ------------------------
     * Low Pass          zero: Fc x/C               pole: Fc x/C
     * S -> C S/Fc       gain: C/Fc                 gain: Fc/C
     * ----------------  -------------------------  ------------------------
     */
    private void lowPass(Complex[] zero, Complex[] pole, double Fc, double gain) {

        sGain = gain * Math.pow(C / Fc, zero.length - pole.length);
        sPole = new Complex[pole.length];
        for (int i = 0; i < pole.length; i++) {
            sPole[i] = pole[i].multiply(Fc / C);
        }
        sZero = new Complex[zero.length];  //        sZero = zero .* Fc ./ C ... sZero = zero.multiply(Fc).divideToSelf(C);
        for (int i = 0; i < zero.length; i++) {
            sZero[i] = zero[i].multiply(Fc / C);
        }
    }

    /** ----------------  -------------------------  ------------------------
     * High Pass         zero: Fc C/x               pole: Fc C/x
     * S -> C Fc/S       pole: 0                    zero: 0
     *                   gain: -x                   gain: -1/x
     * ----------------  -------------------------  ------------------------
     */
    private void highPass(Complex[] zero, Complex[] pole, double Fc, double gain) {
        if (zero.length == 0) {
            sGain = gain * Complex.ONE.divide(prod(neg(pole))).getReal();
        } else if (pole.length == 0) {
            sGain = gain * prod(neg(zero)).getReal();
        } else {
            sGain = gain * prod(neg(zero)).divide(prod(neg(pole))).getReal();
        }

        final Complex _C_x_Fc = new Complex(C * Fc);
        sPole = new Complex[pole.length];
        for (int i = 0; i < pole.length; i++) {
            sPole[i] = _C_x_Fc.divide(pole[i]);
        }

        if (zero.length == 0) {
            sZero = new Complex[pole.length];
            Arrays.fill(sZero, Complex.ZERO);
        } else {
            sZero = new Complex[Math.max(zero.length, pole.length)];
            for (int i = 0; i < zero.length; i++) {
                sZero[i] = _C_x_Fc.divide(zero[i]);
            }
            if (pole.length > zero.length) {
                Arrays.fill(sZero, zero.length, sZero.length, Complex.ZERO);
            }
        }
    }

    /** ----------------  -------------------------  ------------------------
     * Band Stop         zero: b ± sqrt(b^2-FhFl)   pole: b ± sqrt(b^2-FhFl)
     *        S(Fh-Fl)   pole: ±sqrt(-FhFl)         zero: ±sqrt(-FhFl)
     * S -> C --------   gain: -x                   gain: -1/x
     *        S^2+FhFl   b=C/x (Fh-Fl)/2            b=C/x (Fh-Fl)/2
     * ----------------  -------------------------  ------------------------
     */
    private void bandStop(Complex[] zero, Complex[] pole, double Fl, double Fh, double gain) {
        if (zero.length == 0) {
            sGain = gain * Complex.ONE.divide(prod(neg(pole))).getReal();
        } else if (pole.length == 0) {
            sGain = gain * prod(neg(zero)).getReal();
        } else {
            sGain = gain * prod(neg(zero)).divide(prod(neg(pole))).getReal();
        }

        Complex[] b = new Complex[pole.length];
        final Complex c = new Complex((Fh - Fl) / (2 * C));
        for (int i = 0; i < pole.length; i++) {
            b[i] = c.divide(pole[i]);
        }

        final double _Fh_X_Fl = Fh * Fl;
        sPole = new Complex[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            Complex h = b[i].pow(2).subtract(_Fh_X_Fl).sqrt();
            sPole[i] = b[i].add(h);
            sPole[i + b.length] = b[i].subtract(h);
        }

        Complex[] extend = new Complex[]{new Complex(-_Fh_X_Fl).sqrt(), new Complex(-_Fh_X_Fl).sqrt().negate()};
        if (zero.length == 0) {
            sZero = new Complex[pole.length * 2];
            for (int i = 0; i < pole.length; i++) {
                sZero[i * 2] = extend[0];
                sZero[i * 2 + 1] = extend[1];
            }
        } else {

            b = new Complex[zero.length];
            for (int i = 0; i < zero.length; i++) {
                b[i] = c.divide(zero[i]);
            }
            sZero = new Complex[Math.max(zero.length, b.length * 2)];
            for (int i = 0; i < b.length; i++) {
                Complex h = b[i].pow(2).subtract(_Fh_X_Fl).sqrt();
                sZero[i] = b[i].add(h);
                sZero[i + b.length] = b[i].subtract(h);
            }
            if (pole.length > zero.length) {
                for (int i = 0; i < pole.length - zero.length; i++) {
                    sZero[zero.length + i] = extend[i % 2];
                }
            }
        }
    }

    /** ----------------  -------------------------  ------------------------
     * Band Pass         zero: b ± sqrt(b^2-FhFl)   pole: b ± sqrt(b^2-FhFl)
     *        S^2+FhFl   pole: 0                    zero: 0
     * S -> C --------   gain: C/(Fh-Fl)            gain: (Fh-Fl)/C
     *        S(Fh-Fl)   b=x/C (Fh-Fl)/2            b=x/C (Fh-Fl)/2
     * ----------------  -------------------------  ------------------------
     */
    private void bandPass(Complex[] zero, Complex[] pole, double Fl, double Fh, double gain) {

        sGain = gain * Math.pow(C / (Fh - Fl), zero.length - pole.length);

        Complex[] b = new Complex[pole.length];
        final Complex c = new Complex((Fh - Fl) / (2 * C));
        for (int i = 0; i < pole.length; i++) {
            b[i] = pole[i].multiply(c);
        }

        final double _Fh_X_Fl = Fh * Fl;
        sPole = new Complex[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            Complex h = b[i].pow(2).subtract(_Fh_X_Fl).sqrt();
            sPole[i] = b[i].add(h);
            sPole[i + b.length] = b[i].subtract(h);
        }
        if (zero.length == 0) {
            sZero = new Complex[pole.length];
            Arrays.fill(sZero, Complex.ZERO);
        } else {
            b = new Complex[zero.length];
            for (int i = 0; i < zero.length; i++) {
                b[i] = zero[i].multiply(c);
            }

            sZero = new Complex[Math.max(zero.length, b.length * 2)];
            for (int i = 0; i < b.length; i++) {
                Complex h = b[i].pow(2).subtract(_Fh_X_Fl).sqrt();
                sZero[i] = b[i].add(h);
                sZero[i + b.length] = b[i].subtract(h);
            }
            if (pole.length > zero.length) {
                Arrays.fill(sZero, b.length * 2, sZero.length, Complex.ZERO);
            }
        }
    }

    public static void addComplexArray(StringBuilder sb, ComplexFormat cf, String name, Complex[] arr) {
        sb.append(name);
        sb.append(" = [");
        for (int i = 0; i < arr.length; i++) {
            sb.append(cf.format(arr[i]));
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

    }

    @Override
    public String toString() {
        ComplexFormat cf = ComplexFormat.getInstance();
        StringBuilder sb = new StringBuilder();
        addComplexArray(sb, cf, "sPole", sPole);
        addComplexArray(sb, cf, ", sZeros", sZero);
        sb.append(", sGain = ");
        sb.append(sGain);
        return sb.toString();
    }

    /**
     * @return the sZero
     */
    public Complex[] getSZero() {
        return sZero;
    }

    /**
     * @return the sPole
     */
    public Complex[] getSPole() {
        return sPole;
    }

    /**
     * @return the sGain
     */
    public double getSGain() {
        return sGain;
    }

    /**
     * @return the C
     */
    public int getC() {
        return C;
    }
}
