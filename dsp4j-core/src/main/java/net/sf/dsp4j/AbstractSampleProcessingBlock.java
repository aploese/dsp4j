package net.sf.dsp4j;

import static net.sf.dsp4j.DspConst.TWO_PI;

/**
 *
 * @author aploese
 */
public abstract class AbstractSampleProcessingBlock implements SampledBlock {

    private double sampleRate;

    @Override
    public void setSampleRate(double samplerate) {
        if (this.sampleRate == samplerate) {
            return;
        }
        this.sampleRate = samplerate;
    }

    @Override
    public double getSampleRate() {
        return sampleRate;
    }

    public void reset() {
    }

    public double calcW(double frequency) {
        return TWO_PI * frequency / getSampleRate();
    }
}
