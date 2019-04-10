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

import de.ibapl.dsp4j.datatypes._double.iirfilter.Direct1stOrderDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class FmSquelch extends AbstractSampleProcessingBlock {

    private DirectDoubleIirFilter lp;
    private DirectDoubleIirFilter hpIn;
    private double flp;
    private double fhp;
    private final double threshold;
    

    public FmSquelch(double threshhold, double flp, double fhp) {
        this.threshold = threshhold;
        this.flp = flp;
        this.fhp = fhp;
    }

    /**
     * @return the fhp
     */
    public double getFhp() {
        return fhp;
    }

    /**
     * @param fhp the fhp to set
     */
    public void setFhp(double fhp) {
        this.fhp = fhp;
    }

    /**
     * @return the hpIn
     */
    public DirectDoubleIirFilter getHpIn() {
        return hpIn;
    }

    /**
     * @param hpIn the hpIn to set
     */
    public void setHpIn(DirectDoubleIirFilter hpIn) {
        this.hpIn = hpIn;
    }

    public double getPower() {
        return lp.getY();
    }

    /**
     * @return the lpSlow
     */
    public DirectDoubleIirFilter getLp() {
        return lp;
    }

    /**
     * @return the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(sampleRate);
        lp = gen.getLP_ButterFc(1, flp, Direct1stOrderDoubleIirFilter.class);
        hpIn = gen.getHP_Cheby2Fc(1, 20, fhp, Direct1stOrderDoubleIirFilter.class);
        super.setSampleRate(sampleRate);
    }

    /**
     *
     * @param sample
     * @return 
     */
    @In
    @Out
    public boolean setX(final double sample) {
        hpIn.setX(sample);
        lp.setX(Math.abs(hpIn.getY()));
        return lp.getY() < threshold;
    }

}
