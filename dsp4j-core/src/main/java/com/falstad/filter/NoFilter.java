package com.falstad.filter;

import java.awt.Label;
import java.awt.Scrollbar;

public class NoFilter extends FilterType {

    @Override
    public Complex getResponse(double w) {
        return new Complex(1, 0);
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(1, 0, 0);
        f.setA(0, 1);
        return f;
    }
}
