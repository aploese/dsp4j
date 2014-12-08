package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class DelayFilter extends CombFilter {

    public DelayFilter() {
        super(1);
    }

    @Override
    public Complex getResponse(double w) {
        if (n > 212) {
            return new Complex(1, 0);
        } else {
            return super.getResponse(w);
        }
    }

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Delay");
        auxBars[0].setValue(300);
        auxLabels[1].setText("Strength");
        auxBars[1].setValue(700);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        n = auxBars[0].getValue() * 16384 / 1000;
        mult = auxBars[1].getValue() / 1250.;
        peak = 1 / (1 - mult);
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Delay (IIR)";
        x[1] = "Delay: " + n + " samples, "
                + getUnitText(n / (double) sampleRate, "s");
        double tl = 340. * n / sampleRate / 2;
        x[2] = "Echo Distance: " + getUnitText(tl, "m");
        if (tl > 1) {
            x[2] += " (" + showFormat.format(tl * 3.28084) + " ft)";
        }
    }
}
