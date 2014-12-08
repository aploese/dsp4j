package net.sf.dsp4j.octave_3_2_4.m.general;

import java.util.Arrays;

/*
## Copyright (C) 1994, 1995, 1996, 1997, 1998, 2000, 2002, 2004, 2005,
##               2006, 2007, 2008, 2009 John W. Eaton
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
## @deftypefn {Function File} {} postpad (@var{x}, @var{l}, @var{c})
## @deftypefnx {Function File} {} postpad (@var{x}, @var{l}, @var{c}, @var{dim})
## @seealso{prepad, resize}
## @end deftypefn

## Author: Tony Richardson <arichard@stark.cc.oh.us>
## Created: June 1994
 */
public class Postpad {

    public static double[] postpad(double[] x, int l) {
        return Arrays.copyOf(x, l);
    }
}
