package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import static net.sf.dsp4j.DspConst.TWO_PI;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aploese
 */
public class NCO2ndOrder extends AbstractSampleProcessingBlock implements NCO {

    final private static Logger LOG = LoggerFactory.getLogger(NCO2ndOrder.class);
    private double phi;
    private double deltaPhiMin;
    private double deltaPhi;
    private double currentDeltaPhi;
    private double deltaPhiMax;
 
    private double alpha;
    private double beta;
    
    private final CosTableDouble cosTable;
    
    private double fMin;
    private double f0;
    private double fMax;
    private double cosY;
    private double sinY;

    /**
     *
     * @param f0
     * @param bandWith the bandwith around f0, 0.5 = 5 percent
     */
    public NCO2ndOrder(double fMin, double f0, double fMax) {
        this.cosTable = CosTableDouble.getTableFor(0.001);
        this.fMin = fMin;
        this.f0 = f0;
        this.fMax = fMax;
        setAlpha(0.1);
    }

    public NCO2ndOrder(double fMin, double f0, double fMax, double alpha) {
        this(fMin, f0, fMax);
        setAlpha(alpha);
    }

    public NCO2ndOrder(double fMin, double f0, double fMax, double alpha, double  beta) {
        this(fMin, f0, fMax);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        if (getSampleRate() == sampleRate) {
            return;
        }
        super.setSampleRate(sampleRate);
        init();
    }

    @In
    @Override
    public void setX(double error) {
        currentDeltaPhi = error * alpha + deltaPhi;
        phi += currentDeltaPhi;
        if (phi > TWO_PI) {
            phi -= TWO_PI;
        } else if (phi < 0) {
            phi += TWO_PI;
        }
        deltaPhi += error * beta;
        if (deltaPhi < deltaPhiMin) {
           deltaPhi = deltaPhiMin;
        } else if (deltaPhi > deltaPhiMax) {
           deltaPhi = deltaPhiMax;
        }
        cosY = cosTable.cos0To2Pi(phi);
        sinY = cosTable.sin0To2Pi(phi);
    }

    private void init() {
        if (getSampleRate() > 0) {
            deltaPhiMin = TWO_PI * fMin / getSampleRate();
            deltaPhiMax = TWO_PI * fMax / getSampleRate();
            deltaPhi = TWO_PI * f0 / getSampleRate();
        }
    }

    /**
     * @return the phi0
     */
    @Override
    public double getPhi() {
        return phi;
    }

    /**
     * @param phi0 the phi0 to set
     */
    @Override
    public void setPhi(double phi) {
        this.phi = phi;
    }

    @Override
    public void reset() {
        cosY = 0;
        sinY = 0;
        init();
    }

    @Out
    @Override
    public double getCosY() {
        return cosY;
    }

    /**
     * @param alpha the alpha to set
     * beta will be calculated
     */
    @Override
    public void setAlpha(double alpha) {
        this.alpha = alpha;
        this.beta = alpha * alpha / 4;
    }

    public void setAlphaAndBeta(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
}

    /**
     * @return the deltaPhiMin
     */
    public double getDeltaPhiMin() {
        return deltaPhiMin;
    }

    /**
     * @return the deltaPhi
     */
    @Override
    public double getDeltaPhi() {
        return deltaPhi;
    }

    /**
     * @return the deltaPhiMax
     */
    public double getDeltaPhiMax() {
        return deltaPhiMax;
    }

    /**
     * @return the alpha
     */
    @Override
    public double getAlpha() {
        return alpha;
    }

    /**
     * @return the fMin
     */
    public double getFMin() {
        return fMin;
    }

    /**
     * @return the fMax
     */
    public double getFMax() {
        return fMax;
    }

    /**
     * @return the sinY
     */
    @Override
    public double getSinY() {
        return sinY;
    }

    /**
     * @param deltaPhi the deltaPhi to set
     */
    public void setDeltaPhi(double deltaPhi) {
        this.deltaPhi = deltaPhi;
    }

    /**
     * @return the currentDeltaPhi
     */
    public double getCurrentDeltaPhi() {
        return currentDeltaPhi;
    }

    void setDeltaphi(double deltaPhi) {
        this.deltaPhi = deltaPhi;
    }

    /**
     * @return the f0
     */
    @Override
    public double getF0() {
        return f0;
    }

    /**
     * @param f0 the f0 to set
     */
    @Override
    public void setF0(double f0) {
        this.f0 = f0;
    }

}
