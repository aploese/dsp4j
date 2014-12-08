package com.falstad.filter.fir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;
import java.util.Random;

public class RandomFilter extends FIRFilterType {

    private int n;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Order");
        auxBars[0].setMaximum(1600);
        auxBars[0].setValue(100);
        return 1;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = auxBars[0].getValue();
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        Random random = new Random();
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(n, 0, 0);
        int i;
        for (i = 0; i != n; i++) {
            f.setA(i, random.nextInt() * getWindow(i, n, winIdx, kaiserbar));
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
        x[0] = "Random (FIR)";
        x[1] = "Order: " + n;
    }
}
