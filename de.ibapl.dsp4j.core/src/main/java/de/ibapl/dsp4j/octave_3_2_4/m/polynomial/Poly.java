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
package de.ibapl.dsp4j.octave_3_2_4.m.polynomial;

import java.util.Arrays;
import org.apache.commons.math3.complex.Complex;

/**
## Copyright (C) 1994, 1995, 1996, 1997, 1999, 2000, 2005, 2006, 2007,
##               2008, 2009 John W. Eaton
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
## @deftypefn {Function File} {} poly (@var{a})
## If @var{a} is a square @math{N}-by-@math{N} matrix, @code{poly (@var{a})}
## is the row vector of the coefficients of @code{det (z * eye (N) - a)},
## the characteristic polynomial of @var{a}.  As an example we can use
## this to find the eigenvalues of @var{a} as the roots of @code{poly (@var{a})}.
## @example
## @group
## roots(poly(eye(3)))
## @result{} 1.00000 + 0.00000i
## @result{} 1.00000 - 0.00000i
## @result{} 1.00000 + 0.00000i
## @end group
## @end example
## In real-life examples you should, however, use the @code{eig} function
## for computing eigenvalues.
##
## If @var{x} is a vector, @code{poly (@var{x})} is a vector of coefficients
## of the polynomial whose roots are the elements of @var{x}.  That is,
## of @var{c} is a polynomial, then the elements of
## @code{@var{d} = roots (poly (@var{c}))} are contained in @var{c}.
## The vectors @var{c} and @var{d} are, however, not equal due to sorting
## and numerical errors.
## @seealso{eig, roots}
## @end deftypefn

## Author: KH <Kurt.Hornik@wu-wien.ac.at>
## Created: 24 December 1993
## Adapted-By: jwe
*/
public class Poly {

    public static Complex[] poly(Complex[] x) {

        Complex[] y;

        if (x.length == 0) {
            return new Complex[]{Complex.ONE};
        }

        y = new Complex[x.length + 1];
        Arrays.fill(y, Complex.ZERO);
        y[0] = Complex.ONE;
        for (int j = 1; j < y.length; j++) {
            for (int i = j; i >= 1; i--) {
                y[i] = y[i].subtract(x[j - 1].multiply(y[i-1]));
            }
        }

        return y;
    }

        public static double[] poly(double[] x) {

        double[] y;

        if (x.length == 0) {
            return new double[]{1.0};
        }

        y = new double[x.length + 1];
        y[0] = 1.0;
        for (int j = 1; j < y.length; j++) {
            for (int i = j; i >= 1; i--) {
                y[i] = y[i] - x[j - 1] * y[i-1];
            }
        }
        return y;
        }

        public static double[] poly(double[][] x) {

        if (x.length == 0) {
            return new double[]{1.0};
        }
        int xLenght = x.length;
        for (int i = 0; i < xLenght; i++) {
            if (x[i].length != xLenght) {
                throw new RuntimeException();
            }
        }

//        return poly(eig(x));
        return null;
        }

}
