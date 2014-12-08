package com.falstad.filter.fir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class SincLowPassFilter extends FIRFilterType {

    int n;
    double wc, mult, peak;
    double resp[];
    boolean invert;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Cutoff Frequency");
        auxLabels[1].setText("Order");
        auxBars[0].setValue(invert ? 500 : 100);
        auxBars[1].setValue(120);
        auxBars[1].setMaximum(1600);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        wc = auxBars[0].getValue() * Math.PI / 1000.;
        n = auxBars[1].getValue();
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(n, 0, 0);
        int n2 = n / 2;
        double sum = 0;
        for (int i = 0; i != n; i++) {
            int ii = i - n2;
            f.setA(i, ((ii == 0) ? wc : Math.sin(wc * ii) / ii) * getWindow(i, n, winIdx, kaiserbar));
            sum += f.getA(i);
        }
        // normalize
        for (int i = 0; i != n; i++) {
            f.setA(i, f.getA(i) / sum);
        }
        if (invert) {
            for (int i = 0; i != n; i++) {
                f.setA(i, -f.getA(i));
            }
            f.setA(n2, f.getA(n2) + 1);
        }
        if (n == 1) {
            f.setA(0, 1);
        }
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Cutoff freq: " + getOmegaText(wc);
        x[1] = "Order: " + n;
    }

    @Override
    public boolean needsWindow() {
        return true;
    }
}
