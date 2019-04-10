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
public class NCO1stOrder extends AbstractSampleProcessingBlock implements NCO {

    final private static Logger LOG = Logger.getLogger(NCO1stOrder.class.getCanonicalName());
    private double phi;
    private double deltaPhi;
    
 
    private double alpha;
    
    private final CosTableDouble cosTable;
    
    private double f0;
    private double cosY;
    private double sinY;

    public NCO1stOrder(double f0, double alpha) {
        this.cosTable = CosTableDouble.getTableFor(0.001);
        this.f0 = f0;
        setAlpha(alpha);
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
    @Override
    public void setX(double error) {
        phi += error * alpha + deltaPhi;
        if (phi > TWO_PI) {
            phi -= TWO_PI;
        } else if (phi < 0) {
            phi += TWO_PI;
        }
        cosY = cosTable.cos0To2Pi(phi);
        sinY = cosTable.sin0To2Pi(phi);
    }

    private void init() {
        if (getSampleRate() > 0) {
            deltaPhi = TWO_PI * f0 / getSampleRate();
        }
    }

    /**
     * @return the phi0
     */
    @Override
    public double getPhi() {
        return phi;
    }

    /**
     * @param phi0 the phi0 to set
     */
    @Override
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
    @Override
    public double getCosY() {
        return cosY;
    }

    /**
     * @param alpha the alpha to set
     * beta will be calculated
     */
    @Override
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the deltaPhi
     */
    @Override
    public double getDeltaPhi() {
        return deltaPhi;
    }

    /**
     * @return the alpha
     */
    @Override
    public double getAlpha() {
        return alpha;
    }

    /**
     * @return the sinY
     */
    @Override
    public double getSinY() {
        return sinY;
    }

    /**
     * @param deltaPhi the deltaPhi to set
     */
    public void setDeltaPhi(double deltaPhi) {
        this.deltaPhi = deltaPhi;
    }

    void setDeltaphi(double deltaPhi) {
        this.deltaPhi = deltaPhi;
    }

    /**
     * @return the f0
     */
    @Override
    public double getF0() {
        return f0;
    }

    /**
     * @param f0 the f0 to set
     */
    @Override
    public void setF0(double f0) {
        this.f0 = f0;
    }

}
