package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.datatypes._double.iirfilter.AbstractCascadedDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.In;
import net.sf.dsp4j.SampledBlock;

/**
 *
 * @author aploese
 */
public class UpSampleButterworth implements SampledBlock {

    
    private int multiplier;
    private double sampleRate;
    private AbstractCascadedDoubleIirFilter filter;

    public UpSampleButterworth(int multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
        filter =  new DoubleIirFilterGenerator(sampleRate * multiplier).getLP_BesselFc(multiplier + 1, sampleRate / 2.0, AbstractCascadedDoubleIirFilter.class);
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
    }

    public double getOutSampleRate() {
        return sampleRate * multiplier;
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
