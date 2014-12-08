package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public class UpSample extends AbstractSampleProcessingBlock {

    private int multiplier;
 
    public UpSample(int multiplier) {
        this.multiplier = multiplier;
    }

    @In
    public void setX(double data) {
        for (int i = 1; i < multiplier; i++) {
 //           sink.setX(0);
        }
 //TODO       sink.setX(data * multiplier);
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
        return getSampleRate() * multiplier;
    }

}
