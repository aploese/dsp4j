package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class ChebyLowPass extends ChebyFilterType {

    public ChebyLowPass() {
        sign = 1;
    }

    @Override public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        int s = selectLowPass(auxBars, auxLabels);
        selectCheby(s++, auxBars, auxLabels);
        return s;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        setupLowPass(auxBars, auxLabels);
        setupCheby(2, auxBars, auxLabels);
    }

    @Override
    public Complex getPole(int i) {
        Complex result = super.getPole(i);
        result.mult(sign);
        return result;
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
        x[0] = "Chebyshev (IIR), " + getPoleCount() + "-pole";
        getInfoLowPass(x);
        getInfoCheby(x);
    }
}
