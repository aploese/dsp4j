package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class InvChebyLowPass extends InvChebyFilterType {

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
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
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Inverse Chebyshev (IIR), " + getPoleCount() + "-pole";
        getInfoLowPass(x);
        getInfoCheby(x);
    }

    @Override
    public Complex getZero(int i) {
        return getChebyZero(i, wc);
    }
}
