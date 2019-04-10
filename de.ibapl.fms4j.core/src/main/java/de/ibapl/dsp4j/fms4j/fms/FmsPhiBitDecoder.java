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

/**
 *
 * @author aploese
 */
public class FmsPhiBitDecoder extends FmsBitDecoder {

    private double samplerate;
    private int samples;
    private double samplesPerSymbol;
    private double lastSample;
    private int nextSynbolSample;
    private int synbolCount;
    private int samplesSinceStart;

    public final boolean setPhiError(double phi) {
        boolean result = false;
        samples++;
        samplesSinceStart++;
        if (phi >= 0 && lastSample < 0) {
            result = reached(true);
        } else if (phi < 0 && lastSample >= 0) {
            result = reached(false);
        } else if (nextSynbolSample == samples) {
            if (phi == 0) {
                result = outputBit(lastSample < 0);
            } else {
                result = outputBit(phi < 0);
            }
        }
        lastSample = phi;
        return result;
    }

    @Override
    public void reset() {
        super.reset();
        initSymbolCounter();
    }

    @Override
    public final boolean setX(final boolean bit) {
        State oldSate = state;
        boolean result = super.setX(bit);
        if (oldSate == State.COLLECT_5TH_0 && state == State.DECODE_BITS) {
            samplesSinceStart = 0;
        }
        if (currentBitPos > (samplesSinceStart / samplesPerSymbol) + 2) {
            reset();
            return false;
        }
        return result;
    }

    private boolean reached(final boolean bit) {
        final double symbols = (double) samples / samplesPerSymbol;
        if (Math.round(symbols) == 0) {
            initSymbolCounter();
            return setX(bit);
        } else {
            final int symbolsLeft = (int) Math.round(symbols - synbolCount);
            switch (symbolsLeft) {
                case 0:
                    initSymbolCounter();
                    return false;
                case 1:
                    initSymbolCounter();
                    return setX(bit);
                default:
                    throw new RuntimeException("Should never happen: symboils left " + symbolsLeft);
            }
        }
    }

    public void setSamplerate(double samplerate) {
        this.samplerate = samplerate;
        this.samplesPerSymbol = samplerate / FmsModulator.SYMBOL_RATE;
        initSymbolCounter();
    }

    public double getSamplerate() {
        return samplerate;
    }

    private final boolean outputBit(boolean bit) {
        synbolCount++;
        nextSynbolSample = (int) Math.floor(samplesPerSymbol * (synbolCount + 1));
        return setX(bit);
    }

    private void initSymbolCounter() {
        samples = 0;
        synbolCount = 0;
        nextSynbolSample = (int) Math.floor(samplesPerSymbol);
    }
}
