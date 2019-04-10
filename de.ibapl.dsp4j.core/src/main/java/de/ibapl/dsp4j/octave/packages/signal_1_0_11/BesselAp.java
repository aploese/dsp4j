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

import de.ibapl.dsp4j.octave_3_2_4.m.polynomial.Roots;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author aploese
 */
public class BesselAp {
    final private Complex[] pole;
    final private Complex[] zero;
    final private double gain;

    
        public BesselAp(int n) {

        if (n < 0) {
            throw new IllegalArgumentException("bessel: filter order n must be a positive integer");
        }

        RealVector p0 = new ArrayRealVector(1, 1.0);
        RealVector p1 = new ArrayRealVector(2, 1.0);
        for (int i = 2; i <= n; i++) {
            RealVector px = p1.mapMultiply(2 * i - 1);
            RealVector py = p0.append(new ArrayRealVector(new double[]{0, 0}, false));
            if (px.getDimension() < py.getDimension()) {
                px = new ArrayRealVector(new double[py.getDimension() - px.getDimension()], (ArrayRealVector)px);
            } else if (py.getDimension() < px.getDimension()) {
                py = new ArrayRealVector(new double[px.getDimension() - py.getDimension()], (ArrayRealVector)py);
            }
            p0 = p1;
            p1 = px.add(py);
        }
        //% p1 now contains the reverse bessel polynomial for n

        //% scale it by replacing s->s/w0 so that the gain becomes 1

        final int p1n = p1.getDimension();
        for (int i = 0; i < p1n; i++) {
            p1.setEntry(i, p1.getEntry(i) * Math.pow(p1.getEntry(p1n - 1), (p1n - 1.0 - i) / (p1n - 1.0)));
        }

        zero = new Complex[0];
        pole = Roots.roots(p1);
        gain = 1;

    }

    /**
     * @return the pole
     */
    public Complex[] getPole() {
        return pole;
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

}
