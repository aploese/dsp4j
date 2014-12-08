package net.sf.dsp4j.fms4j.fms;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.datatypes._double.iirfilter.AbstractCascadedDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.Cordic;
import net.sf.dsp4j.datatypes._double.CostasLoop;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.datatypes._double.NCO;
import net.sf.dsp4j.datatypes._double.NCO1stOrder;

/**
 *
 * @author aploese
 *
 */
//TODO access threshold ... nco bandwith ...?
public class FmsContainer extends AbstractSampleProcessingBlock {

    private final CostasLoop costasLoop;
    private final FmsPhiBitDecoder fmsBD;
    private final FmsDemodulator fmsD;
    private AbstractCascadedDoubleIirFilter symbolFiilter;
    private AbstractCascadedDoubleIirFilter inFilter;
    

    public FmsContainer(FmsContainerListener fmsContainerListener) {
        costasLoop = new CostasLoop(new NCO1stOrder(1500, 0.12), new Cordic(4), FmsModulator.SYMBOL_RATE);
//        costasLoop = new CostasLoop(new NCO1stOrder(1500, 0.12), new GenericAtan(), FmsModulator.SYMBOL_RATE);
        fmsBD = new FmsPhiBitDecoder();
        fmsD = new FmsDemodulator(fmsContainerListener);
    }

    public final void setX(double sample) {
        inFilter.setX(sample);
        costasLoop.setX(inFilter.getY());
        symbolFiilter.setX(costasLoop.getPhiError());
        if (fmsBD.setPhiError(symbolFiilter.getY())) {
                final FmsData data = fmsD.setX(fmsBD.isBit());
                if (data != null) {
                    fmsBD.reset();
                }
            } else if (fmsBD.getState() != FmsBitDecoder.State.DECODE_BITS) {
                    fmsD.reset();
            } 
    }

    @Override
    public void setSampleRate(double sampleRate) {
        super.setSampleRate(sampleRate);
        DoubleIirFilterGenerator iirGenerator = new DoubleIirFilterGenerator(sampleRate);
        symbolFiilter = iirGenerator.getLP_EllipFc(3, 1, 40, 0.75 * FmsModulator.SYMBOL_RATE, AbstractCascadedDoubleIirFilter.class);
        inFilter = iirGenerator.getBP_BesselFc(1, 10, 2000, AbstractCascadedDoubleIirFilter.class);
        costasLoop.setSampleRate(sampleRate);
        fmsBD.setSamplerate(sampleRate);
    }

    @Override
    public void reset() {
        super.reset();
        costasLoop.reset();
        fmsBD.reset();
        fmsD.reset();
        symbolFiilter.reset();
        inFilter.reset();
    }

    /**
     * @return the costasLoop
     */
    public CostasLoop getCostasLoop() {
        return costasLoop;
    }

    /**
     * @return the fmsBD
     */
    public FmsPhiBitDecoder getFmsBD() {
        return fmsBD;
    }

    /**
     * @return the fmsD
     */
    public FmsDemodulator getFmsD() {
        return fmsD;
    }

    public double getSymbolFilterY() {
        return symbolFiilter.getY();
    }

    public boolean getCurrentBit() {
        return fmsBD.isBit();
    }

    /**
     * @return the inFiilter
     */
    public AbstractCascadedDoubleIirFilter getInFilter() {
        return inFilter;
    }
    
    public NCO getNco() {
        return costasLoop.getNco();
    }
    
}
