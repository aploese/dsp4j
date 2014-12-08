package com.falstad.filter.fir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class GaussianFilter extends FIRFilterType {

    private int n;
    private double cw;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Offset");
        auxBars[0].setMaximum(1000);
        auxBars[0].setValue(100);
        auxLabels[1].setText("Width");
        auxBars[1].setMaximum(1000);
        auxBars[1].setValue(100);
        auxLabels[2].setText("Order");
        auxBars[2].setMaximum(1600);
        auxBars[2].setValue(160);
        return 3;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = auxBars[2].getValue();
        cw = auxBars[0].getValue() * Math.PI / 1000.;
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(n, 0, 0);
        int i;
        double w = auxBars[1].getValue() / 100000.;
        int n2 = n / 2;
        for (i = 0; i != n; i++) {
            int ii = i - n2;
            f.setA(i, Math.exp(-w * ii * ii) * Math.cos(ii * cw) * getWindow(i, n, winIdx, kaiserbar));
        }
        setResponse(f);
        return f;
    }

    @Override
    public boolean needsWindow() {
        return true;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Gaussian (FIR)";
        x[1] = "Order: " + n;
    }
}
