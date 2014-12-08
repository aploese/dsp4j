package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class ButterLowPass extends ButterFilterType {

    protected int sign;

    public ButterLowPass() {
        sign = 1;
    }

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        return selectLowPass(auxBars, auxLabels);
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        setupLowPass(auxBars, auxLabels);
    }

    @Override
    public Complex getZero(int i) {
        return new Complex(-sign, 0);
    }

    @Override
    public int getPoleCount() {
        return n;
    }

    @Override
    public int getZeroCount() {
        return n;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Butterworth (IIR), " + getPoleCount() + "-pole";
        getInfoLowPass(x);
    }
}
