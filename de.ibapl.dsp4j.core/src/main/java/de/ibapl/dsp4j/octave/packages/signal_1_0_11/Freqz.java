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
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;

/*
## Copyright (C) 1994, 1995, 1996, 1997, 1999, 2000, 2002, 2005, 2006,
##               2007, 2009 John W. Eaton
##
## This file is part of Octave.
##
## Octave is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or (at
## your option) any later version.
##
## Octave is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with Octave; see the file COPYING.  If not, see
## <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {[@var{h}, @var{w}] =} freqz (@var{b}, @var{a}, @var{n}, "whole")
## Return the complex frequency response @var{h} of the rational IIR filter
## whose numerator and denominator coefficients are @var{b} and @var{a},
## respectively.  The response is evaluated at @var{n} angular frequencies
## between 0 and
## @ifnottex
##  2*pi.
## @end ifnottex
## @tex
##  $2\pi$.
## @end tex
##
## @noindent
## The output value @var{w} is a vector of the frequencies.
##
## If the fourth argument is omitted, the response is evaluated at
## frequencies between 0 and
## @ifnottex
##  pi.
## @end ifnottex
## @tex
##  $\pi$.
## @end tex
##
## If @var{n} is omitted, a value of 512 is assumed.
##
## If @var{a} is omitted, the denominator is assumed to be 1 (this
## corresponds to a simple FIR filter).
##
## For fastest computation, @var{n} should factor into a small number of
## small primes.
##
## @deftypefnx {Function File} {@var{h} =} freqz (@var{b}, @var{a}, @var{w})
## Evaluate the response at the specific frequencies in the vector @var{w}.
## The values for @var{w} are measured in radians.
##
## @deftypefnx {Function File} {[@dots{}] =} freqz (@dots{}, @var{Fs})
## Return frequencies in Hz instead of radians assuming a sampling rate
## @var{Fs}.  If you are evaluating the response at specific frequencies 
## @var{w}, those frequencies should be requested in Hz rather than radians.
##
## @deftypefnx {Function File} {} freqz (@dots{})
## Plot the pass band, stop band and phase response of @var{h} rather
## than returning them.
## @end deftypefn

## Author: jwe ???
 */
public class Freqz {

    Complex[] H;
    double[] w;

    public Freqz(double[] b, double[] a, int n) {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] hb = fft.transform(Arrays.copyOf(b, 2*n), TransformType.FORWARD);
        Complex[] ha = fft.transform(Arrays.copyOf(a, 2*n), TransformType.FORWARD);

        H = new Complex[n];
        w = new double[n];

        for (int i = 0; i < H.length; i++) {
            H[i] = hb[i].divide(ha[i]);
            w[i] = Math.PI / n * i;
        }

    }

    public Freqz(double[] b, int n) {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] hb = fft.transform(Arrays.copyOf(b, 2*n), TransformType.FORWARD);

        H = Arrays.copyOfRange(hb, 0, n);
        w = new double[n];

        for (int i = 0; i < H.length; i++) {
            w[i] = Math.PI / n * i;
        }
    }

    public Freqz(double[] b) {
        this(b, 512);
    }

    public Freqz(double[] b, double[] a) {
        this(b, a, 512);
    }
 }

/*
%!test # correct values and fft-polyval consistency
%! # butterworth filter, order 2, cutoff pi/2 radians
%! b = [0.292893218813452  0.585786437626905  0.292893218813452];
%! a = [1  0  0.171572875253810];
%! [h,w] = freqz(b,a,32);
%! assert(h(1),1,10*eps);
%! assert(abs(h(17)).^2,0.5,10*eps);
%! assert(h,freqz(b,a,w),10*eps); # fft should be consistent with polyval

%!test # whole-half consistency
%! b = [1 1 1]/3; # 3-sample average
%! [h,w] = freqz(b,1,32,'whole');
%! assert(h(2:16),conj(h(32:-1:18)),20*eps);
%! [h2,w2] = freqz(b,1,16,'half');
%! assert(h(1:16),h2,20*eps);
%! assert(w(1:16),w2,20*eps);

%!test # Sampling frequency properly interpreted
%! b = [1 1 1]/3; a = [1 0.2];
%! [h,f] = freqz(b,a,16,320);
%! assert(f,[0:15]'*10,10*eps);
%! [h2,f2] = freqz(b,a,[0:15]*10,320);
%! assert(f2,[0:15]*10,10*eps);
%! assert(h,h2.',20*eps);
%! [h3,f3] = freqz(b,a,32,'whole',320);
%! assert(f3,[0:31]'*10,10*eps);
*/
