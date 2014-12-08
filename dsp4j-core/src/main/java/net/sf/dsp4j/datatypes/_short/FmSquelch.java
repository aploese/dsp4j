package net.sf.dsp4j.datatypes._short;

import net.sf.dsp4j.datatypes._short.iirfilter.DirectShortIirFilter;
import net.sf.dsp4j.datatypes._short.iirfilter.ShortIirFilterGenerator;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class FmSquelch extends AbstractSampleProcessingBlock {

    private DirectShortIirFilter lp;
    private DirectShortIirFilter hpIn;
    private double flp;
    private double fhp;
    private final int threshold;
    
    public FmSquelch(int threshhold, double flp, double fhp) {
        this.threshold = threshhold;
        this.flp = flp;
        this.fhp = fhp;
    }

    /**
     * @return the fhp
     */
    public double getFhp() {
        return fhp;
    }

    /**
     * @param fhp the fhp to set
     */
    public void setFhp(double fhp) {
        this.fhp = fhp;
    }

    /**
     * @return the hpIn
     */
    public DirectShortIirFilter getHpIn() {
        return hpIn;
    }

    /**
     * @param hpIn the hpIn to set
     */
    public void setHpIn(DirectShortIirFilter hpIn) {
        this.hpIn = hpIn;
    }

    /**
     * @return the lpSlow
     */
    public DirectShortIirFilter getLp() {
        return lp;
    }

    /**
     * @return the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        ShortIirFilterGenerator gen = new ShortIirFilterGenerator(sampleRate);
        lp = gen.getLP_EllipFc(1, 1, 20, flp, DirectShortIirFilter.class);
        hpIn = gen.getHP_EllipFc(1, 1, 20, fhp, DirectShortIirFilter.class);
        super.setSampleRate(sampleRate);
    }

    /**
     *
     * @param sample
     * @return 
     */
    @In
    public boolean setX(int sample) {
        hpIn.setX(sample);
        lp.setX(Math.abs(hpIn.getY()));
        return lp.getY() < threshold;
    }

    public int getPower() {
        return lp.getY();
    }

}
