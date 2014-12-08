package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class AllPassFilter extends IIRFilterType {

    private double a;

    @Override public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Phase Delay");
        auxBars[0].setValue(500);
        return 1;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        double delta = auxBars[0].getValue() / 1000.;
        a = (1 - delta) / (1 + delta);
    }

    @Override
    public Complex getPole(int i) {
        return new Complex(-a, 0);
    }

    @Override
    public int getPoleCount() {
        return 1;
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(2, 2, 2);
        f.setN(0,0);
        f.setN(1,1);
        f.setA(0, a);
        f.setA(1, 1);
        f.setB(0, 1);
        f.setB(1, a);
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Allpass Fractional Delay (IIR)";
    }
}
