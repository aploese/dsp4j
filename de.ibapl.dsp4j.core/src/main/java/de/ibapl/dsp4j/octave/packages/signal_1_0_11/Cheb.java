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

import org.apache.commons.math3.util.FastMath;

/*
## Copyright (C) 2002 André Carezia
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or (at
## your option) any later version.
##
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; If not, see <http://www.gnu.org/licenses/>.

## Usage:  cheb (n, x)
##
## Returns the value of the nth-order Chebyshev polynomial calculated at
## the point x. The Chebyshev polynomials are defined by the equations:
##
##           / cos(n acos(x),    |x| <= 1
##   Tn(x) = |
##           \ cosh(n acosh(x),  |x| > 1
##
## If x is a vector, the output is a vector of the same size, where each
## element is calculated as y(i) = Tn(x(i)).

## Author:  André Carezia <acarezia@uol.com.br>
## Description:  Value of the Chebyshev polynomials
 */
public class Cheb {

    public static double[] cheb(int n, double[] x) {

        if (n <= 0) {
            throw new IllegalArgumentException("cheb: n has to be a positive integer");
        }

        if (x.length == 0) {
            return new double[0];
        }
        //# avoid resizing latencies
        double[] T = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i]) > 1) {
                T[i] = FastMath.cos(n * FastMath.acos(x[i]));
            } else {
                T[i] = FastMath.cosh(n * FastMath.acosh(x[i]));
            }
        }
        return T;
    }
}
