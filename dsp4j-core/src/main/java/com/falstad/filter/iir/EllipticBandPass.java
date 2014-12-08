package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class EllipticBandPass extends EllipticFilterType {

    @Override public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        int s = selectBandPass(auxBars, auxLabels);
        auxBars[2].setValue(5);
        selectElliptic(s, auxBars, auxLabels);
        return s + 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        setupBandPass(auxBars, auxLabels);
        setupElliptic(3, auxBars, auxLabels);
    }

    @Override
    public Complex getPole(int i) {
        return getBandPassPole(i);
    }

    @Override
    public Complex getZero(int i) {
        Complex result = getEllipticZero(i / 2, Math.PI * .5);
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
        x[0] = "Elliptic (IIR), " + getPoleCount() + "-pole";
        getInfoBandPass(x, this instanceof EllipticBandStop);
        getInfoElliptic(x);
    }
}
