package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import java.util.List;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class CostasLoop<N extends NCO, T extends FunctionAtan> extends AbstractSampleProcessingBlock {

    private DirectDoubleIirFilter cosPathLpFilter;
    private DirectDoubleIirFilter sinPathLpFilter;
    private final N nco;
    private final T arcTan;
    private double fcFilter;
    private double phiError;
    

    public CostasLoop(N nco, T arcTan, double fcFilter) {
        this.fcFilter = fcFilter;
        this.nco = nco;
        this.arcTan = arcTan;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        super.setSampleRate(sampleRate);
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(sampleRate);
        cosPathLpFilter = gen.getLP_FirstOrder(fcFilter, DirectDoubleIirFilter.class);
        sinPathLpFilter = gen.getLP_FirstOrder(fcFilter, DirectDoubleIirFilter.class);
        nco.setSampleRate(sampleRate);
    }

    @In
    public void setX(double x) {
        cosPathLpFilter.setX(x * nco.getCosY());
        sinPathLpFilter.setX(x * -nco.getSinY());
        phiError = arcTan.aTan(getCosPathLpFilter().getY(), sinPathLpFilter.getY());
        nco.setX(phiError);
    }

    @Out
    public double getY() {
        return getCosPathLpFilter().getY();
    }

    @Out
    public double getPhiError() {
        return phiError;
    }

    @Out
    public double getNcoDeltaPhi() {
        return nco.getDeltaPhi();
    }

    @Override
    public void reset() {
        super.reset();
        cosPathLpFilter.reset();
        sinPathLpFilter.reset();
        nco.reset();
    }

    /**
     * @return the frequency of the filters
     */
    public double getfFilter() {
        return fcFilter;
    }

    /**
     * @param fcFilter the symbolrate to set
     */
    public void setFcFilter(double fcFilter) {
        this.fcFilter = fcFilter;
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(getSampleRate());
        cosPathLpFilter = gen.getLP_FirstOrder(fcFilter, DirectDoubleIirFilter.class);
        sinPathLpFilter = gen.getLP_FirstOrder(fcFilter, DirectDoubleIirFilter.class);
    }

    /**
     * @return the cosPathLpFilter
     */
    public DirectDoubleIirFilter getCosPathLpFilter() {
        return cosPathLpFilter;
    }

    /**
     * @return the sinPathLpFilter
     */
    public DirectDoubleIirFilter getSinPathLpFilter() {
        return sinPathLpFilter;
    }

    /**
     * @return the nco
     */
    public N getNco() {
        return nco;
    }

}