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
package de.ibapl.dsp4j.datatypes;

import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Bessel;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Bilinear;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Butter;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Cheby1;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Cheby2;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.PoleZeroGainIIRFilterGenerator;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.SfTrans;
import de.ibapl.dsp4j.octave.packages.signal_1_2_0.ButtOrd;
import de.ibapl.dsp4j.octave.packages.signal_1_2_0.Cheb1Ord;
import de.ibapl.dsp4j.octave.packages.signal_1_2_0.Cheb2Ord;
import de.ibapl.dsp4j.octave.packages.signal_1_2_0.Ellip;
import de.ibapl.dsp4j.octave.packages.signal_1_2_0.EllipOrd;
import de.ibapl.dsp4j.octave.packages.signal_1_2_0.Zp2Sos;
import static de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn.real;
import de.ibapl.dsp4j.octave_3_2_4.m.polynomial.Poly;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public abstract class IirFilterGenerator {

    private double samplerate;

    public IirFilterGenerator(double sampleRate) {
        this.samplerate = sampleRate;
    }

    public IirFilterGenerator() {
    }

    protected double getW(double f) {
        return 2.0 * f / samplerate;
    }

    private <F extends IirFilter> F doTransformSAndZ(PoleZeroGainIIRFilterGenerator pzg, Class<F> clazz) {
        SfTrans sfTrans = new SfTrans(pzg.getZero(), pzg.getPole(), pzg.getGain(), pzg.getW(), pzg.isStop());
        return doTransformZ(sfTrans, pzg.getT(), clazz);
    }

    public abstract <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz);

    public abstract <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz);

    protected <F extends IirFilter> F doTransformZ(SfTrans sfTrans, double T, Class<F> clazz) {
        Bilinear bilinear = new Bilinear(sfTrans.getSZero(), sfTrans.getSPole(), sfTrans.getSGain(), T);

        if (DirectIirFilter.class.isAssignableFrom(clazz)) {
            double[] a = real(Poly.poly(bilinear.getZPole()));

            Complex[] bCplx = Poly.poly(bilinear.getZZero());
            for (int i = 0; i < bCplx.length; i++) {
                bCplx[i] = bCplx[i].multiply(bilinear.getZGain());
            }

            double[] b = real(bCplx);
            if (a.length != b.length) {
                throw new RuntimeException("a and b must be of equal length!");
            }
            return (F) createDirectFilter(a, b, (Class<DirectIirFilter>) clazz);
        }
        if (CascadedIirFilter.class.isAssignableFrom(clazz)) {
            Zp2Sos zp2Sos = new Zp2Sos(bilinear.getZZero(), bilinear.getZPole(), bilinear.getZGain());
            return (F) createCascadedFilter(zp2Sos.getSos(), zp2Sos.getGain(), (Class<CascadedIirFilter>) clazz);
        }
        throw new RuntimeException();
    }

    public <F extends IirFilter> F getLP_BesselFc(int n, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Bessel(n, getW(fc), true, false), clazz);
    }

    public <F extends IirFilter> F getHP_BesselFc(int n, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Bessel(n, getW(fc), true, true), clazz);
    }

    public <F extends IirFilter> F getBP_BesselFc(int n, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Bessel(n, getW(fl), getW(fh), true, false), clazz);
    }

    public <F extends IirFilter> F getBS_BesselFc(int n, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Bessel(n, getW(fl), getW(fh), true, true), clazz);
    }

    public <F extends IirFilter> F getLP_ButterFc(int n, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Butter(n, getW(fc), true, false), clazz);
    }

    public <F extends IirFilter> F getLP_ButterFpFs(double rp, double rs, double fp, double fs, Class<F> clazz) {
        ButtOrd ord = new ButtOrd(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Butter(ord.getN(), ord.getWc(0), true, false), clazz);
    }

    public <F extends IirFilter> F getHP_ButterFc(int n, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Butter(n, getW(fc), true, true), clazz);
    }

    public <F extends IirFilter> F getHP_ButterFpFs(double rp, double rs, double fp, double fs, Class<F> clazz) {
        ButtOrd ord = new ButtOrd(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Butter(ord.getN(), ord.getWc(0), true, true), clazz);
    }

    public <F extends IirFilter> F getBP_ButterFc(int n, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Butter(n, getW(fl), getW(fh), true, false), clazz);
    }

    public <F extends IirFilter> F getBP_ButterFpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        ButtOrd ord = new ButtOrd(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Butter(ord.getN(), ord.getWc(0), ord.getWc(1), true, false), clazz);
    }

    public <F extends IirFilter> F getBS_ButterFc(int n, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Butter(n, getW(fl), getW(fh), true, true), clazz);
    }

    public <F extends IirFilter> F getBS_ButterFpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        ButtOrd ord = new ButtOrd(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Butter(ord.getN(), ord.getWc(0), ord.getWc(1), true, true), clazz);
    }

    public <F extends IirFilter> F getLP_Cheby1Fc(int n, double rp, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Cheby1(n, rp, getW(fc), true, false), clazz);
    }

    public <F extends IirFilter> F getLP_Cheby1FpFs(double rp, double rs, double fp, double fs, Class<F> clazz) {
        Cheb1Ord ord = new Cheb1Ord(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Cheby1(ord.getN(), rp, ord.getWc(0), true, false), clazz);
    }

    public <F extends IirFilter> F getHP_Cheby1Fc(int n, double rp, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Cheby1(n, rp, getW(fc), true, true), clazz);
    }

    public <F extends IirFilter> F getHP_Cheby1FpFs(double rp, double rs, double fp, double fs, Class<F> clazz) {
        Cheb1Ord ord = new Cheb1Ord(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Cheby1(ord.getN(), rp, ord.getWc(0), true, true), clazz);
    }

    public <F extends IirFilter> F getBP_Cheby1Fc(int n, double rp, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Cheby1(n, rp, getW(fl), getW(fh), true, false), clazz);
    }

    public <F extends IirFilter> F getBP_Cheby1FpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        Cheb1Ord ord = new Cheb1Ord(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Cheby1(ord.getN(), rp, ord.getWc(0), ord.getWc(1), true, false), clazz);
    }

    public <F extends IirFilter> F getBS_Cheby1Fc(int n, double rp, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Cheby1(n, rp, getW(fl), getW(fh), true, true), clazz);
    }

    public <F extends IirFilter> F getBS_Cheby1FpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        Cheb1Ord ord = new Cheb1Ord(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Cheby1(ord.getN(), rp, ord.getWc(0), ord.getWc(1), true, true), clazz);
    }

    public <F extends IirFilter> F getLP_Cheby2Fc(int n, double rs, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Cheby2(n, rs, getW(fc), true, false), clazz);
    }

    public <F extends IirFilter> F getLP_Cheby2FpFs(double rp, double rs, double fp, double fs, Class<F> clazz) {
        Cheb2Ord ord = new Cheb2Ord(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Cheby2(ord.getN(), rs, ord.getWc(0), true, false), clazz);
    }

    public <F extends IirFilter> F getHP_Cheby2Fc(int n, double rs, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Cheby2(n, rs, getW(fc), true, true), clazz);
    }

    public <F extends IirFilter> F getHP_Cheby2FpFs(double rp, double rs, double fp, double fs, Class<F> clazz) {
        Cheb2Ord ord = new Cheb2Ord(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Cheby2(ord.getN(), rs, ord.getWc(0), true, true), clazz);
    }

    public <F extends IirFilter> F getBP_Cheby2Fc(int n, double rs, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Cheby2(n, rs, getW(fl), getW(fh), true, false), clazz);
    }

    public <F extends IirFilter> F getBP_Cheby2FpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        Cheb2Ord ord = new Cheb2Ord(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Cheby2(ord.getN(), rs, ord.getWc(0), ord.getWc(1), true, false), clazz);
    }

    public <F extends IirFilter> F getBS_Cheby2Fc(int n, double rs, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Cheby2(n, rs, getW(fl), getW(fh), true, true), clazz);
    }

    public <F extends IirFilter> F getBS_Cheby2FpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        Cheb2Ord ord = new Cheb2Ord(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Cheby2(ord.getN(), rs, ord.getWc(0), ord.getWc(1), true, true), clazz);
    }

    public <F extends IirFilter> F getLP_EllipFc(int n, double rp, double rs, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Ellip(n, rp, rs, getW(fc), true, false), clazz);
    }

    public <F extends IirFilter> F getLP_EllipFpFs(double fp, double fs, double rp, double rs, Class<F> clazz) {
        EllipOrd ord = new EllipOrd(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Ellip(ord.getN(), rp, rs, ord.getWp(0), true, false), clazz);
    }

    public <F extends IirFilter> F getHP_EllipFc(int n, double rp, double rs, double fc, Class<F> clazz) {
        return doTransformSAndZ(new Ellip(n, rp, rs, getW(fc), true, true), clazz);
    }

    public <F extends IirFilter> F getHP_EllipFpFs(double fp, double fs, double rp, double rs, Class<F> clazz) {
        EllipOrd ord = new EllipOrd(getW(fp), getW(fs), rp, rs);
        return doTransformSAndZ(new Ellip(ord.getN(), rp, rs, ord.getWp(0), true, true), clazz);
    }

    public <F extends IirFilter> F getBP_EllipFc(int n, double rp, double rs, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Ellip(n, rp, rs, getW(fl), getW(fh), true, false), clazz);
    }

    public <F extends IirFilter> F getBP_EllipFpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        EllipOrd ord = new EllipOrd(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Ellip(ord.getN(), rp, rs, ord.getWp(0), ord.getWp(1), true, false), clazz);
    }

    public <F extends IirFilter> F getBS_EllipFc(int n, double rp, double rs, double fl, double fh, Class<F> clazz) {
        return doTransformSAndZ(new Ellip(n, rp, rs, getW(fl), getW(fh), true, true), clazz);
    }

    public <F extends IirFilter> F getBS_EllipFpFs(double rp, double rs, double fpl, double fph, double fsl, double fsh, Class<F> clazz) {
        EllipOrd ord = new EllipOrd(getW(fpl), getW(fph), getW(fsl), getW(fsh), rp, rs);
        return doTransformSAndZ(new Ellip(ord.getN(), rp, rs, ord.getWp(0), ord.getWp(1), true, true), clazz);
    }

    public <F extends IirFilter> F getLP_FirstOrder(double fc, Class<F> clazz) {
        final double fg = Math.tan(Math.PI * fc / samplerate);
        double b_0_1 = fg / (1 + fg);
        double a1 = (fg - 1) / (1 + fg);
        if (DirectIirFilter.class.isAssignableFrom(clazz)) {
            return (F) createDirectFilter(new double[]{1, a1}, new double[]{b_0_1, b_0_1}, (Class<DirectIirFilter>) clazz);
        }
        if (CascadedIirFilter.class.isAssignableFrom(clazz)) {
            return (F) createCascadedFilter(new double[][]{{b_0_1, b_0_1, 0, 1, a1, 0}}, 1, (Class<CascadedIirFilter>) clazz);
        }
        throw new RuntimeException();
    }

    /**
     * @return the samplerate
     */
    public double getSamplerate() {
        return samplerate;
    }

    /**
     * @param samplerate the samplerate to set
     */
    public void setSamplerate(double samplerate) {
        this.samplerate = samplerate;
    }
}
