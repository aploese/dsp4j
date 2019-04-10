/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.datatypes._double;

import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Remez;

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
