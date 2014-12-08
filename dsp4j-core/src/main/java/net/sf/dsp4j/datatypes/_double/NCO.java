package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;
import net.sf.dsp4j.SampledBlock;

/**
 *
 * @author aploese
 */
public interface NCO extends SampledBlock {

    @In
    void setX(double error);

    /**
     * @return the phi0
     */
    double getPhi();

    /**
     * @param phi0 the phi0 to set
     */
    void setPhi(double phi);

    @Out
    double getCosY();

    /**
     * @param alpha the alpha to set
     * beta will be calculated
     */
    void setAlpha(double alpha);

    /**
     * @return the deltaPhi
     */
    double getDeltaPhi();

    /**
     * @return the alpha
     */
    double getAlpha();

    /**
     * @return the sinY
     */
    double getSinY();

    /**
     * @return the f0
     */
    double getF0();

    /**
     * @param f0 the f0 to set
     */
    void setF0(double f0);

}
