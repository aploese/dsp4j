package com.falstad.filter;

public class Complex implements Cloneable {

    private double re;
    private double im;

    public Complex() {
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex(Complex c) {
        this.re = c.re;
        this.im = c.im;
    }

    public double magSquared() {
        return Math.pow(getMagnitude(), 2);
    }

    public void set(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public void set(double re) {
        this.re = re;
        this.im = 0;
    }

    public void set(Complex c) {
        re = c.re;
        im = c.im;
    }

    public Complex add(double re) {
        this.re += re;
        return this;
    }

    public static Complex add(Complex c, double re) {
        return c.clone().add(re);
    }

    public Complex add(double re, double im) {
        this.re += re;
        this.im += im;
        return this;
    }

    public static Complex add(Complex c, double re, double im) {
        return c.clone().add(re, im);
    }

    public Complex add(Complex c) {
        re += c.re;
        im += c.im;
        return this;
    }

    public static Complex add(Complex c0, Complex c1) {
        return c0.clone().add(c1);
    }

    public void addMult(double x, Complex z) {
        re += z.re * x;
        im += z.im * x;
    }

    public Complex square() {
        set(re * re - im * im, 2 * re * im);
        return this;
    }

    public static Complex square(Complex c) {
        return c.clone().square();
    }

    public Complex sqrt() {
        setMagPhase(Math.sqrt(getMagnitude()), getPhase() * 0.5);
        return this;
    }

    public static Complex sqrt(Complex c) {
        return c.clone().sqrt();
    }

    public Complex mult(double re, double im) {
        set(this.re * re - this.im * im, this.re * im + this.im * re);
        return this;
    }

    public static Complex mult(Complex c, double re, double im) {
        return c.clone().mult(re, im);
    }

    public Complex mult(double re) {
        this.re *= re;
        this.im *= re;
        return this;
    }

    public static Complex mult(Complex c, double re) {
        return c.clone().mult(re);
    }

    public Complex mult(Complex c) {
        mult(c.re, c.im);
        return this;
    }

    public static Complex mult(Complex c0, Complex c1) {
        return c0.clone().mult(c1);
    }

    public double getMagnitude() {
        return Math.sqrt(re * re + im * im);
    }

    public double getPhase() {
        return Math.atan2(im, re);
    }

    public static Complex createMagPhase(double m, double p) {
        Complex result = new Complex();
        result.setMagPhase(m, p);
        return result;
    }

    public void setMagPhase(double m, double ph) {
        re = m * Math.cos(ph);
        im = m * Math.sin(ph);
    }

    public Complex recip() {
        double n = re * re + im * im;
        set(re / n, -im / n);
        return this;
    }

    public static Complex recip(Complex c) {
        return c.clone().recip();
    }

    public Complex div(Complex c) {
        div(c.re, c.im);
        return this;
    }

    public static Complex div(Complex c0, Complex c1) {
        return c0.clone().div(c1);
    }

    public Complex rotate(double phase) {
        setMagPhase(getMagnitude(), (getPhase() + phase) % (2 * Math.PI));
        return this;
    }

    public static Complex rotate(Complex c, double phase) {
        return c.clone().rotate(phase);
    }

    public Complex conjugate() {
        im = -im;
        return this;
    }

    public static Complex conjugate(Complex c) {
        return c.clone().conjugate();
    }

    public static Complex pow(Complex c0, double p) {
        return c0.clone().pow(p);
    }

    /**
     * @return the re
     */
    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    /**
     * @return the im
     */
    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public Complex div(double re) {
        this.re /= re;
        this.im /= re;
        return this;
    }

    public static Complex div(Complex c, double re) {
        return c.clone().div(re);
    }

    @Override
    public Complex clone() {
        return new Complex(re, im);
    }

    @Override
    public String toString() {
        return String.format("%s %si", Double.toString(re), Double.toString(im));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.re) ^ (Double.doubleToLongBits(this.re) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.im) ^ (Double.doubleToLongBits(this.im) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Complex other = (Complex) obj;
        if (Double.doubleToLongBits(this.re) != Double.doubleToLongBits(other.re)) {
            return false;
        }
        if (Double.doubleToLongBits(this.im) != Double.doubleToLongBits(other.im)) {
            return false;
        }
        return true;
    }

    public Complex subtract(double re, double im) {
        this.re -= re;
        this.im -= im;
        return this;
    }

    public static Complex subtract(Complex c0, double re, double im) {
        return c0.clone().subtract(re, im);
    }

    public Complex subtract(Complex c) {
        this.re -= c.re;
        this.im -= c.im;
        return this;
    }

    public static Complex subtract(Complex c0, Complex c1) {
        return c0.clone().subtract(c1);
    }

    public Complex negate() {
        this.re = -this.re;
        this.im = -this.im;
        return this;
    }

    public static Complex negate(Complex c) {
        return c.clone().negate();
    }

    public Complex pow(Complex x) {
        this.log().mult(x).exp();
        return this;
    }

    public Complex pow(double x) {
        this.log().mult(x).exp();
        return this;
    }

    public Complex log() {
        re = Math.log(abs());
        im = Math.atan2(im, re);
        return this;
    }

    public Complex exp() {
        double expReal = Math.exp(re);
        this.re = expReal * Math.cos(im);
        this.im = expReal * Math.sin(im);
        return this;
    }

    public double abs() {
        if (Math.abs(re) < Math.abs(im)) {
            if (im == 0.0) {
                return Math.abs(re);
            }
            double q = re / im;
            return (Math.abs(im) * Math.sqrt(1 + q * q));
        } else {
            if (re == 0.0) {
                return Math.abs(im);
            }
            double q = im / re;
            return (Math.abs(re) * Math.sqrt(1 + q * q));
        }
    }

    public Complex subtract(double re) {
        this.re -= re;
        return this;
    }

    public static Complex subtract(Complex c, double re) {
        return c.clone().subtract(re);
    }

    public Complex div(double re, double im) {
        if (Math.abs(re) < Math.abs(im)) {
            if (im == 0.0) {
                this.re /= re;
                this.im /= re;
                return this;
            }
            double q = re / im;
            double denominator = re * q + im;
            this.re *= (q + this.im) / denominator;
            this.im *= (q - this.re) / denominator;
        } else {
            if (re == 0.0) {
                this.re = this.im / im;
                this.im = -this.re / im;
            }
            double q = im / re;
            double denominator = im * q + re;
            this.re = (this.im * q + this.re) / denominator;
            this.im = (this.im - this.re * q) / denominator;
            return this;
        }
        return this;
    }
}
