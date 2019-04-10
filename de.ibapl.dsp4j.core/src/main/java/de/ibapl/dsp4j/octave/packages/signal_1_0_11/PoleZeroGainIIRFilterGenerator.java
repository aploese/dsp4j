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
package de.ibapl.dsp4j.octave.packages.signal_1_0_11;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public class PoleZeroGainIIRFilterGenerator {

    public final static Complex IMAG_ONE = new Complex(0.0, 1);
    protected Complex[] pole;
    protected Complex[] zero;
    protected double gain;
    protected final double[] W;
    protected final boolean stop;
    protected final double T;

    public PoleZeroGainIIRFilterGenerator(double[] W, boolean digital, boolean stop) {
        this.stop = stop;
        if ((W.length == 2) && !(W[0] < W[1])) {
            throw new RuntimeException("butter: first band edge must be smaller than second");
        }
        if (digital) {
            for (double d : W) {
                if ((d < 0) || (d > 1.0)) {
                    throw new RuntimeException("butter: critical frequencies must be in (0 1)");
                }
            }
        } else {
            for (double d : W) {
                if (d < 0) {
                    throw new RuntimeException("butter: critical frequencies must be in (0 inf)");
                }
            }
        }
        // Prewarp to the band edges to s plane
        if (digital) {
            this.W = new double[W.length];
            T = 2; // sampling frequency of 2 Hz
            for (int i = 0; i < this.W.length; i++) {
                this.W[i] = 2.0 / T * Math.tan(Math.PI * W[i] / T);
            }
        } else {
            this.W = W;
            T = 0;
        }
    }

    /**
     * @return the pole
     */
    public Complex[] getPole() {
        return pole;
    }

    /**
     * @param pole the pole to set
     */
    protected void setPole(Complex[] pole) {
        this.pole = pole;
    }

    /**
     * @return the zero
     */
    public Complex[] getZero() {
        return zero;
    }

    /**
     * @return the gain
     */
    public double getGain() {
        return gain;
    }

    /**
     * @return the W
     */
    public double[] getW() {
        return W;
    }

    /**
     * @return the stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * @return the T
     */
    public double getT() {
        return T;
    }
}
