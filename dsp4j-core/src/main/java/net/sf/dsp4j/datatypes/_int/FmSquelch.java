package net.sf.dsp4j.datatypes._int;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class FmSquelch extends AbstractSampleProcessingBlock {

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
    public DirectIntIirFilter getHpIn() {
        return hpIn;
    }

    /**
     * @param hpIn the hpIn to set
     */
    public void setHpIn(DirectIntIirFilter hpIn) {
        this.hpIn = hpIn;
    }

    public enum State {

        BLOCKING,
        START_PASSING,
        PASSING,
        START_BLOCKING;
    }

    /**
     * @return the lpSlow
     */
    public DirectIntIirFilter getLpSlow() {
        return lpSlow;
    }

    /**
     * @return the lpFast
     */
    public DirectIntIirFilter getLpFast() {
        return lpFast;
    }

    /**
     * @return the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    /**
     * @return the passTroughTime
     */
    public double getPassTroughTime() {
        return passTroughTime;
    }

    /**
     * @param passTroughTime the passTroughTime to set
     */
    public void setPassTroughTime(double passTroughTime) {
        this.passTroughTime = passTroughTime;
    }
    private DirectIntIirFilter lpSlow;
    private DirectIntIirFilter lpFast;
    private DirectIntIirFilter hpIn;
    private double fSlow;
    private double fFast;
    private double fhp;
    private double threshold;
    public double ratio;
    private double passTroughTime;
    private int samplesToPassLeft;
    private int passTroughSamples;
    
    private State state = State.BLOCKING;
    
    public FmSquelch(double threshhold, double fSlow, double fFast, double fhp, double passTroughTime) {
        this.threshold = threshhold;
        this.passTroughTime = passTroughTime;

        this.fSlow = fSlow;
        this.fFast = fFast;
        this.fhp = fhp;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        IntIirFilterGenerator gen = new IntIirFilterGenerator(sampleRate);
        lpSlow = gen.getLP_EllipFc(1, 1, 20, fSlow, DirectIntIirFilter.class);
        lpFast = gen.getLP_EllipFc(1, 1, 20, fFast, DirectIntIirFilter.class);
        hpIn = gen.getHP_EllipFc(1, 1, 20, fhp, DirectIntIirFilter.class);
        passTroughSamples = (int) (sampleRate * passTroughTime);
        super.setSampleRate(sampleRate);
    }

    /**
     *
     * @param sample
     * @return 
     */
    @In
    public State setX(int sample) {
        final int xFilter = Math.abs(hpIn.setX(sample));
        final int lpSlowY = lpSlow.setX(xFilter);
        final int lpFastY = lpFast.setX(xFilter);
//        ratio =  1.0 - lpFastY / lpSlowY;
        if (Math.abs(ratio) > threshold) {
            samplesToPassLeft = passTroughSamples;
        }
        switch (state) {
            case BLOCKING:
                if (samplesToPassLeft > 0) {
                    state = State.START_PASSING;
                }
                break;
            case START_PASSING:
                if (samplesToPassLeft > 0) {
                    state = State.PASSING;
                } else {
                    state = State.START_BLOCKING;
                }
                break;
            case PASSING:
                if (samplesToPassLeft == 0) {
                    state = State.START_BLOCKING;
                }
                break;
            case START_BLOCKING:
                if (samplesToPassLeft > 0) {
                    state = State.START_PASSING;
                } else {
                    state = State.BLOCKING;
                }
                break;

        }
        samplesToPassLeft--;
        return state;
    }

    @Out
    public State getState() {
        return state;
    }

    /**
     * @return the noiseLevel
     */
    public double getNoiseLevel() {
        return threshold;
    }

    /**
     * @param noiseLevel the noiseLevel to set
     */
    public void setNoiseLevel(double noiseLevel) {
        this.threshold = noiseLevel;
    }

    /**
     * @return the passtroughTime
     */
    public double getPasstroughTime() {
        return passTroughTime;
    }

    /**
     * @param passtroughTime the passtroughTime to set
     */
    public void setPasstroughTime(double passtroughTime) {
        this.passTroughTime = passtroughTime;
    }

    /**
     * @return the samplesToPassLeft
     */
    public int getSamplesToPassLeft() {
        return samplesToPassLeft;
    }
}
