package net.sf.dsp4j.octave.packages.signal_1_2_0;

/*## Copyright (C) 1999 Paul Kienzle <pkienzle@users.sf.net>
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
 * ## Compute butterworth filter order and cutoff for the desired response ##
 * characteristics. Rp is the allowable decibels of ripple in the pass ## band.
 * Rs is the minimum attenuation in the stop band. ## ## [n, Wc] = buttord(Wp,
 * Ws, Rp, Rs) ## Low pass (Wp<Ws) or high pass (Wp>Ws) filter design. Wp is the
 * ## pass band edge and Ws is the stop band edge. Frequencies are ## normalized
 * to [0,1], corresponding to the range [0,Fs/2]. ## ## [n, Wc] = buttord([Wp1,
 * Wp2], [Ws1, Ws2], Rp, Rs) ## Band pass (Ws1<Wp1<Wp2<Ws2) or band reject
 * (Wp1<Ws1<Ws2<Wp2) ## filter design. Wp gives the edges of the pass band, and
 * Ws gives ## the edges of the stop band. ## ## Theory: |H(W)|^2 =
 * 1/[1+(W/Wc)^(2N)] = 10^(-R/10) ## With some algebra, you can solve
 * simultaneously for Wc and N given ## Ws,Rs and Wp,Rp. For high pass filters,
 * subtracting the band edges ## from Fs/2, performing the test, and swapping
 * the resulting Wc back ## works beautifully. For bandpass and bandstop filters
 * this process ## significantly overdesigns. Artificially dividing N by 2 in
 * this case ## helps a lot, but it still overdesigns. ## ## See also: butter
 */
public class ButtOrd extends IirFilterOrder {

    private double[] Wc;

    private void calcButtOrd(double[] Wp, double[] Ws, double Rp, double Rs) {
        double T = 2;

        // if high pass, reverse the sense of the test
        int stopIndex = -1;
        for (int i = 0; i < Wp.length; i++) {
            if (Wp[i] > Ws[i]) {
                stopIndex = i;
                break;
            }
        }
        if (stopIndex > -1) {
            Wp[stopIndex] = 1 - Wp[stopIndex]; // stop will be at most length 1, so no need to
            Ws[stopIndex] = 1 - Ws[stopIndex]; // subtract from ones(1,length(stop))
        }

        // warp the target frequencies according to the bilinear transform
        for (int i = 0; i < Wp.length; i++) {
            Ws[i] = 2.0 / T * Math.tan(Math.PI * Ws[i] / T);
            Wp[i] = 2.0 / T * Math.tan(Math.PI * Wp[i] / T);
        }
        // compute minimum n which satisfies all band edge conditions
        // the factor 1/length(Wp) is an artificial correction for the
        // band pass/stop case, which otherwise significantly overdesigns.
        double qs = Math.log(Math.pow(10.0, Rs / 10.0) - 1.0);
        double qp = Math.log(Math.pow(10, Rp / 10.0) - 1);
        n = 0;
        for (int i = 0; i < Wp.length; i++) {
            n = Math.max(n, (int) Math.ceil(0.5 * (qs - qp) / Math.log(Ws[i] / Wp[i]) / Wp.length));
        }
        // compute -3dB cutoff given Wp, Rp and n
        Wc = new double[Wp.length];
        for (int i = 0; i < Wp.length; i++) {
            Wc[i] = Math.exp(Math.log(Wp[i]) - qp / 2.0 / n);
        }

        // unwarp the returned frequency
        for (int i = 0; i < Wp.length; i++) {
            Wc[i] = Math.atan(T / 2.0 * Wc[i]) * T / Math.PI;
        }
        // if high pass, reverse the sense of the test
        if (stopIndex > -1) {
            Wc[stopIndex] = 1 - Wc[stopIndex];
        }

    }

    public ButtOrd(double wp, double ws, double rp, double rs) {
        if (wp < ws) {
            stop = false;
        } else if (wp > ws) {
            stop = true;
        } else {
            throw new RuntimeException();
        }
        calcButtOrd(new double[]{wp}, new double[]{ws}, rp, rs);
    }

    public ButtOrd(double wpl, double wph, double wsl, double wsh, double rp, double rs) {
        if (wsl < wpl && wpl < wph && wph < wsh) {
            stop = false;
        } else if (wpl < wsl && wsl < wsh && wsh < wph) {
            stop = true;
        } else {
            throw new RuntimeException();
        }
        calcButtOrd(new double[]{wpl, wph}, new double[]{wsl, wsh}, rp, rs);
    }

    public double getWc(int index) {
        return Wc[index];
    }

    public int getWcCount() {
        return Wc.length;
    }
}
