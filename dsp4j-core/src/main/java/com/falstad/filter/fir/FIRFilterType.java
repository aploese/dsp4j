package com.falstad.filter.fir;

import com.falstad.filter.Complex;
import com.falstad.filter.FFT;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.FilterType;
import java.awt.Scrollbar;

public abstract class FIRFilterType extends FilterType {

    private double response[];

    @Override
    public Complex getResponse(double w) {
        if (response == null) {
            return new Complex();
        }
        int off = (int) (response.length * w / (2 * Math.PI));
        off &= ~1;
        if (off < 0) {
            off = 0;
        }
        if (off >= response.length) {
            off = response.length - 2;
        }
        return new Complex(response[off], response[off + 1]);
    }

    double getWindow(int i, int n, int winIdx, Scrollbar kaiserBar) {
        if (n == 1) {
            return 1;
        }
        double x = 2 * Math.PI * i / (n - 1);
        double n2 = n / 2; // int
        switch (winIdx) {
            case 0:
                return 1; // rect
            case 1:
                return .54 - .46 * Math.cos(x); // hamming
            case 2:
                return .5 - .5 * Math.cos(x); // hann
            case 3:
                return .42 - .5 * Math.cos(x) + .08 * Math.cos(2 * x); // blackman
            case 4: {
                double kaiserAlphaPi = kaiserBar.getValue() * Math.PI / 120.;
                double q = (2 * i / (double) n) - 1;
                return bessi0(kaiserAlphaPi * Math.sqrt(1 - q * q));
            }
            case 5:
                return (i < n2) ? i / n2 : 2 - i / n2; // bartlett
            case 6: {
                double xt = (i - n2) / n2;
                return 1 - xt * xt;
            } // welch
        }
        return 0;
    }

    void setResponse(DirectFilter f) {
        response = new double[8192];
        if (f.nLength() != f.aLength()) {
            f.createN(f.aLength());
            for (int i = 0; i < f.aLength(); i++) {
                f.setN(i, i);
            }
        }
        for (int i = 0; i < f.aLength(); i++) {
            response[f.getN(i) * 2] = f.getA(i);
        }
        new FFT(response.length / 2).fft(response);
        double maxresp = 0;
        for (int i = 0; i < response.length; i += 2) {
            double r2 = response[i] * response[i] + response[i + 1] * response[i + 1];
            if (maxresp < r2) {
                maxresp = r2;
            }
        }
        // normalize response
        maxresp = Math.sqrt(maxresp);
        for (int i = 0; i < response.length; i++) {
            response[i] /= maxresp;
        }
        for (int i = 0; i < f.aLength(); i++) {
            f.setA(i, f.getA(i) / maxresp);
        }
    }

    double bessi0(double x) {
        final double absX = Math.abs(x);
        if (absX < 3.75) {
            double y = x / 3.75;
            y *= y;
            return  1.0 + y * (3.5156229 + y * (3.0899424 + y * (1.2067492
                    + y * (0.2659732 + y * (0.360768e-1 + y * 0.45813e-2)))));
        } else {
            double y = 3.75 / absX;
            return  (Math.exp(absX) / Math.sqrt(absX)) * (0.39894228 + y * (0.1328592e-1
                    + y * (0.225319e-2 + y * (-0.157565e-2 + y * (0.916281e-2
                    + y * (-0.2057706e-1 + y * (0.2635537e-1 + y * (-0.1647633e-1
                    + y * 0.392377e-2))))))));
        }
    }
}
