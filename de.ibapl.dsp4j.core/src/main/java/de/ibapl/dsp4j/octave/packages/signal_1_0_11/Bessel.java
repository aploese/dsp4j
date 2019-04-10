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

/*
 ## Copyright (C) 2009 Thomas Sailer
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

 ## Return bessel analog filter prototype.
 ##
 ## References: 
 ##
 ## http://en.wikipedia.org/wiki/Bessel_polynomials
 */
public class Bessel extends PoleZeroGainIIRFilterGenerator {

    private Bessel(int n, double[] W, boolean digital, boolean stop) {
        super(W, digital, stop);
        BesselAp bap = new BesselAp(n);

        zero = bap.getZero();
        pole = bap.getPole();
        gain = bap.getGain();

    }
    
    public Bessel(int n, double wc, boolean digital, boolean stop) {
        this(n, new double[] {wc}, digital, stop);
    }

    public Bessel(int n, double wl, double wh, boolean digital, boolean stop) {
        this(n, new double[] {wl, wh}, digital, stop);
    }

}
