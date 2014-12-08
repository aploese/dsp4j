package net.sf.dsp4j.octave.packages.signal_1_2_0;

import java.util.Arrays;
import net.sf.dsp4j.octave.packages.specfun_1_1_0.Ellipke;
import net.sf.dsp4j.octave_3_2_4.OctaveBuildIn;
import net.sf.dsp4j.octave_3_6_4.m.optimization.Fminbnd;
import net.sf.dsp4j.octave_3_6_4.m.optimization.FunctionWrapper;
import org.apache.commons.math3.complex.Complex;

/*
 * Copyright (C) 2001 Paulo Neis <p_neis@yahoo.com.br>

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU General Public License as published by the Free Software
 Foundation; either version 3 of the License, or (at your option) any later
 version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 details.

 You should have received a copy of the GNU General Public License along with
 this program; if not, see <http://www.gnu.org/licenses/>.
 */
/**
 * usage: [Zz, Zp, Zg] = ncauer(Rp, Rs, n)
 *
 * Analog prototype for Cauer filter. [z, p, g]=ncauer(Rp, Rs, ws) Rp = Passband
 * ripple Rs = Stopband ripple Ws = Desired order
 *
 * References: * - Serra, Celso Penteado, Teoria e Projeto de Filtros, Campinas:
 * CARTGRAF, 1983. - Lamar, Marcus Vinicius, Notas de aula da disciplina TE 456
 * - Circuitos Analogicos II, UFPR, 2001/2002.
 */
public class NCauer {

    private Complex[] zer;
    private Complex[] pol;
    private double T0;

