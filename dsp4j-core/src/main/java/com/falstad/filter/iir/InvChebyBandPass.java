package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class InvChebyBandPass extends InvChebyFilterType {

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
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
        Complex result = getChebyZero(i / 2, Math.PI * .5);
        bandPassXform(i, result);
        return result;
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
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Inv Cheby (IIR), " + getPoleCount() + "-pole";
        getInfoBandPass(x, this instanceof InvChebyBandStop);
        getInfoCheby(x);
    }
}
