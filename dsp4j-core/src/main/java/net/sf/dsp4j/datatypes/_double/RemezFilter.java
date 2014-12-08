/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.octave.packages.signal_1_0_11.Remez;

/**
 *
 * @author aploese
 */
public class RemezFilter extends AbstractDoubleFirFilter {

    int order;
    double[] frequencies;
    double[] response;
    double[] weight;
    Remez.FType ftype;
    int griddensity;


    public RemezFilter(int order, double[] f, double[] a, Remez.FType ftype) {
        this(order, f, a, Remez.defaultWeight(a), ftype, Remez.DEFAULT_GRID_DENSITY);
    }

    public RemezFilter(int order, double[] f, double[] a, double[] w) {
        this(order, f, a, w, Remez.FType.BAND_PASS, Remez.DEFAULT_GRID_DENSITY);
    }

    public RemezFilter(int order, double[] f, double[] a) {
        this(order, f, a, Remez.defaultWeight(a), Remez.FType.BAND_PASS, Remez.DEFAULT_GRID_DENSITY);
    }

    public RemezFilter(int order, double[] f, double[] a, double[] w, Remez.FType ftype, int griddensity) {
        this.order = order;
        this.frequencies = f;
        this.response = a;
        this.weight = w;
        this.ftype = ftype;
        this.griddensity = griddensity;
    }

    protected double[] getW() {
        double[] result = new double[frequencies.length + 1];
        for (int i = 0; i < frequencies.length; i++) {
            result[i] = 2.0 * frequencies[i] / getSampleRate();
        }
        result[result.length -1] = 1; // add last frq at PI
        return result;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        if (sampleRate == getSampleRate()) {
            return;
        }
        super.setSampleRate(sampleRate);
        init();
    }

    void setFc(int order, double fc, double with, boolean high) {
        this.order = order;
        frequencies = new double[] {0, fc * (1 - with), fc * (1 + with)};
        if (high) {
            response = new double[] {0,0,1,1};
        } else {
            response = new double[] {1,1,0,0};
        }
        this.weight =  Remez.defaultWeight(response);
        init();
    }

    private void init() {
        setCoeff(new Remez(order, getW(), response, weight, ftype, griddensity).getB());
    }

}
