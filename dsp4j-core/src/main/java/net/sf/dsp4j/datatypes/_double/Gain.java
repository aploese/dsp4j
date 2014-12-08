package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class Gain extends AbstractSampleProcessingBlock {

    private double gain;
    private double y;

    public Gain(double gain) {
        this.gain = gain;
    }
    
    @In
    public double setX(double data) {
        y = data * gain;
        return y;
    }
    
    @Out
    public double getY() {
        return y;
    }

    /**
     * @return the gain
     */
    public double getGain() {
        return gain;
    }

    /**
     * @param gain the gain to set
     */
    public void setGain(double gain) {
        this.gain = gain;
    }

}
