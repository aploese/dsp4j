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

import java.util.logging.Logger;
import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import static de.ibapl.dsp4j.DspConst.TWO_PI;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class GenericNCO2ndOrder extends AbstractSampleProcessingBlock implements NCO {

    final private static Logger LOG = Logger.getLogger(GenericNCO2ndOrder.class.getCanonicalName());
    private double phi;
    private double deltaPhiMin;
    private double deltaPhi;
    private double currentDeltaPhi;
    private double deltaPhiMax;
 
    private double alpha;
    private double beta;
    
    private double fMin;
    private double f0;
    private double fMax;
    private double cosY;
    private double sinY;

    /**
     *
     * @param f0
     * @param bandWith the bandwith around f0, 0.5 = 5 percent
     */
    public GenericNCO2ndOrder(double fMin, double f0, double fMax) {
        this.fMin = fMin;
        this.f0 = f0;
        this.fMax = fMax;
        setAlpha(0.1);
    }

    public GenericNCO2ndOrder(double fMin, double f0, double fMax, double alpha) {
        this(fMin, f0, fMax);
        setAlpha(alpha);
    }

    public GenericNCO2ndOrder(double fMin, double f0, double fMax, double alpha, double  beta) {
        this(fMin, f0, fMax);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        if (getSampleRate() == sampleRate) {
            return;
        }
        super.setSampleRate(sampleRate);
        init();
    }

    @In
    public void setX(double error) {
        currentDeltaPhi = error * alpha + deltaPhi;
        phi += currentDeltaPhi;
        if (phi > TWO_PI) {
            phi -= TWO_PI;
        } else if (phi < 0) {
            phi += TWO_PI;
        }
        deltaPhi += error * beta;
        if (deltaPhi < deltaPhiMin) {
           deltaPhi = deltaPhiMin;
        } else if (deltaPhi > deltaPhiMax) {
           deltaPhi = deltaPhiMax;
        }
        cosY = Math.cos(phi);
        sinY = Math.sin(phi);
    }

    private void init() {
        if (getSampleRate() > 0) {
            deltaPhiMin = TWO_PI * fMin / getSampleRate();
            deltaPhiMax = TWO_PI * fMax / getSampleRate();
            deltaPhi = TWO_PI * f0 / getSampleRate();
        }
    }

    /**
     * @return the phi0
     */
    public double getPhi() {
        return phi;
    }

    /**
     * @param phi0 the phi0 to set
     */
    public void setPhi(double phi) {
        this.phi = phi;
    }

    @Override
    public void reset() {
        cosY = 0;
        sinY = 0;
        init();
    }

    @Out
    public double getCosY() {
        return cosY;
    }

    /**
     * @param alpha the alpha to set
     * beta will be calculated
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
        this.beta = alpha * alpha / 4;
    }

    public void setAlphaAndBeta(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
}

    /**
     * @return the deltaPhiMin
     */
    public double getDeltaPhiMin() {
        return deltaPhiMin;
    }

    /**
     * @return the deltaPhi
     */
    public double getDeltaPhi() {
        return deltaPhi;
    }

    /**
     * @return the deltaPhiMax
     */
    public double getDeltaPhiMax() {
        return deltaPhiMax;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @return the fMin
     */
    public double getFMin() {
        return fMin;
    }

    /**
     * @return the fMax
     */
    public double getFMax() {
        return fMax;
    }

    /**
     * @return the sinY
     */
    public double getSinY() {
        return sinY;
    }

    /**
     * @param deltaPhi the deltaPhi to set
     */
    public void setDeltaPhi(double deltaPhi) {
        this.deltaPhi = deltaPhi;
    }

    /**
     * @return the currentDeltaPhi
     */
    public double getCurrentDeltaPhi() {
        return currentDeltaPhi;
    }

    void setDeltaphi(double deltaPhi) {
        this.deltaPhi = deltaPhi;
    }

    /**
     * @return the f0
     */
    public double getF0() {
        return f0;
    }

    /**
     * @param f0 the f0 to set
     */
    public void setF0(double f0) {
        this.f0 = f0;
    }

}
