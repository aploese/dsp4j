package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class CustomIIRFilter extends IIRFilterType {

    Complex[] poles;
    Complex[] zeros;

    @Override public
    int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("# of Pole Pairs");
        auxBars[0].setMaximum(10);
        auxBars[0].setValue(getPoleCount() / 2);
        return 1;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        poles = new Complex[auxBars[0].getValue() * 2];
        zeros = new Complex[auxBars[0].getValue() * 2];
    }

    @Override
    public Complex getPole(int i) {
        return new Complex(poles[i]);
    }

    @Override
    public int getPoleCount() {
        return poles.length;
    }

    @Override
    public Complex getZero(int i) {
        return new Complex(zeros[i]);
    }

    @Override
    public int getZeroCount() {
        return zeros.length;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Custom IIR";
        x[1] = getPoleCount() + " poles and zeros";
    }

    public void editPoleZero(int index, Complex c) {
        if (c.getMagnitude() > 1.1) {
            return;
        }
        if (index != -1) {
            poles[index].set(c);
            poles[index ^ 1].set(c.getRe(), -c.getIm());
        }
        if (index != -1) {
            zeros[index].set(c);
            zeros[index ^ 1].set(c.getRe(), -c.getIm());
        }
    }
}
