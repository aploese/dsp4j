package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author aploese
 */
public class AutoGain extends AbstractSampleProcessingBlock {

    final private static Logger LOG = LoggerFactory.getLogger(AutoGain.class);

    private DirectDoubleIirFilter lp;
    private double gainMin = 0.1;
    private double gain = 1.0;
    private double gainMax = 20.0;
    private double alpha = 4;
    private double beta = 0.9;
    private double pow = 1.0 / Math.sqrt(2.0);
    private double y;

    //TODO add args... and Listener ...
    public AutoGain() {
    }


    @Override
    public void setSampleRate(double  sampleRate) {
        lp = new DoubleIirFilterGenerator(sampleRate).getLP_FirstOrder(100, DirectDoubleIirFilter.class);
        super.setSampleRate(sampleRate);
    }

    @In
    public double setX(double data) {
        y = data * gain;
        lp.setX(y < 0 ? -y : y);
                gain = beta * gain + alpha * (pow - lp.getY());
                if (gain < gainMin) {
                    gain = gainMin;
                } else if (gain > gainMax) {
                    gain = gainMax;
                }

                return y;
    }
    
    @Out
    public double getY() {
        return y;
    }

    /**
     * @return the gainMin
     */
    public double getGainMin() {
        return gainMin;
    }

    /**
     * @param gainMin the gainMin to set
     */
    public void setGainMin(double gainMin) {
        this.gainMin = gainMin;
    }

    /**
     * @return the gainMax
     */
    public double getGainMax() {
        return gainMax;
    }

    /**
     * @param gainMax the gainMax to set
     */
    public void setGainMax(double gainMax) {
        this.gainMax = gainMax;
    }

    /**
     * @return the lp
     */
    public DirectDoubleIirFilter getLp() {
        return lp;
    }

    /**
     * @return the gain
     */
    public double getGain() {
        return gain;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
    }

    /**
     * @param beta the beta to set
     */
    public void setBeta(double beta) {
        this.beta = beta;
    }

    /**
     * @return the pow
     */
    public double getPow() {
        return pow;
    }

    /**
     * @param pow the pow to set
     */
    public void setPow(double pow) {
        this.pow = pow;
    }

}
