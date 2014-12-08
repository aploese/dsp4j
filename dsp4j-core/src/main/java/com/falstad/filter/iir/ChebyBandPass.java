package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class ChebyBandPass extends ChebyFilterType {

    @Override public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        int s = selectBandPass(auxBars, auxLabels);
        selectCheby(s++, auxBars, auxLabels);
        return s;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        setupBandPass(auxBars, auxLabels);
        setupCheby(3, auxBars, auxLabels);
    }

    @Override
    public Complex getPole(int i) {
        return getBandPassPole(i);
    }

    @Override
    public Complex getZero(int i) {
        return getBandPassZero(i);
    }

    @Override
    public int getPoleCount() {
        return n * 2;
    }

    @Override
    public int getZeroCount() {
        return n * 2;
    }

    @Override
    public void getInfo(String x[],Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Chebyshev (IIR), " + getPoleCount() + "-pole";
        getInfoBandPass(x, this instanceof ChebyBandStop);
        getInfoCheby(x);
    }
}
