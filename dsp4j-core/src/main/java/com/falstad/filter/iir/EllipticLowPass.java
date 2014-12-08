package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class EllipticLowPass extends EllipticFilterType {

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        int s = selectLowPass(auxBars, auxLabels);
        selectElliptic(s, auxBars, auxLabels);
        return s + 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        setupLowPass(auxBars, auxLabels);
        setupElliptic(2, auxBars, auxLabels);
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Elliptic (IIR), " + getPoleCount() + "-pole";
        getInfoLowPass(x);
        getInfoElliptic(x);
    }

    @Override
    public Complex getZero(int i) {
        return getEllipticZero(i, wc);
    }
}
