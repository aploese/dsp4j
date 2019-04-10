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
package de.ibapl.dsp4j.octave.packages.signal_1_2_0;

import de.ibapl.dsp4j.octave.packages.signal_1_0_11.PoleZeroGainIIRFilterGenerator;

/*
  ## Copyright (C) 2001 Paulo Neis <p_neis@yahoo.com.br>
## Copyright (C) 2003 Doug Stewart <dastew@sympatico.ca>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.
*/
/**
## N-ellip 0.2.1
##usage: [Zz, Zp, Zg] = ellip(n, Rp, Rs, Wp, stype,'s')
##
## Generate an Elliptic or Cauer filter (discrete and contnuious).
## 
## [b,a] = ellip(n, Rp, Rs, Wp)
##  low pass filter with order n, cutoff pi*Wp radians, Rp decibels 
##  of ripple in the passband and a stopband Rs decibels down.
##
## [b,a] = ellip(n, Rp, Rs, Wp, 'high')
##  high pass filter with cutoff pi*Wp...
##
## [b,a] = ellip(n, Rp, Rs, [Wl, Wh])
##  band pass filter with band pass edges pi*Wl and pi*Wh ...
##
## [b,a] = ellip(n, Rp, Rs, [Wl, Wh], 'stop')
##  band reject filter with edges pi*Wl and pi*Wh, ...
##
## [z,p,g] = ellip(...)
##  return filter as zero-pole-gain.
##
## [...] = ellip(...,'s')
##     return a Laplace space filter, W can be larger than 1.
## 
## [a,b,c,d] = ellip(...)
##  return  state-space matrices 
##
## References: 
##
## - Oppenheim, Alan V., Discrete Time Signal Processing, Hardcover, 1999.
## - Parente Ribeiro, E., Notas de aula da disciplina TE498 -  Processamento 
##   Digital de Sinais, UFPR, 2001/2002.
## - Kienzle, Paul, functions from Octave-Forge, 1999 (http://octave.sf.net).
*/

public class Ellip  extends PoleZeroGainIIRFilterGenerator {
    private NCauer nc;

    private Ellip(int n, double Rp, double Rs, double[] W, boolean digital, boolean stop) {
        super(W, digital, stop);


        if (Rp < 0) {
            throw new IllegalArgumentException("ellip: stopband attenuation must be positive decibels");
        }

        if (Rs < 0) {
            throw new IllegalArgumentException("ellip: stopband attenuation must be positive decibels");
        }

        nc = new NCauer(Rp, Rs, n);
        zero = nc.getZer();
        pole = nc.getPol();
        gain = nc.getT0();
    }
    
    public Ellip(int n, double Rp, double Rs, double wc, boolean digital, boolean stop) {
        this(n, Rp, Rs, new double[] {wc}, digital, stop);
    }

    public Ellip(int n, double Rp, double Rs, double wl, double wh, boolean digital, boolean stop) {
        this(n, Rp, Rs, new double[] {wl, wh}, digital, stop);
    }

}