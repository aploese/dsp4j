/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.In;

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
