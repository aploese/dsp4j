package com.falstad.filter.iir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class PluckedStringFilter extends IIRFilterType {

    private int n;
    private double mult;

    @Override public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Fundamental");
        auxBars[0].setValue(20);
        auxLabels[1].setText("Sharpness");
        auxBars[1].setValue(970);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = 2000 / auxBars[0].getValue();
        mult = .5 * Math.exp(-.5 + auxBars[1].getValue() / 2000.);
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(4, 4, 4);
        f.setA(0,1);
        f.setA(1,1);
        f.setA(2,0);
        f.setA(3,0);
         f.setB(0,1);
         f.setB(1,0);
         f.setB(2,-mult);
         f.setB(3,-mult);
         f.setN(0,0);
         f.setN(1,1);
         f.setN(2,n);
         f.setN(3,n+1);

        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Plucked String (IIR); Resonance every " + getOmegaText(2 * Math.PI / n);
        x[1] = "Delay: " + n + " samples, "
                + getUnitText(n / (double) sampleRate, "s");
    }
}
