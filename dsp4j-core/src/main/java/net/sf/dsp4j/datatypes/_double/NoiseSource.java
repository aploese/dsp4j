package net.sf.dsp4j.datatypes._double;

import java.util.Random;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
@Deprecated
public class NoiseSource extends AbstractSampleProcessingBlock {

    private double gain;
    private Random random;
    private double y;


    public NoiseSource() {
        random = new Random();
    }

    public NoiseSource(long randomSeed) {
        random = new Random(randomSeed);
    }

    @In
    public double setX(double x) {
        y = ((random.nextDouble() - 0.5) * gain) + x;
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

    public void setRandomSeed(long seed) {
        random.setSeed(seed);
    }


}
