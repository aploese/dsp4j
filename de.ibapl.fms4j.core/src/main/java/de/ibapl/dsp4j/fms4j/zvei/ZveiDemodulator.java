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
package de.ibapl.dsp4j.fms4j.zvei;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.cos;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import static de.ibapl.dsp4j.DspConst.TWO_PI;
import de.ibapl.dsp4j.In;

// got siren tone 12/16 - Probe
/**
 *
 * @author aploese
 */
public class ZveiDemodulator extends AbstractSampleProcessingBlock {

    private void resetFrequencyBins() {
        samplesLeft = (int) Math.round(getSampleRate() * DEFAULT_BLOCK_LENGTH_IN_S);
        for (FrequencyBin fb : freqencyBin) {
            fb.reset();
        }
        maxEnergy1st = null;
        maxEnergy2nd = null;
        maxEnergy3rd = null;
        minEnergy = null;
    }

    private void sortPower(FrequencyBin fb) {
        int compResult;

        if (Double.compare(minEnergy == null ? Double.MAX_VALUE : minEnergy.getPower(), fb.getPower()) > 0) {
            minEnergy = fb;
        }

        if (0 == (compResult = Double.compare(maxEnergy1st == null ? -1 : maxEnergy1st.getPower(), fb.getPower()))) {
            return;
        } else if (compResult < 0) {
            maxEnergy3rd = maxEnergy2nd;
            maxEnergy2nd = maxEnergy1st;
            maxEnergy1st = fb;
            return;
        } else if (0 == (compResult = Double.compare(maxEnergy2nd == null ? -1 : maxEnergy2nd.getPower(), fb.getPower()))) {
            return;
        } else if (compResult < 0) {
            maxEnergy3rd = maxEnergy2nd;
            maxEnergy2nd = fb;
            return;
        } else if (0 == (compResult = Double.compare(maxEnergy3rd == null ? -1 : maxEnergy3rd.getPower(), fb.getPower()))) {
            return;
        } else if (compResult < 0) {
            maxEnergy3rd = fb;
        }
    }

    private void findMaxMinima() {
        LOG.finest("processBlock");

        for (FrequencyBin fb : freqencyBin) {
            LOG.finest(String.format("frequencyBin(%s) f = %dHz p = %f", fb.zvei.label, fb.zvei.f, fb.getPower()));
            sortPower(fb);
        }

        if (LOG.isLoggable(Level.FINEST)) {
            LOG.finest(String.format("maxEnergy1st(%s) f = %dHz p = %f", maxEnergy1st.zvei.label, maxEnergy1st.zvei.f, maxEnergy1st.getPower()));
            LOG.finest(String.format("maxEnergy2nd(%s) f = %dHz p = %f", maxEnergy2nd.zvei.label, maxEnergy2nd.zvei.f, maxEnergy2nd.getPower()));
            LOG.finest(String.format("maxEnergy3rd(%s) f = %dHz p = %f", maxEnergy3rd.zvei.label, maxEnergy3rd.zvei.f, maxEnergy3rd.getPower()));
            LOG.finest(String.format("minEnergy(%s) f = %dHz p = %f", minEnergy.zvei.label, minEnergy.zvei.f, minEnergy.getPower()));
            LOG.finest(String.format("Ratio maxEnergy1st minEnergy = %f", maxEnergy1st.getPower() / minEnergy.getPower()));
        }


    }

    private ZveiFreqTable auswertung() {
        if (maxEnergy1st.getPower() > minEnergy.getPower() * signalNoiseRatio) {
            return maxEnergy1st.zvei;
        } else {
            LOG.finest("Value replaced due to signalNoiseRatio");
            return ZveiFreqTable.UNBEKANNT;
        }
    }

    /**
     * @return the signalNoiseRatio
     */
    public double getSignalNoiseRatio() {
        return signalNoiseRatio;
    }

    /**
     * @param signalNoiseRatio the signalNoiseRatio to set
     */
    public void setSignalNoiseRatio(double signalNoiseRatio) {
        this.signalNoiseRatio = signalNoiseRatio;
    }

    //Goertzel Algo
    private class FrequencyBin {

        private double power;
        private boolean powerValid;
        private ZveiFreqTable zvei;
        private double sPrev;
        private double sPrev2;
        private double coeff;

        private FrequencyBin(ZveiFreqTable zvei) {
            this.zvei = zvei;
            reset();
        }

        private void reset() {
            powerValid = false;
            power = 0;
            coeff = 2 * cos(TWO_PI * zvei.f / getSampleRate());
            sPrev = 0;
            sPrev2 = 0;
        }

        private void calc(double d) {
            powerValid = false;
            final double s = d + coeff * sPrev - sPrev2;
            sPrev2 = sPrev;
            sPrev = s;
        }

        private double getPower() {
            if (!powerValid) {
                power = sPrev2 * sPrev2 + sPrev * sPrev - coeff * sPrev * sPrev2;
                powerValid = true;
            }
            return power;
        }
    }
    private static Logger LOG = Logger.getLogger(ZveiDemodulator.class.getCanonicalName());
    final static int DEFAULT_ZVEI_SIZE = 5;
    final static double DEFAULT_BLOCK_LENGTH_IN_S = 0.010;
    final static double DEFAULT_SIGNAL_NOISE_RATIO = 1000;
    private FrequencyBin[] freqencyBin;
    private int samplesLeft;
    private FrequencyBin maxEnergy1st;
    private FrequencyBin maxEnergy2nd;
    private FrequencyBin maxEnergy3rd;
    private FrequencyBin minEnergy;
    private double signalNoiseRatio = DEFAULT_SIGNAL_NOISE_RATIO;

    @In
    public ZveiFreqTable setX(double sample) {
        if (samplesLeft == 0) {
            resetFrequencyBins();
        }
        for (FrequencyBin fb : freqencyBin) {
            fb.calc(sample);
        }
        if (samplesLeft-- == 0) {
            findMaxMinima();
            return auswertung();
        }
        return null;
    }

    public void init() {

        List<ZveiFreqTable> tbl = new ArrayList<ZveiFreqTable>();
        for (ZveiFreqTable z : ZveiFreqTable.values()) {
            switch (z) {
                case UNBEKANNT:
                    tbl.remove(z);
                    break;
                default:
                    tbl.add(z);
            }
        }
        freqencyBin = new FrequencyBin[tbl.size()];

        for (int i = 0; i < tbl.size(); i++) {
            freqencyBin[i] = new FrequencyBin(tbl.get(i));
        }

        resetFrequencyBins();

    }

    /**
     * @brief constructor, initializing most of our little ZVEI universe ;)
     * @param sampleRate sample rate of data from soundcard to be analyzed by
     * demod()
     */
    public ZveiDemodulator() {
        init();
    }

    /**
     * @param samplerate the samplerate to set
     */
    @Override
    public void setSampleRate(double samplerate) {
        super.setSampleRate(samplerate);
        init();
    }
}
