package net.sf.dsp4j.octave_3_2_4.m.polynomial;

import java.util.Arrays;
import net.sf.dsp4j.octave_3_2_4.Eig;
import net.sf.dsp4j.octave_3_2_4.OctaveBuildIn;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;

/*
 ## Copyright (C) 1994, 1995, 1996, 1997, 1999, 2000, 2004, 2005, 2006,
 ##               2007, 2008, 2009 John W. Eaton
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
 ## @deftypefn {Function File} {} roots (@var{v})
 ##
 ## For a vector @var{v} with @math{N} components, return
 ## the roots of the polynomial
 ## @tex
 ## $$
 ## v_1 z^{N-1} + \cdots + v_{N-1} z + v_N.
 ## $$
 ## @end tex
 ## @ifnottex
 ##
 ## @example
 ## v(1) * z^(N-1) + @dots{} + v(N-1) * z + v(N)
 ## @end example
 ## @end ifnottex
 ##
 ## As an example, the following code finds the roots of the quadratic
 ## polynomial
 ## @tex
 ## $$ p(x) = x^2 - 5. $$
 ## @end tex
 ## @ifnottex
 ## @example
 ## p(x) = x^2 - 5.
 ## @end example
 ## @end ifnottex
 ## @example
 ## @group
 ## c = [1, 0, -5];
 ## roots(c)
 ## @result{}  2.2361
 ## @result{} -2.2361
 ## @end group
 ## @end example
 ## Note that the true result is
 ## @tex
 ## $\pm \sqrt{5}$
 ## @end tex
 ## @ifnottex
 ## @math{+/- sqrt(5)}
 ## @end ifnottex
 ## which is roughly
 ## @tex
 ## $\pm 2.2361$.
 ## @end tex
 ## @ifnottex
 ## @math{+/- 2.2361}.
 ## @end ifnottex
 ## @seealso{compan}
 ## @end deftypefn

 ## Author: KH <Kurt.Hornik@wu-wien.ac.at>
 ## Created: 24 December 1993
 ## Adapted-By: jwe
 */
public class Roots {

    public static Complex[] roots(RealVector v) {

        
            if (v.isInfinite() || v.isNaN()) {
                throw new RuntimeException("roots: inputs must not contain Inf or NaN");
            }


        int n = v.getDimension();

        // ## If v = [ 0 ... 0 v(k+1) ... v(k+l) 0 ... 0 ], we can remove the
        // ## leading k zeros and n - k - l roots of the polynomial are zero.

        int[] f = new int[v.getDimension()];
        if (v.getDimension() > 0) {
            int fI = 0;
            double max = v.getMaxValue();
            double min = FastMath.abs(v.getMinValue());
            if (min > max) {
                max = min;
            }
            RealVector v1 = v.mapDivide(max);
            f = OctaveBuildIn.find(v1);
        }

        Complex[] r = new Complex[0];
        if (f.length > 0 && n > 1) {
            v = v.getSubVector(f[0], f[f.length - 1] - f[0] + 1);
            if (v.getDimension() > 1) {
                double[] ones = new double[v.getDimension() - 2];
                Arrays.fill(ones, 1);
                RealMatrix A = OctaveBuildIn.diag(ones, -1);
                for (int i = 0; i < A.getRowDimension(); i++) {
                    A.setEntry(i, 0, -v.getEntry(i + 1) / v.getEntry(0));
                }
                try {
                    r = Eig.eig(A);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (f[f.length - 1] < n) {
                    int diffLength = n -1 - f[f.length - 1];
                    if (diffLength > 0) {
                    int rl = r.length;
                    r = Arrays.copyOf(r, r.length + diffLength);
                    Arrays.fill(r, rl, r.length, Complex.ZERO);
                    }
                }
            } else {
                r = new Complex[n - f[f.length - 1]];
                Arrays.fill(r, Complex.ZERO);
            }
        } else {
            r = new Complex[0];
        }
        return r;

    }
}
/*
%!test
%! p = [poly([3 3 3 3]), 0 0 0 0];
%! r = sort (roots (p));
%! assert (r, [0; 0; 0; 0; 3; 3; 3; 3], 0.001)

%!assert(all (all (abs (roots ([1, -6, 11, -6]) - [3; 2; 1]) < sqrt (eps))));

%!assert(isempty (roots ([])));

%!error roots ([1, 2; 3, 4]);
 
%!assert(isempty (roots (1)));

 %!error roots ([1, 2; 3, 4]);
 
%!error roots ([1 Inf 1]);

%!error roots ([1 NaN 1]);

%!assert(roots ([1e-200, -1e200, 1]), 1e-200)
%!assert(roots ([1e-200, -1e200 * 1i, 1]), -1e-200 * 1i)
*/