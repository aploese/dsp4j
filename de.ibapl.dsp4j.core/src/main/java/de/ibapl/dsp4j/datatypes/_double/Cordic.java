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

import de.ibapl.dsp4j.In;

/**
 *
 * @author aploese
 */
public class Cordic implements FunctionAtan {

    private double phiY;
    private double magnitudeY;
    private double reY;
    private double imY;
    private int n;
    private double b[];
    private double arctTan_b[];
    private double kn[];

    public Cordic() {
        this(15);
    }

    public Cordic(int n) {
        setN(n);
    }

    private boolean sign(double value) {
        return value >= 0;
    }

    @In
    public double setReIm(double re, double im) {
        phiY = 0;
        for (int i = 0; i < n; i++) {
            if ((sign(re) == sign(im))) {
                phiY += arctTan_b[i];
                final double re_New = re + im * b[i];
                im -= re * b[i];
                re = re_New;
            } else {
                phiY -= arctTan_b[i];
                final double re_New = re - im * b[i];
                im += re * b[i];
                re = re_New;
            }
        }
        re *= kn[n - 1];
        magnitudeY = Math.abs(re);
        if (re < 0) {
            if (phiY < 0) {
                phiY += Math.PI;
            } else {
                phiY -= Math.PI;
            }
        }
        return phiY;
    }

    @In
    public void setMagPhi(double magnitude, double phi) {
        imY = 0;
        reY = 1.0;
        boolean flipSign;
        if (phi < -Math.PI / 2) {
            phi += Math.PI;
            flipSign = true;
        } else if (phi > Math.PI / 2) {
            phi -= Math.PI;
            flipSign = true;
        } else {
            flipSign = false;
        }

        for (int i = 0; i < n; i++) {
            if (phi < 0) {
                phi += arctTan_b[i];
                final double re_New = reY + imY * b[i];
                imY -= reY * b[i];
                reY = re_New;
            } else {
                phi -= arctTan_b[i];
                final double re_New = reY - imY * b[i];
                imY += reY * b[i];
                reY = re_New;
            }
        }
        if (flipSign) {
            reY *= -kn[n - 1];
            imY *= -kn[n - 1];
        } else {
            reY *= kn[n - 1];
            imY *= kn[n - 1];
        }
    }

    private void setN(int n) {
        this.n = n;
        b = new double[n];
        arctTan_b = new double[n];
        kn = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = Math.pow(2, -i);
            arctTan_b[i] = Math.atan(b[i]);
            if (i == 0) {
                kn[i] = Math.cos(arctTan_b[i]);
            } else {
                kn[i] = kn[i - 1] * Math.cos(arctTan_b[i]);
            }
        }
    }

    /**
     * @return the phiY
     */
    public double getPhiY() {
        return phiY;
    }

    /**
     * @return the magnitudeY
     */
    public double getMagnitudeY() {
        return magnitudeY;
    }

    /**
     * @return the reY
     */
    public double getReY() {
        return reY;
    }

    /**
     * @return the imY
     */
    public double getImY() {
        return imY;
    }

    /**
     * @return the n
     */
    public int getN() {
        return n;
    }

    @Override
    public double aTan(double re, double im) {
        return setReIm(re, im);
    }
}
