/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.signal_1_0_11;

import net.sf.dsp4j.octave_3_2_4.m.polynomial.Roots;
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
