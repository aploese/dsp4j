package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class NotchFilter extends IIRFilterType {

    private double wc;
    private double a;
    private double b;
    private double bw;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Notch Frequency");
        auxBars[0].setValue(500);
        auxLabels[1].setText("Bandwidth");
        auxBars[1].setValue(900);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        wc = auxBars[0].getValue() * Math.PI / 1000.;
        bw = auxBars[1].getValue() * Math.PI / 2000.;
        a = (1 - Math.tan(bw / 2)) / (1 + Math.tan(bw / 2));
        b = Math.cos(wc);
    }

    @Override
    public Complex getPole(int i) {
        Complex result = new Complex(-4 * a + (b + a * b) * (b + a * b), 0);
        result.sqrt();
        if (i == 1) {
            result.mult(-1);
        }
        result.add(b + a * b);
        result.mult(.5);
        return result;
    }

    @Override
    public int getPoleCount() {
        return 2;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Notch (IIR)";
        x[1] = "Notch Frequency: " + getOmegaText(wc);
        x[2] = "Bandwidth: " + getOmegaText(bw);
    }

    @Override
    public int getZeroCount() {
        return 2;
    }

    @Override
    public Complex getZero(int i) {
        Complex result = new Complex(b * b - 1, 0);
        result.sqrt();
        if (i == 1) {
            result.mult(-1);
        }
        result.add(b);
        return result;
    }
}
