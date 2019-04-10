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
import de.ibapl.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;


/**
 *
 * @author aploese
 */
public class AutoGain extends AbstractSampleProcessingBlock {

    final private static Logger LOG = Logger.getLogger(AutoGain.class.getCanonicalName());

    private DirectDoubleIirFilter lp;
    private double gainMin = 0.1;
    private double gain = 1.0;
    private double gainMax = 20.0;
    private double alpha = 4;
    private double beta = 0.9;
    private double pow = 1.0 / Math.sqrt(2.0);
    private double y;

    //TODO add args... and Listener ...
    public AutoGain() {
    }


    @Override
    public void setSampleRate(double  sampleRate) {
        lp = new DoubleIirFilterGenerator(sampleRate).getLP_FirstOrder(100, DirectDoubleIirFilter.class);
        super.setSampleRate(sampleRate);
    }

    @In
    public double setX(double data) {
        y = data * gain;
        lp.setX(y < 0 ? -y : y);
                gain = beta * gain + alpha * (pow - lp.getY());
                if (gain < gainMin) {
                    gain = gainMin;
                } else if (gain > gainMax) {
                    gain = gainMax;
                }

                return y;
    }
    
    @Out
    public double getY() {
        return y;
    }

    /**
     * @return the gainMin
     */
    public double getGainMin() {
        return gainMin;
    }

    /**
     * @param gainMin the gainMin to set
     */
    public void setGainMin(double gainMin) {
        this.gainMin = gainMin;
    }

    /**
     * @return the gainMax
     */
    public double getGainMax() {
        return gainMax;
    }

    /**
     * @param gainMax the gainMax to set
     */
    public void setGainMax(double gainMax) {
        this.gainMax = gainMax;
    }

    /**
     * @return the lp
     */
    public DirectDoubleIirFilter getLp() {
        return lp;
    }

    /**
     * @return the gain
     */
    public double getGain() {
        return gain;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
    }

    /**
     * @param beta the beta to set
     */
    public void setBeta(double beta) {
        this.beta = beta;
    }

    /**
     * @return the pow
     */
    public double getPow() {
        return pow;
    }

    /**
     * @param pow the pow to set
     */
    public void setPow(double pow) {
        this.pow = pow;
    }

}
