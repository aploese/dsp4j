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

import java.util.Arrays;
import org.apache.commons.math3.util.FastMath;

/*
 ## Copyright (C) 2000 Paul Kienzle <pkienzle@users.sf.net>
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
 * ## Compute chebyshev type II filter order and cutoff for the desired response
 * ## characteristics. Rp is the allowable decibels of ripple in the pass ##
 * band. Rs is the minimum attenuation in the stop band. ## ## [n, Wc] =
 * cheb2ord(Wp, Ws, Rp, Rs) ## Low pass (Wp<Ws) or high pass (Wp>Ws) filter
 * design. Wp is the ## pass band edge and Ws is the stop band edge. Frequencies
 * are ## normalized to [0,1], corresponding to the range [0,Fs/2]. ## ## [n,
 * Wc] = cheb2ord([Wp1, Wp2], [Ws1, Ws2], Rp, Rs) ## Band pass (Ws1<Wp1<Wp2<Ws2)
 * or band reject (Wp1<Ws1<Ws2<Wp2) ## filter design. Wp gives the edges of the
 * pass band, and Ws gives ## the edges of the stop band. ## ## Theory: ## ##
 * See also: cheby2
 */
public class Cheb2Ord extends IirFilterOrder {

    private double[] Wc;

    private void calcCheb2Ord(double[] Wp, double[] Ws, double Rp, double Rs) {
        double T = 2;

        // returned frequency is the same as the input frequency
        Wc = Arrays.copyOf(Ws, Ws.length);

        // warp the target frequencies according to the bilinear transform
        for (int i = 0; i < Wp.length; i++) {
            Ws[i] = 2.0 / T * Math.tan(Math.PI * Ws[i] / T);
            Wp[i] = 2.0 / T * Math.tan(Math.PI * Wp[i] / T);
        }
        double Wa;

        if (Wp[0] < Ws[0]) {
            // low pass
            if (Wp.length == 1) {
                Wa = Wp[0] / Ws[0];
            } else {
                // band reject
                throw new RuntimeException("band reject is not implement yet.");
            }
        } else {
            // if high pass, reverse the sense of the test
            if (Wp.length == 1) {
                Wa = Ws[0] / Wp[0];
            } else {
                // band pass 
                Wa = Double.MAX_VALUE;
                for (int i = 0; i < Wp.length; i++) {
                    Wa = Math.min(Wa, Math.abs((Math.pow(Wp[i], 2) - Ws[0] * Ws[1]) / (Wp[i] * (Ws[0] - Ws[1]))));
                }
            }
        }

        // compute minimum n which satisfies all band edge conditions
        final double stop_atten = Math.pow(10, Math.abs(Rs) / 10.0);
        final double pass_atten = Math.pow(10, Math.abs(Rp) / 10.0);
        n = (int) Math.ceil(FastMath.acosh(Math.sqrt((stop_atten - 1.0) / (pass_atten - 1.0))) / FastMath.acosh(1.0 / Wa));

    }

    public Cheb2Ord(double wp, double ws, double rp, double rs) {
        if (wp < ws) {
            stop = false;
        } else if (wp > ws) {
            stop = true;
        } else {
            throw new RuntimeException();
        }
        calcCheb2Ord(new double[]{wp}, new double[]{ws}, rp, rs);
    }

    public Cheb2Ord(double wpl, double wph, double wsl, double wsh, double rp, double rs) {
        if (wsl < wpl && wpl < wph && wph < wsh) {
            stop = false;
        } else if (wpl < wsl && wsl < wsh && wsh < wph) {
            stop = true;
        } else {
            throw new RuntimeException();
        }
        calcCheb2Ord(new double[]{wpl, wph}, new double[]{wsl, wsh}, rp, rs);
    }

    public double getWc(int index) {
        return Wc[index];
    }

    public int getWcCount() {
        return Wc.length;
    }
}