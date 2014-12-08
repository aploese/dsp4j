package com.falstad.filter.fir;

import com.falstad.filter.Complex;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public abstract class SincBandFilter extends FIRFilterType {

    private int n;
    private double wc1;
    private double wc2;
    private double mult;
    private double peak;
    private double resp[];
    final private boolean invert;

    public SincBandFilter(boolean invert) {
        this.invert = invert;
    }

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Center Frequency");
        auxLabels[1].setText(invert ? "Passband Width" : "Stopband Width");
        auxLabels[2].setText("Order");
        auxBars[0].setValue(500);
        auxBars[1].setValue(50);
        auxBars[2].setValue(140);
        auxBars[2].setMaximum(1600);
        return 3;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        double wcmid = auxBars[0].getValue() * Math.PI / 1000.;
        double width = auxBars[1].getValue() * Math.PI / 1000.;
        wc1 = wcmid - width;
        wc2 = wcmid + width;
        if (wc1 < 0) {
            wc1 = 0;
        }
        if (wc2 > Math.PI) {
            wc2 = Math.PI;
        }
        n = auxBars[2].getValue();
    }

    @Override
    public int getPoleCount() {
        return 0;
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(n + 1, 0, 0);
        double xlist[] = new double[n + 1];
        int n2 = n / 2;
        int i;

        // generate low-pass filter
        double sum = 0;
        for (i = 0; i != n; i++) {
            int ii = i - n2;
            f.setA(i, ((ii == 0) ? wc1 : Math.sin(wc1 * ii) / ii)
                    * getWindow(i, n, winIdx, kaiserbar));
            sum += f.getA(i);
        }
        if (sum > 0) {
            // normalize
            for (i = 0; i != n; i++) {
                f.setA(i, f.getA(i) / sum);
            }
        }

        // generate high-pass filter
        sum = 0;
        for (i = 0; i != n; i++) {
            int ii = i - n2;
            xlist[i] = ((ii == 0) ? wc2 : Math.sin(wc2 * ii) / ii)
                    * getWindow(i, n, winIdx, kaiserbar);
            sum += xlist[i];
        }
        // normalize
        for (i = 0; i != n; i++) {
            xlist[i] /= sum;
        }
        // invert and combine with lopass
        for (i = 0; i != n; i++) {
            f.setA(i, f.getA(i) - xlist[i]);
        }
        f.setA(n2, f.getA(n2) + 1);
        if (invert) {
            for (i = 0; i != n; i++) {
                f.setA(i, -f.getA(i));
            }
            f.setA(n2, f.getA(i) + 1);
        }
        if (n == 1) {
            f.setA(0, 1);
        }
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = (invert) ? "Passband: " : "Stopband: ";
        x[0] += getOmegaText(wc1) + " - " + getOmegaText(wc2);
        x[1] = "Order: " + n;
    }

    @Override
    public boolean needsWindow() {
        return true;
    }
}
