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

## Generate an Chebyshev type I filter with Rp dB of pass band ripple.
## 
## [b, a] = cheby1(n, Rp, Wc)
##    low pass filter with cutoff pi*Wc radians
##
## [b, a] = cheby1(n, Rp, Wc, 'high')
##    high pass filter with cutoff pi*Wc radians
##
## [b, a] = cheby1(n, Rp, [Wl, Wh])
##    band pass filter with edges pi*Wl and pi*Wh radians
##
## [b, a] = cheby1(n, Rp, [Wl, Wh], 'stop')
##    band reject filter with edges pi*Wl and pi*Wh radians
##
## [z, p, g] = cheby1(...)
##    return filter as zero-pole-gain rather than coefficients of the
##    numerator and denominator polynomials.
##
## [...] = cheby1(...,'s')
##     return a Laplace space filter, W can be larger than 1.
## 
## [a,b,c,d] = cheby1(...)
##  return  state-space matrices 
## 
## References: 
##
## Parks & Burrus (1987). Digital Filter Design. New York:
## John Wiley & Sons, Inc.

## Author: Paul Kienzle <pkienzle@user.sf.net>
## Modified: Doug Stewart Feb. 2003
 */
public class Cheby1 extends PoleZeroGainIIRFilterGenerator {

    final double Rp;

    private Cheby1(int n, double Rp, double[] W, boolean digital, boolean stop) {
        super(W, digital, stop);

        if (Rp < 0) {
            throw new IllegalArgumentException("cheby1: passband ripple must be positive decibels");
        }

        this.Rp = Rp;

        //## Generate splane poles and zeros for the chebyshev type 1 filter
        final double C = 1; //# default cutoff frequency
        final double epsilon = FastMath.sqrt(FastMath.pow(10, (Rp / 10)) - 1);
        final double v0 = FastMath.asinh(1 / epsilon) / n;
        pole = new Complex[n];
        for (int i = 0; i < n; i++) {
            final Complex p = new Complex(0, FastMath.PI * (2.0 * i - n + 1.0) / (2.0 * n)).exp(); //TODO richtig ???
            pole[i] = new Complex(-FastMath.sinh(v0) * p.getReal()).add(IMAG_ONE.multiply(FastMath.cosh(v0)).multiply(p.getImaginary()));
        }
        zero = new Complex[0];

        //## compensate for amplitude at s=0
        Complex gainC = OctaveBuildIn.prod(OctaveBuildIn.neg(pole));
        //## if n is even, the ripple starts low, but if n is odd the ripple
        //## starts high. We must adjust the s=0 amplitude to compensate.
        if (n % 2 == 0) {
            gainC = gainC.divide(FastMath.pow(10, Rp / 20));
        }
        gain = gainC.getReal();
    }
    
    public Cheby1(int n, double Rp, double wc, boolean digital, boolean stop) {
        this(n, Rp, new double[] {wc}, digital, stop);
    }
    
    public Cheby1(int n, double Rp, double wl, double wh, boolean digital, boolean stop) {
        this(n, Rp, new double[] {wl, wh}, digital, stop);
    }
    
}
