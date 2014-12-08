package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import java.awt.Label;
import java.awt.Scrollbar;

public class ResonatorFilter extends IIRFilterType {

    private double r;
    private double wc;


    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Resonant Frequency");
        auxBars[0].setValue(500);
        auxLabels[1].setText("Sharpness");
        auxBars[1].setValue(900);
        return 2;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        wc = auxBars[0].getValue() * Math.PI / 1000.;
        double rolldb = -auxBars[1].getValue() * 3 / 1000.;
        r = 1 - Math.pow(10, rolldb);
    }

    @Override
    public Complex getPole(int i) {
        Complex result = new Complex();
        result.setMagPhase(r, (i == 1) ? -wc : wc);
        return result;
    }

    @Override
    public int getPoleCount() {
        return 2;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Reson (IIR)";
        x[1] = "Res. Frequency: " + getOmegaText(wc);
    }

    /**
     * @return the resonant frequency
     */
    public double getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(double r) {
        this.r = r;
    }

    /**
     * @return the wc
     */
    public double getWc() {
        return wc;
    }

    /**
     * @param wc the wc to set
     */
    public void setWc(double wc) {
        this.wc = wc;
    }
}
