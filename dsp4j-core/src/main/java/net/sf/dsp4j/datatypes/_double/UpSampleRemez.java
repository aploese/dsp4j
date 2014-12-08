package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.In;
import net.sf.dsp4j.SampledBlock;

/**
 *
 * @author aploese
 */
public class UpSampleRemez implements SampledBlock {

    private int multiplier;
    private double sampleRate;
    private RemezFilter filter;

    public UpSampleRemez(int multiplier) {
        this.multiplier = multiplier;
        filter = new RemezFilter(multiplier * 16, new double[] {0}, new double[] {0,1});
    }

    @Override
    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
        init();
    }

    @In
    public void setX(double data) {
        for (int i = 1; i < multiplier; i++) {
            filter.setX(0);
        }
        filter.setX(data * multiplier);
    }

    /**
     * @return the multiplier
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier the multiplier to set
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
        init();
    }

    private void init() {
        filter.setSampleRate(sampleRate * multiplier);
        if (sampleRate == 0) {
            return;
        }
        double deltaBand = 0.2;
        filter.setFc(multiplier * 32, sampleRate / 2.0, deltaBand, false);
    }

    public double getOutSampleRate() {
        return filter.getSampleRate();
    }

    @Override
    public double getSampleRate() {
        return sampleRate;
    }

    @Override
    public void reset() {
        filter.reset();
    }
}
