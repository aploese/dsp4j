package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class CombFilter extends IIRFilterType {

    protected int n;
    protected  int sign;
    protected double mult;
    protected double peak;

    public CombFilter(int s) {
        sign = s;
    }

    @Override public
    int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("1st Pole");
        auxBars[0].setValue(60);
        auxLabels[1].setText("Sharpness");
        auxBars[1].setValue(700);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = 2000 / auxBars[0].getValue();
        mult = auxBars[1].getValue() / 1000.;
        peak = 1 / (1 - mult);
    }

    @Override
    public Complex getPole(int i) {
        int odd = (sign == 1) ? 0 : 1;
        Complex result = new Complex();
        result.setMagPhase(Math.pow(mult, 1. / n), Math.PI * (odd + 2 * i) / n);
        return result;
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(2, 2, 2);
        f.setA(0,1 / peak);
        f.setA(1, 0);
        f.setB(0, 0);
        f.setB(1, -sign * mult);
        f.setN(0, 0);
        f.setN(1, n);
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Comb (IIR); Resonance every " + getOmegaText(2 * Math.PI / n);
        x[1] = "Delay: " + n + " samples, "
                + getUnitText(n / (double) sampleRate, "s");
        double tl = 340. * n / (sampleRate * 2);
        x[2] = "Tube length: " + getUnitText(tl, "m");
        if (sign == -1) {
            x[2] += " (closed)";
        } else {
            x[2] += " (open)";
        }
    }

    @Override
    public int getPoleCount() {
        return n;
    }

    @Override
    public int getZeroCount() {
        return n;
    }

    /**
     * @return the n
     */
    public int getN() {
        return n;
    }
}
