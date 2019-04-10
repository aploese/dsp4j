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
package de.ibapl.dsp4j.octave_3_2_4.m.general;

/*
## Copyright (C) 1995, 1996, 1999, 2000, 2002, 2004, 2005, 2006, 2007,
##               2008, 2009 Kurt Hornik
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
## @deftypefn {Function File} {} diff (@var{x}, @var{k}, @var{dim})
## If @var{x} is a vector of length @var{n}, @code{diff (@var{x})} is the
## vector of first differences
## @tex
##  $x_2 - x_1, \ldots{}, x_n - x_{n-1}$.
## @end tex
## @ifnottex
##  @var{x}(2) - @var{x}(1), @dots{}, @var{x}(n) - @var{x}(n-1).
## @end ifnottex
##
## If @var{x} is a matrix, @code{diff (@var{x})} is the matrix of column
## differences along the first non-singleton dimension.
##
## The second argument is optional.  If supplied, @code{diff (@var{x},
## @var{k})}, where @var{k} is a non-negative integer, returns the
## @var{k}-th differences.  It is possible that @var{k} is larger than
## then first non-singleton dimension of the matrix.  In this case,
## @code{diff} continues to take the differences along the next
## non-singleton dimension.
##
## The dimension along which to take the difference can be explicitly
## stated with the optional variable @var{dim}.  In this case the 
## @var{k}-th order differences are calculated along this dimension.
## In the case where @var{k} exceeds @code{size (@var{x}, @var{dim})}
## then an empty matrix is returned.
## @end deftypefn

## Author: KH <Kurt.Hornik@wu-wien.ac.at>
## Created: 2 February 1995
## Adapted-By: jwe
*/
public class Diff {

public static double[] diff (double[] x) {
    double[] result = new double[x.length - 1];
    for (int i = 0; i < result.length; i++) {
        result[i] = x[i + 1] - x[i];
    }
    return result;
}


}
/*
%!assert((diff ([1, 2, 3, 4]) == [1, 1, 1]
%! && diff ([1, 3, 7, 19], 2) == [2, 8]
%! && diff ([1, 2; 5, 4; 8, 7; 9, 6; 3, 1]) == [4, 2; 3, 3; 1, -1; -6, -5]
%! && diff ([1, 2; 5, 4; 8, 7; 9, 6; 3, 1], 3) == [-1, -5; -5, 0]
%! && isempty (diff (1))));

%!error diff ([1, 2; 3, 4], -1);

%!error diff ("foo");

%!error diff ();

%!error diff (1, 2, 3, 4);
*/
