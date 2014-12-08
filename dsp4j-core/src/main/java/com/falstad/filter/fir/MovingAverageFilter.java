package com.falstad.filter.fir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class MovingAverageFilter extends FIRFilterType {

    private double n;
    private int ni;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Cutoff Frequency");
        auxBars[0].setValue(500);
        return 1;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = 2000. / auxBars[0].getValue();
        if (n > 1000) {
            n = 1000;
        }
        ni = (int) n;
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(ni + 1, 0, 0);
        int i;
        for (i = 0; i != ni; i++) {
            f.setA(i, 1. / n);
        }
        f.setA(i, (n - ni) / n);
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Moving Average (FIR)";
        x[1] = "Cutoff: " + getOmegaText(2 * Math.PI / n);
        x[2] = "Length: " + showFormat.format(n);
    }
}
