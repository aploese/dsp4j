/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j.datatypes._double;

/**
 *
 * @author aploese
 */
public class MinSampleRate extends UpSample {

    private double minOutSampleRate;

    public MinSampleRate(double minOutSampleRate) {
        super(1);
        this.minOutSampleRate = minOutSampleRate;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        if (getSampleRate() == sampleRate) {
            return;
        }
        super.setSampleRate(sampleRate);
        init();
    }

    /**
     * @return the minOutSampleRate
     */
    public double getMinOutSampleRate() {
        return minOutSampleRate;
    }

    /**
     * @param minOutSampleRate the minOutSampleRate to set
     */
    public void setMinOutSampleRate(double minOutSampleRate) {
        this.minOutSampleRate = minOutSampleRate;
        init();
    }

    private void init() {
       setMultiplier((int)Math.ceil(getMinOutSampleRate() / getSampleRate()));
    }

}
