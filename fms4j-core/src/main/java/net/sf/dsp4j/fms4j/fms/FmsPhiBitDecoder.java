/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

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
