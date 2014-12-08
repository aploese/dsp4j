package com.falstad.filter.iir;

import com.falstad.filter.Complex;
import com.falstad.filter.CascadeFilter;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import com.falstad.filter.FilterType;
import java.awt.Label;
import java.awt.Scrollbar;

public abstract class IIRFilterType extends FilterType {

    Complex[] response;

    @Override
    public Complex getResponse(double w) {
        if (response == null) {
            return new Complex();
        }
        int off = (int) (response.length * w / Math.PI);
        off &= ~1;
        if (off < 0) {
            off = 0;
        }
        if (off >= response.length) {
            off = response.length - 1;
        }
        return response[off];
    }

    protected void setResponse(DirectFilter f) {
        response = new Complex[8192 / 2];
        if (f.getB(0) != 1) {
            throw new RuntimeException("Wrong B");
        }

        if (f.aLength() != f.bLength()) {
            System.out.println("length mismatch " + f.aLength()
                    + " " + f.bLength());
        }
        // use the coefficients to multiply out the transfer function for
        // various values of z

        double maxresp = 0;
        for (int j = 0; j < response.length; j++) {
            final Complex top = new Complex();
            final Complex bottom = new Complex();
            for (int i = 0; i != f.aLength(); i++) {
                final Complex czn = new Complex();
                czn.setMagPhase(1, -Math.PI * j * f.getN(i) / response.length);
                top.addMult(f.getA(i), czn);
                bottom.addMult(f.getB(i), czn);
            }
            top.div(bottom);
            if (top.getMagnitude() > maxresp) {
                maxresp = top.getMagnitude();
            }
            response[j] = top;
        }
        // normalize response
        for (int j = 0; j != response.length; j++) {
            response[j].div(maxresp);
        }
        for (int j = 0; j != f.aLength(); j++) {
            f.setA(j, f.getA(j) / maxresp);
        }
        //System.out.println(f.aList.length + " " + f.bList.length + " XX");
    }

    protected void setResponse(CascadeFilter f) {
        // it's good to have this bigger for normalization
        response = new Complex[4096 / 2];
        double maxresp = 0;

        // use the coefficients to multiply out the transfer function for
        // various values of z
        //System.out.println("sr1");
        for (int j = 0; j != response.length; j++) {
            final Complex ch = new Complex(1, 0);
            final Complex cbot = new Complex(1, 0);
            final Complex czn1 = new Complex();
            czn1.setMagPhase(1, -Math.PI * j / response.length);
            final Complex czn2 = new Complex();
            czn2.setMagPhase(1, -Math.PI * j * 2 / response.length);
            for (int i = 0; i != f.getSize(); i++) {
                final Complex ct = new Complex(f.getB0(i), 0);
                final Complex cb = new Complex(1, 0);
                ct.addMult(f.getB1(i), czn1);
                cb.addMult(-f.getA1(i), czn1);
                ct.addMult(f.getB2(i), czn2);
                cb.addMult(-f.getA2(i), czn2);
                ch.mult(ct);
                cbot.mult(cb);
            }
            ch.div(cbot);
            if (ch.getMagnitude() > maxresp) {
                maxresp = ch.getMagnitude();
            }
            response[j] = ch;
        }
        //System.out.println("sr2");
        // normalize response
        for (int j = 0; j != response.length; j++) {
            response[j].div(maxresp);
        }
        f.setB0(0, f.getB0(0) / maxresp);
        f.setB1(0, f.getB1(0) / maxresp);
        f.setB2(0, f.getB2(0) / maxresp);

        //System.out.println(f.aList.length + " " + f.bList.length + " XX");
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        CascadeFilter f = new CascadeFilter((getPoleCount() + 1) / 2);
        for (int i = 0; i < getPoleCount(); i++) {
            Complex pole = getPole(i);
            //System.out.println("pole " + i + " " + c1.re + " " + c1.im);
            if (Math.abs(pole.getIm()) < 1e-6) {
                pole.setIm(0);
            }
            if (pole.getIm() < 0) {
                continue;
            }
            if (pole.getIm() == 0) {
                f.setAStage(pole.getRe(), 0);
                //System.out.println("real pole " + i + " " + c1.re + " " + c1.im);
            } else {
                f.setAStage(2 * pole.getRe(), -pole.magSquared());
            }
        }
        for (int i = 0; i < getZeroCount(); i++) {
            Complex zero = getZero(i);
            //TODO Double.MIN_VALUE ???
            //System.out.println("zero " + i + " " + c1.re + " " + c1.im);
            if (Math.abs(zero.getIm()) < 1e-6) {
                zero.setIm(0);
            }
            if (zero.getIm() < 0) {
                continue;
            }
            if (zero.getIm() == 0) {
                f.setBStage(-zero.getRe(), 1, 0);
            } else {
                f.setBStage(zero.magSquared(), -2 * zero.getRe(), 1);
            }
        }
        setResponse(f);
        return f;
    }
}
