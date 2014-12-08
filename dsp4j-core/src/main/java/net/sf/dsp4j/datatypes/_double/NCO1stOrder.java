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
public class NCO1stOrder extends AbstractSampleProcessingBlock implements NCO {

    final private static Logger LOG = LoggerFactory.getLogger(NCO1stOrder.class);
    private double phi;
    private double deltaPhi;
    
 
    private double alpha;
    
    private final CosTableDouble cosTable;
    
    private double f0;
    private double cosY;
    private double sinY;

    public NCO1stOrder(double f0, double alpha) {
        this.cosTable = CosTableDouble.getTableFor(0.001);
        this.f0 = f0;
        setAlpha(alpha);
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
        phi += error * alpha + deltaPhi;
        if (phi > TWO_PI) {
            phi -= TWO_PI;
        } else if (phi < 0) {
            phi += TWO_PI;
        }
        cosY = cosTable.cos0To2Pi(phi);
        sinY = cosTable.sin0To2Pi(phi);
    }

    private void init() {
        if (getSampleRate() > 0) {
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
    }

    /**
     * @return the deltaPhi
     */
    @Override
    public double getDeltaPhi() {
        return deltaPhi;
    }

    /**
     * @return the alpha
     */
    @Override
    public double getAlpha() {
        return alpha;
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
