package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.datatypes._double.iirfilter.Direct1stOrderDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class FmSquelch extends AbstractSampleProcessingBlock {

    private DirectDoubleIirFilter lp;
    private DirectDoubleIirFilter hpIn;
    private double flp;
    private double fhp;
    private final double threshold;
    

    public FmSquelch(double threshhold, double flp, double fhp) {
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
    public DirectDoubleIirFilter getHpIn() {
        return hpIn;
    }

    /**
     * @param hpIn the hpIn to set
     */
    public void setHpIn(DirectDoubleIirFilter hpIn) {
        this.hpIn = hpIn;
    }

    public double getPower() {
        return lp.getY();
    }

    /**
     * @return the lpSlow
     */
    public DirectDoubleIirFilter getLp() {
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
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(sampleRate);
        lp = gen.getLP_ButterFc(1, flp, Direct1stOrderDoubleIirFilter.class);
        hpIn = gen.getHP_Cheby2Fc(1, 20, fhp, Direct1stOrderDoubleIirFilter.class);
        super.setSampleRate(sampleRate);
    }

    /**
     *
     * @param sample
     * @return 
     */
    @In
    @Out
    public boolean setX(final double sample) {
        hpIn.setX(sample);
        lp.setX(Math.abs(hpIn.getY()));
        return lp.getY() < threshold;
    }

}
