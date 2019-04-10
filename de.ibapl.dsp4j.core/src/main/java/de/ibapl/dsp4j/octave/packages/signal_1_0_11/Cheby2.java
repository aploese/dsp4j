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

import de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;

/*
## Copyright (C) 1999 Paul Kienzle
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

## Generate an Chebyshev type II filter with Rs dB of stop band attenuation.
## 
## [b, a] = cheby2(n, Rs, Wc)
##    low pass filter with cutoff pi*Wc radians
##
## [b, a] = cheby2(n, Rs, Wc, 'high')
##    high pass filter with cutoff pi*Wc radians
##
## [b, a] = cheby2(n, Rs, [Wl, Wh])
##    band pass filter with edges pi*Wl and pi*Wh radians
##
## [b, a] = cheby2(n, Rs, [Wl, Wh], 'stop')
##    band reject filter with edges pi*Wl and pi*Wh radians
##
## [z, p, g] = cheby2(...)
##    return filter as zero-pole-gain rather than coefficients of the
##    numerator and denominator polynomials.
##
## [...] = cheby2(...,'s')
##     return a Laplace space filter, W can be larger than 1.
## 
## [a,b,c,d] = cheby2(...)
##  return  state-space matrices 
## 
## References: 
##
## Parks & Burrus (1987). Digital Filter Design. New York:
## John Wiley & Sons, Inc.

## Author: Paul Kienzle <pkienzle@users.sf.net>
## Modified: Doug Stewart Feb. 2003
 */
public class Cheby2 extends PoleZeroGainIIRFilterGenerator {

    private double Rs;

    private Cheby2(int n, double Rs, double[] W, boolean digital, boolean stop) {
        super(W, digital, stop);


        if (Rs < 0) {
            throw new IllegalArgumentException("cheby2: stopband attenuation must be positive decibels");
        }

        this.Rs = Rs;

        //## Generate splane poles and zeros for the chebyshev type 2 filter
        //## From: Stearns, SD; David, RA; (1988). Signal Processing Algorithms.
        //##       New Jersey: Prentice-Hall.
        int C = 1; //# default cutoff frequency
        final double lambda = FastMath.pow(10, Rs / 20);
        final double phi = FastMath.log(lambda + FastMath.sqrt(FastMath.pow(lambda, 2) - 1)) / n;
        final double[] theta = new double[n];
        final double[] alpha = new double[n];
        final double[] beta = new double[n];

        for (int i = 0; i < n; i++) {
            theta[i] = FastMath.PI * (i + 0.5) / n;
            alpha[i] = -FastMath.sinh(phi) * FastMath.sin(theta[i]);
            beta[i] = FastMath.cosh(phi) * FastMath.cos(theta[i]);
        }
        final Complex IMAG_ONE = new Complex(0.0, 1);
        if (n % 2 != 0) {
            //## drop theta==pi/2 since it results in a zero at infinity
            zero = new Complex[n - 1];
            for (int i = 0; i < n / 2; i++) {
                zero[i] = IMAG_ONE.multiply(C / FastMath.cos(theta[i]));
            }
            for (int i = n / 2 + 1; i < n; i++) {
                zero[i - 1] = IMAG_ONE.multiply(C / FastMath.cos(theta[i]));
            }
        } else {
            zero = new Complex[n];
            for (int i = 0; i < n; i++) {
                zero[i] = IMAG_ONE.multiply(C / FastMath.cos(theta[i]));
            }
        }
        pole = new Complex[n];
        for (int i = 0; i < n; i++) {
            pole[i] = new Complex(C / (FastMath.pow(alpha[i], 2) + FastMath.pow(beta[i], 2))).multiply(new Complex(alpha[i], -beta[i]));
        }

        /*
        ## Compensate for amplitude at s=0
        ## Because of the vagaries of floating point computations, the
        ## prod(pole)/prod(zero) sometimes comes out as negative and
        ## with a small imaginary component even though analytically
        ## the gain will always be positive, hence the abs(real(...))
         */
        gain = FastMath.abs(OctaveBuildIn.prod(pole).divide(OctaveBuildIn.prod(zero)).getReal());
    }
    
    public Cheby2(int n, double Rs, double wc, boolean digital, boolean stop) {
        this(n, Rs, new double[] {wc}, digital, stop);
    }

    public Cheby2(int n, double Rs, double wl, double wh, boolean digital, boolean stop) {
        this(n, Rs, new double[] {wl, wh}, digital, stop);
    }

}
