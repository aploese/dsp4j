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
package de.ibapl.dsp4j.octave_3_2_4;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

/*

Copyright (C) 1996-2011 John W. Eaton

This file is part of Octave.

Octave is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 3 of the License, or (at your
option) any later version.

Octave is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with Octave; see the file COPYING.  If not, see
<http://www.gnu.org/licenses/>.

*/
/*
#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include "EIG.h"
#include "fEIG.h"

#include "defun-dld.h"
#include "error.h"
#include "gripes.h"
#include "oct-obj.h"
#include "utils.h"

DEFUN_DLD (eig, args, nargout,
  "-*- texinfo -*-\n\
@deftypefn  {Loadable Function} {@var{lambda} =} eig (@var{A})\n\
@deftypefnx {Loadable Function} {@var{lambda} =} eig (@var{A}, @var{B})\n\
@deftypefnx {Loadable Function} {[@var{V}, @var{lambda}] =} eig (@var{A})\n\
@deftypefnx {Loadable Function} {[@var{V}, @var{lambda}] =} eig (@var{A}, @var{B})\n\
The eigenvalues (and eigenvectors) of a matrix are computed in a several\n\
step process which begins with a Hessenberg decomposition, followed by a\n\
Schur@tie{}decomposition, from which the eigenvalues are apparent.  The\n\
eigenvectors, when desired, are computed by further manipulations of the\n\
Schur@tie{}decomposition.\n\
\n\
The eigenvalues returned by @code{eig} are not ordered.\n\
@seealso{eigs, svd}\n\
@end deftypefn")
*/

public class Eig {

    public static Complex[] eig(RealMatrix d)  {
        EigenDecomposition eig = new EigenDecomposition(d);
        double[] realEigenvalues = eig.getRealEigenvalues();
        double[] imagEigenvalues = eig.getImagEigenvalues();
        
        final Complex[] result = new Complex[realEigenvalues.length];
        for (int i = 0; i < realEigenvalues.length; i++) {
            result[i] = new Complex(realEigenvalues[i],imagEigenvalues[i]);
        }
        return result;
    }

}

/*

%!assert(eig ([1, 2; 2, 1]), [-1; 3], sqrt (eps));

%!test
%! [v, d] = eig ([1, 2; 2, 1]);
%! x = 1 / sqrt (2);
%! assert(d, [-1, 0; 0, 3], sqrt (eps));
%! assert(v, [-x, x; x, x], sqrt (eps));

%!assert(eig (single ([1, 2; 2, 1])), single([-1; 3]), sqrt (eps('single')));

%!test
%! [v, d] = eig (single([1, 2; 2, 1]));
%! x = single(1 / sqrt (2));
%! assert(d, single([-1, 0; 0, 3]), sqrt (eps('single')));
%! assert(v, [-x, x; x, x], sqrt (eps('single')));

%!test
%! A = [1, 2; -1, 1]; B = [3, 3; 1, 2];
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps));

%!test
%! A = single([1, 2; -1, 1]); B = single([3, 3; 1, 2]);
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps('single')));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps('single')));

%!test
%! A = [1, 2; 2, 1]; B = [3, -2; -2, 3];
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps));

%!test
%! A = single([1, 2; 2, 1]); B = single([3, -2; -2, 3]);
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps('single')));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps('single')));

%!test
%! A = [1+3i, 2+i; 2-i, 1+3i]; B = [5+9i, 2+i; 2-i, 5+9i];
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps));

%!test
%! A = single([1+3i, 2+i; 2-i, 1+3i]); B = single([5+9i, 2+i; 2-i, 5+9i]);
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps('single')));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps('single')));

%!test
%! A = [1+3i, 2+3i; 3-8i, 8+3i]; B = [8+i, 3+i; 4-9i, 3+i];
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps));

%!test
%! A = single([1+3i, 2+3i; 3-8i, 8+3i]); B = single([8+i, 3+i; 4-9i, 3+i]);
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps('single')));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps('single')));

%!test
%! A = [1, 2; 3, 8]; B = [8, 3; 4, 3];
%! [v, d] = eig (A, B);
%! assert(A * v(:, 1), d(1, 1) * B * v(:, 1), sqrt (eps));
%! assert(A * v(:, 2), d(2, 2) * B * v(:, 2), sqrt (eps));

%!error <Invalid call to eig.*> eig ();
%!error <Invalid call to eig.*> eig ([1, 2; 3, 4], [4, 3; 2, 1], 1);
%!error eig ([1, 2; 3, 4], 2);
%!error eig ([1, 2; 3, 4; 5, 6]);
%!error eig ("abcd");
%!error eig ([1 2 ; 2 3], "abcd");
%!error eig (false, [1 2 ; 2 3]);

*/
