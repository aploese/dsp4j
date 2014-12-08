package net.sf.dsp4j.octave.packages.signal_1_0_11;

import java.util.Arrays;
import org.apache.commons.math3.complex.Complex;

import static net.sf.dsp4j.octave_3_2_4.OctaveBuildIn.prod;

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

 * usage: [Zz, Zp, Zg] = bilinear(Sz, Sp, Sg, T)
 *        [Zb, Za] = bilinear(Sb, Sa, T)
 *
 * Transform a s-plane filter specification into a z-plane
 * specification. Filters can be specified in either zero-pole-gain or
 * transfer function form. The input form does not have to match the
 * output form. 1/T is the sampling frequency represented in the z plane.
 *
 * Note: this differs from the bilinear function in the signal processing
 * toolbox, which uses 1/T rather than T.
 *
 * Theory: Given a piecewise flat filter design, you can transform it
 * from the s-plane to the z-plane while maintaining the band edges by
 * means of the bilinear transform.  This maps the left hand side of the
 * s-plane into the interior of the unit circle.  The mapping is highly
 * non-linear, so you must design your filter with band edges in the
 * s-plane positioned at 2/T tan(w*T/2) so that they will be positioned
 * at w after the bilinear transform is complete.
 *
 * The following table summarizes the transformation:
 *
 * +---------------+-----------------------+----------------------+
 * | Transform     | Zero at x             | Pole at x            |
 * |    H(S)       |   H(S) = S-x          |    H(S)=1/(S-x)      |
 * +---------------+-----------------------+----------------------+
 * |       2 z-1   | zero: (2+xT)/(2-xT)   | zero: -1             |
 * |  S -> - ---   | pole: -1              | pole: (2+xT)/(2-xT)  |
 * |       T z+1   | gain: (2-xT)/T        | gain: (2-xT)/T       |
 * +---------------+-----------------------+----------------------+
 *
 * With tedious algebra, you can derive the above formulae yourself by
 * substituting the transform for S into H(S)=S-x for a zero at x or
 * H(S)=1/(S-x) for a pole at x, and converting the result into the
 * form:
 *
 *    H(Z)=g prod(Z-Xi)/prod(Z-Xj)
 *
 * Please note that a pole and a zero at the same place exactly cancel.
 * This is significant since the bilinear transform creates numerous
 * extra poles and zeros, most of which cancel. Those which do not
 * cancel have a "fill-in" effect, extending the shorter of the sets to
 * have the same number of as the longer of the sets of poles and zeros
 * (or at least split the difference in the case of the band pass
 * filter). There may be other opportunistic cancellations but I will
 * not check for them.
 *
 * Also note that any pole on the unit circle or beyond will result in
 * an unstable filter.  Because of cancellation, this will only happen
 * if the number of poles is smaller than the number of zeros.  The
 * analytic design methods all yield more poles than zeros, so this will
 * not be a problem.
 *
 * References:
 *
 * Proakis & Manolakis (1992). Digital Signal Processing. New York:
 * Macmillan Publishing Company.

 * Author: Paul Kienzle <pkienzle@users.sf.net>
 */
public class Bilinear {

    private Complex[] zZero, zPole;
    private double zGain;

    /* ----------------  -------------------------  ------------------------
     * Bilinear          zero: (2+xT)/(2-xT)        pole: (2+xT)/(2-xT)
     *      2 z-1        pole: -1                   zero: -1
     * S -> - ---        gain: (2-xT)/T             gain: (2-xT)/T
     *      T z+1
     * ----------------  -------------------------  ------------------------
     */
    public Bilinear(Complex[] sZero, Complex[] sPole, double sGain, double T) {

        Complex _2 = new Complex(2);

        if (sZero.length > sPole.length || sPole.length == 0) {
//    error("bilinear: must have at least as many poles as zeros in s-plane");
        }

        Complex[] bz = new Complex[sZero.length];
        for (int i = 0; i < sZero.length; i++) {
            bz[i] = _2.subtract(sZero[i].multiply(T)).divide(T);
        }
        Complex[] bp = new Complex[sPole.length];
        for (int i = 0; i < sPole.length; i++) {
            bp[i] = _2.subtract(sPole[i].multiply(T)).divide(T);
        }

        zGain = new Complex(sGain, 0).multiply(prod(bz).divide(prod(bp))).getReal();
        zPole = new Complex[sPole.length];
        for (int i = 0; i < sPole.length; i++) {
            zPole[i] = _2.add(sPole[i].multiply(T)).divide(_2.subtract(sPole[i].multiply(T)));
        }
        zZero = new Complex[sPole.length];
        if (sZero.length == 0) {
            Arrays.fill(zZero, Complex.ONE.negate());
        } else {
        }
        for (int i = 0; i < sZero.length; i++) {
            zZero[i] = _2.add(sZero[i].multiply(T)).divide(_2.subtract(sZero[i].multiply(T)));
        }
        if (sZero.length < zZero.length) {
            Arrays.fill(zZero, sZero.length, zZero.length, Complex.ONE.negate());
        }
    }

    /**
     * @return the zZero
     */
    public Complex[] getZZero() {
        return zZero;
    }

    /**
     * @return the zPole
     */
    public Complex[] getZPole() {
        return zPole;
    }

    /**
     * @return the zGain
     */
    public double getZGain() {
        return zGain;
    }

}
