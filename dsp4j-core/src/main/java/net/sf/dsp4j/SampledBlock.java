package net.sf.dsp4j;

/**
 *
 * @author aploese
 */
public interface SampledBlock {

    void setSampleRate(double sampleRate);

    double getSampleRate();

    void reset();
}
