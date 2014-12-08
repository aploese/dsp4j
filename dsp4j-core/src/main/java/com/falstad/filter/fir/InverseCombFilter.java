package com.falstad.filter.fir;

import com.falstad.filter.Complex;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class InverseCombFilter extends FIRFilterType {

    private int n;
    private double mult;
    private double peak;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("2nd Zero");
        auxBars[0].setValue(60);
        auxLabels[1].setText("Sharpness");
        auxBars[1].setValue(1000);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = 1990 / auxBars[0].getValue();
        mult = auxBars[1].getValue() / 1000.;
        peak = 1 + mult;
    }

    @Override
    public Complex getZero(int i) {
        Complex result = new Complex();
        result.setMagPhase(Math.pow(mult, 1. / n), Math.PI * 2 * i / n);
        return result;
    }

    @Override
    public int getZeroCount() {
        return n;
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(2, 0,2);
        f.setA(0, 1 / peak);
        f.setA(1, -mult / peak);
        f.setN(0, 0);
        f.setN(1, n);
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Inverse Comb (FIR)";
        x[1] = "Zeros every " + getOmegaText(2 * Math.PI / n);
    }
}
