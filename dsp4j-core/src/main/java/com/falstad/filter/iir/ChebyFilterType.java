package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public abstract class ChebyFilterType extends PoleFilterType {

    public static final double EPSILON = .00001;
    public static final double EPSILON2 = .003;
    public static final double LOG_10 = 2.30258509299404568401;
    protected double epsilon;
    protected int sign;

    void selectCheby(int s, Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[s].setText("Passband Ripple");
        auxBars[s].setValue(60);
    }

    void setupCheby(int a, Scrollbar[] auxBars, Label[] auxLabels) {
        int val = auxBars[a].getValue();
        double ripdb = 0;
        if (val < 300) {
            ripdb = 5 * val / 300.;
        } else {
            ripdb = 5 + 45 * (val - 300) / 700.;
        }
        double ripval = Math.exp(-ripdb * .1 * LOG_10);
        epsilon = Math.sqrt(1 / ripval - 1);
    }

    @Override
    public Complex getSPole(int i, double wc) {
        Complex c2 = new Complex();
        double alpha = 1 / epsilon + Math.sqrt(1 + 1 / (epsilon * epsilon));
        double a = .5 * (Math.pow(alpha, 1. / n) - Math.pow(alpha, -1. / n));
        double b = .5 * (Math.pow(alpha, 1. / n) + Math.pow(alpha, -1. / n));
        double theta = Math.PI / 2 + (2 * i + 1) * Math.PI / (2 * n);
        if (sign == -1) {
            wc = Math.PI - wc;
        }
        Complex result = new Complex();
        result.setMagPhase(Math.tan(wc * .5), theta);
        result.setRe(result.getRe() * a);
        result.setIm(result.getIm() * b);
        return result;
    }

    void getInfoCheby(String x[]) {
        x[2] = "Ripple: "
                + showFormat.format(-10 * Math.log(1 / (1 + epsilon * epsilon))
                / LOG_10) + " dB";
    }
}
