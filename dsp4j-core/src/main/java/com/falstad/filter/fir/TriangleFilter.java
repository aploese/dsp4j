package com.falstad.filter.fir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class TriangleFilter extends FIRFilterType {

    int ni;
    double n;

    @Override public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Cutoff Frequency");
        auxBars[0].setValue(500);
        return 1;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = 4000. / auxBars[0].getValue();
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
        double sum = 0;
        double n2 = n / 2;
        for (i = 0; i < n; i++) {
            double q = 0;
            if (i < n2) {
                q = i / n2;
            } else {
                q = 2 - (i / n2);
            }
            sum += q;
            f.setA(i, q);
        }
        for (i = 0; i != f.aLength(); i++) {
            f.setA(i, f.getA(i) /sum);
        }
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Triangle (FIR)";
        x[1] = "Cutoff: " + getOmegaText(4 * Math.PI / n);
        x[2] = "Length: " + showFormat.format(n);
    }
}
