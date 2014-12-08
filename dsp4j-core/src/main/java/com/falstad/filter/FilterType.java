package com.falstad.filter;

import java.awt.Label;
import java.awt.Scrollbar;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class FilterType {

    protected int sampleRate;
    protected NumberFormat showFormat;

    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        return 0;
    }

    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        showFormat = DecimalFormat.getInstance();
        showFormat.setMaximumFractionDigits(2);
    }

    public abstract Complex getResponse(double w);

    public int getPoleCount() {
        return 0;
    }

    public int getZeroCount() {
        return 0;
    }

    public Complex getPole(int i) {
        return new Complex(0, 0);
    }

    public Complex getZero(int i) {
        return new Complex(0, 0);
    }

    public abstract Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar);

    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
    }

    public boolean needsWindow() {
        return false;
    }

    protected String getOmegaText(double wc) {
        return ((int) (wc * sampleRate / (2 * Math.PI))) + " Hz";
    }

    protected String getUnitText(double v, String u) {
        double va = Math.abs(v);
        if (va < 1e-17) {
            return "0 " + u;
        }
        if (va < 1e-12) {
            return showFormat.format(v * 1e15) + " f" + u;
        }
        if (va < 1e-9) {
            return showFormat.format(v * 1e12) + " p" + u;
        }
        if (va < 1e-6) {
            return showFormat.format(v * 1e9) + " n" + u;
        }
        if (va < 1e-3) {
            return showFormat.format(v * 1e6) + " \u03bc" + u;
        }
        if (va < 1e-2 || (u.compareTo("m") != 0 && va < 1)) {
            return showFormat.format(v * 1e3) + " m" + u;
        }
        if (va < 1) {
            return showFormat.format(v * 1e2) + " c" + u;
        }
        if (va < 1e3) {
            return showFormat.format(v) + " " + u;
        }
        if (va < 1e6) {
            return showFormat.format(v * 1e-3) + " k" + u;
        }
        if (va < 1e9) {
            return showFormat.format(v * 1e-6) + " M" + u;
        }
        if (va < 1e12) {
            return showFormat.format(v * 1e-9) + " G" + u;
        }
        if (va < 1e15) {
            return showFormat.format(v * 1e-12) + " T" + u;
        }
        return v + " " + u;
    }

    public void setCutoff(double f, Scrollbar[] auxBars, Label[] auxLabels) {
        auxBars[0].setValue((int) (2000*f));
    }

}
