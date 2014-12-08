package net.sf.dsp4j.octave.packages.signal_1_2_0;

/*## Copyright (C) 2001 Paulo Neis <p_neis@yahoo.com.br>
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
 * ## usage: [n,wp] = ellipord(wp,ws, rp,rs) ## ## Calculate the order for the
 * elliptic filter (discrete) ## wp: Cutoff frequency ## ws: Stopband edge ##
 * rp: decibels of ripple in the passband. ## rs: decibels of ripple in the
 * stopband. ## ## References: ## ## - Lamar, Marcus Vinicius, Notas de aula da
 * disciplina TE 456 - Circuitos ## Analogicos II, UFPR, 2001/2002.
 */
public class EllipOrd extends IirFilterOrder {

        private double[] Wp;

        private void calcEllipOrd(double[] Wp, double[] Ws, double Rp, double Rs) {
        // sampling frequency of 2 Hz
        double T = 2;

        double wl;
        double wh;
        double[] Wlw = new double[Wp.length];
        for (int i = 0; i < Wp.length; i++) {
            Wlw[i] = Math.tan(Math.PI * Wp[i] / T);
        } //prewarp
        double[] Whw = new double[Ws.length];
        for (int i = 0; i < Ws.length; i++) {
            Whw[i] = Math.tan(Math.PI * Ws[i] / T);
        } //prewarp

        //pass/stop band to low pass filter transform:
        if (Wlw.length == 2 && Whw.length == 2) {
            wl = 1;
            final double w02 = Wlw[0] * Wlw[1];	//Central frequency of stop/pass band (square)
            final double w3 = w02 / Whw[1];
            final double w4 = w02 / Whw[0];
            if (w3 > Whw[0]) {
                wh = (Whw[1] - w3) / (Wlw[1] - Wlw[0]);
            } else if (w4 < Whw[1]) {
                wh = (w4 - Whw[0]) / (Wlw[1] - Wlw[0]);
            } else {
                wh = (Whw[1] - Whw[0]) / (Wlw[1] - Wlw[0]);
            }
        } else if (Wlw.length == 2 && Whw.length == 1) {
            wl = 1;
            double w02 = Wlw[0] * Wlw[1];
            if (Whw[0] > Wlw[1]) {
                double w3 = w02 / Whw[0];
                wh = (Whw[0] - w3) / (Wlw[1] - Wlw[0]);
            } else {
                double w4 = w02 / Whw[0];
                wh = (w4 - Whw[0]) / (Wlw[1] - Wlw[0]);
            }
        } else {
            wl = Wlw[0];
            wh = Whw[0];
        }

        final double k = wl / wh;
        final double k1 = Math.sqrt(1 - Math.pow(k, 2));
        final double q0 = 0.5 * ((1 - Math.sqrt(k1)) / (1 + Math.sqrt(k1)));
        final double q = q0 + 2.0 * Math.pow(q0, 5) + 15.0 * Math.pow(q0, 9) + 150.0 * Math.pow(q0, 13); //(....)
        final double D = (Math.pow(10, 0.1 * Rs) - 1) / (Math.pow(10, 0.1 * Rp) - 1);

        n = (int) Math.ceil(Math.log10(16.0 * D) / Math.log10(1.0 / q));
    }

    public EllipOrd(double wp, double ws, double rp, double rs) {
        this.Wp = new double[]{wp};
        if (wp < ws) {
            stop = false;
            calcEllipOrd(new double[]{wp}, new double[]{ws}, rp, rs);
        } else {
            stop = true;
            calcEllipOrd(new double[]{ws}, new double[]{wp}, rp, rs);
        }
    }

    public EllipOrd(double wpl, double wph, double wsl, double wsh, double rp, double rs) {
        this.Wp = new double[]{wpl, wph};
        if (wsl < wpl && wpl < wph && wph < wsh) {
            stop = false;
            calcEllipOrd(new double[]{wpl, wph}, new double[]{wsl, wsh}, rp, rs);
        } else if (wpl < wsl && wsl < wsh && wsh < wph) {
            stop = true;
            calcEllipOrd(new double[]{wsl, wsh}, new double[]{wpl, wph}, rp, rs);
        } else {
            throw new RuntimeException();
        }
    }

    public double getWp(int index) {
        return Wp[index];
    }
    
    public int getWpCount() {
        return Wp.length;
    }

}