    public NCauer(double Rp, double Rs, int n) {

        // Cutoff frequency = 1:
        double wp = 1;

        // Stop band edge ws:
        double ws = __ellip_ws(n, Rp, Rs);

        double k = wp / ws;
        double k1 = Math.sqrt(1.0 - Math.pow(k, 2));
        double q0 = (1.0 / 2.0) * ((1.0 - Math.sqrt(k1)) / (1 + Math.sqrt(k1)));
        double q = q0 + 2.0 * Math.pow(q0, 5) + 15.0 * Math.pow(q0, 9) + 150.0 * Math.pow(q0, 13); //%(....)
        double D = (Math.pow(10, 0.1 * Rs) - 1.0) / (Math.pow(10, (0.1 * Rp)) - 1.0);

        //Filter order maybe this, but not used now:
        //n=ceil(log10(16*D)/log10(1/q))

        double l = (1.0 / (2.0 * n)) * Math.log((Math.pow(10, 0.05 * Rp) + 1.0) / (Math.pow(10, 0.05 * Rp) - 1.0));
        double sig01 = 0;
        double sig02 = 0;
        for (int m = 0; m <= 30; m++) {
            sig01 = sig01 + Math.pow((-1), m) * Math.pow(q, (m * (m + 1))) * Math.sinh((2 * m + 1) * l);
        }
        for (int m = 1; m <= 30; m++) {
            sig02 = sig02 + Math.pow(-1.0, m) * Math.pow(q, (Math.pow(m, 2))) * Math.cosh(2 * m * l);
        }
        double sig0 = Math.abs((2.0 * Math.pow(q, (1.0 / 4.0)) * sig01) / (1.0 + 2.0 * sig02));

        double w = Math.sqrt((1.0 + k * Math.pow(sig0, 2)) * (1.0 + Math.pow(sig0, 2) / k));

        int r;
        if (n % 2 != 0) {
            r = (n - 1) / 2;
        } else {
            r = n / 2;
        }

        double[] wi = new double[r];
        for (int ii = 1; ii <= r; ii++) {
            double mu;
            if (n % 2 != 0) {
                mu = ii;
            } else {
                mu = (double)ii - 0.5;
            }
            double soma1 = 0;
            for (int m = 0; m <= 30; m++) {
                soma1 = soma1 + 2.0 * Math.pow(q, (1.0 / 4.0)) * (Math.pow(-1.0, m) * Math.pow(q, (m * (m + 1))) * Math.sin(((2.0 * m + 1.0) * Math.PI * mu) / n));
            }
            double soma2 = 0;
            for (int m = 1; m <= 30; m++) {
                soma2 = soma2 + 2.0 * (Math.pow(-1.0, m) * Math.pow(q, (Math.pow(m, 2))) * Math.cos((2.0 * m * Math.PI * mu) / n));
            }
            wi[ii - 1] = (soma1 / (1.0 + soma2));
        }

        double[] Vi = new double[wi.length];
        for (int i = 0; i < wi.length; i++) {
            Vi[i] = Math.sqrt((1.0 - (k * (Math.pow(wi[i], 2)))) * (1.0 - (Math.pow(wi[i], 2)) / k));
        }
        double[] A0i = new double[wi.length];
        for (int i = 0; i < wi.length; i++) {
            A0i[i] = 1.0 / (Math.pow(wi[i], 2));
        }
        double[] sqrA0i = new double[wi.length];
        for (int i = 0; i < wi.length; i++) {
            sqrA0i[i] = 1.0 / wi[i];
        }

        double[] B0i = new double[wi.length];
        for (int i = 0; i < wi.length; i++) {
            B0i[i] = (Math.pow((sig0 * Vi[i]), 2) + Math.pow((w * wi[i]), 2)) / Math.pow((1.0 + Math.pow(sig0, 2) * Math.pow(wi[i], 2)), 2);
        }
        
        double C01;
        if (wi.length == 0) {
            C01 = 1.0;
        } else {
            C01 = B0i[0] / A0i[0];
            for (int i = 1; i < wi.length; i++) {
                C01 *= B0i[i] / A0i[i];
            }
        }

        //Gain T0:
        if (n % 2 != 0) {
            T0 = sig0 * C01 * Math.sqrt(ws);
        } else {
            T0 = Math.pow(10, (-0.05 * Rp)) * C01;
        }

        //zeros:
        zer = new Complex[sqrA0i.length * 2];
        for (int i = 0; i < sqrA0i.length; i++) {
            zer[i] = Complex.valueOf(0.0, sqrA0i[i]);
            zer[i + sqrA0i.length] = Complex.valueOf(0.0, -sqrA0i[i]);
        }

        //poles:
        pol = new Complex[Vi.length * 2];
        for (int i = 0; i < Vi.length; i++) {
            pol[i] = new Complex(-2.0 * sig0 * Vi[i],  2.0 * wi[i] * w).divide(2.0 * (1 + Math.pow(sig0, 2) * Math.pow(wi[i], 2)));
            pol[i + Vi.length] = new Complex(-2.0 * sig0 * Vi[i], - 2.0 * wi[i] * w).divide(2.0 * (1 + Math.pow(sig0, 2) * Math.pow(wi[i], 2)));
        }

        //If n odd, there is a real pole  -sig0:
        if (n % 2 != 0) {
            pol = Arrays.copyOf(pol, pol.length + 1);
            pol[pol.length - 1] = new Complex(-sig0);
        }

        for (int i = 0; i < pol.length; i++) {
            pol[i] = pol[i].multiply(Math.sqrt(ws));
        }
        for (int i = 0; i < zer.length; i++) {
            zer[i] = zer[i].multiply(Math.sqrt(ws));
        }

    }

// usage: ws = __ellip_ws(n, rp, rs)
// Calculate the stop band edge for the Cauer filter.
    private double __ellip_ws(int n, double rp, double rs) {
        double ws;
        double kl0 = ((Math.pow(10, (0.1 * rp)) - 1.0) / (Math.pow(10, (0.1 * rs)) - 1.0));
        double k0 = (1.0 - kl0);

        Ellipke ellipke = new Ellipke(new double[]{kl0, k0});

        double ql0 = ellipke.getK(0);
        double q0 = ellipke.getK(1);
        final double x_V = (double)n * ql0 / q0;
        Fminbnd fminbnd = new Fminbnd();
        double kl = fminbnd.fminbnd(new FunctionWrapper() {
            double err;

            @Override
            public double fun(double x) {
                err = __ellip_ws_min(x, x_V);
                return err;
            }
        }, OctaveBuildIn.EPS, 1.0 - OctaveBuildIn.EPS);
        ws = Math.sqrt(1.0 / kl);
        return ws;
    }

//## usage: err = __ellip_ws_min(kl, x)
    private double __ellip_ws_min(double kl, double x) {
        Ellipke ellipke = new Ellipke(new double[]{kl, 1.0 - kl});
        double ql = ellipke.getK(0);
        double q = ellipke.getK(1);
        return Math.abs((ql / q) - x);
    }

    /**
     * @return the zer
     */
    public Complex[] getZer() {
        return zer;
    }

    /**
     * @return the pol
     */
    public Complex[] getPol() {
        return pol;
    }

    /**
     * @return the T0
     */
    public double getT0() {
        return T0;
    }
}
