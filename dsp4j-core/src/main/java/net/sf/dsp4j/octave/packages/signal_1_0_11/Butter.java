package net.sf.dsp4j.octave.packages.signal_1_0_11;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;

/**
 * Copyright (C) 1999 Paul Kienzle
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
 *
 * Generate a butterworth filter.
 * Default is a discrete space (Z) filter.
 *
 * [b,a] = butter(n, Wc)
 * low pass filter with cutoff pi*Wc radians
 *
 * [b,a] = butter(n, Wc, 'high')
 * high pass filter with cutoff pi*Wc radians
 *
 * [b,a] = butter(n, [Wl, Wh])
 * band pass filter with edges pi*Wl and pi*Wh radians
 *
 * [b,a] = butter(n, [Wl, Wh], 'stop')
 * band reject filter with edges pi*Wl and pi*Wh radians
 *
 * [z,p,g] = butter(...)
 * return filter as zero-pole-gain rather than coefficients of the
 * numerator and denominator polynomials.
 *
 * [...] = butter(...,'s')
 * return a Laplace space filter, W can be larger than 1.
 *
 * [a,b,c,d] = butter(...)
 * return  state-space matrices
 *
 * References:
 *
 * Proakis & Manolakis (1992). Digital Signal Processing. New York:
 * Macmillan Publishing Company.
 *
 * Author: Paul Kienzle <pkienzle@user.sf.net>
 * Modified by: Doug Stewart <dastew@sympatico.ca> Feb, 2003
 */
public class Butter extends PoleZeroGainIIRFilterGenerator {

    private Butter(int n, double[] w, boolean digital, boolean stop) {
        super(w, digital, stop);
        // Generate splane poles for the prototype butterworth filter
        // source: Kuc
        pole = new Complex[n];
        for (int i = 1; i <= pole.length; i++) {
            pole[i-1] = ComplexUtils.polar2Complex(1, Math.PI * (2 * i + n - 1) / (2 * n));
        }
        if (n % 2 == 1) {
            pole[n / 2] = new Complex(-1, 0); // pure real value at exp(i*pi)
        }
        zero = new Complex[0];
        gain = 1;
    }
    
    public Butter(int n, double wc, boolean digital, boolean stop) {
        this(n, new double[] {wc}, digital, stop);
    }

    public Butter(int n, double wl, double wh, boolean digital, boolean stop) {
        this(n, new double[] {wl, wh}, digital, stop);
    }
}
