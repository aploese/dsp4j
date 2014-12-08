package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public abstract class InvChebyFilterType extends ChebyFilterType {

    protected double cosh(double x) {
        return .5 * (Math.exp(x) + Math.exp(-x));
    }

    protected double sinh(double x) {
        return .5 * (Math.exp(x) - Math.exp(-x));
    }

    protected double acosh(double x) {
        return Math.log(x + Math.sqrt(x * x - 1));
    }
    protected double scale;

    @Override
    void selectCheby(int s, Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[s].setText("Stopband Attenuation");
        auxBars[s].setValue(600);
    }

    @Override
    void setupCheby(int a, Scrollbar[] auxBars, Label[] auxLabels) {
        epsilon = Math.exp(-auxBars[a].getValue() / 120.);
        scale = cosh(acosh(1 / epsilon) / n);
    }

    @Override
    public Complex getSPole(int i, double wc) {
        wc = Math.PI - wc;
        Complex result = super.getSPole(i, wc);
        result.recip();
        result.mult(scale);
        return result;
    }

    protected Complex getChebyZero(int i, double wc) {
        double bk = 1 / Math.cos((2 * i + 1) * Math.PI / (2 * n)) * scale;
        double a = Math.sin(Math.PI / 4 - wc / 2) / Math.sin(Math.PI / 4 + wc / 2);
        Complex result = new Complex(1 + a, bk * (1 - a));
        Complex c2 = new Complex(1 + a, bk * (a - 1));
        result.div(c2);
        return result;
    }

    @Override
    public void getInfoCheby(String x[]) {
        x[2] = "Stopband attenuation: "
                + showFormat.format(-10 * Math.log(1 + 1 / (epsilon * epsilon))
                / LOG_10) + " dB";
    }

    @Override
    public int getPoleCount() {
        return n;
    }

    @Override
    public int getZeroCount() {
        return n;
    }
}
