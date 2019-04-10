/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.fms4j.fms;

import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import de.ibapl.dsp4j.datatypes._double.iirfilter.AbstractCascadedDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.Cordic;
import de.ibapl.dsp4j.datatypes._double.CostasLoop;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import de.ibapl.dsp4j.datatypes._double.NCO;
import de.ibapl.dsp4j.datatypes._double.NCO1stOrder;

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
