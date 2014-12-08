/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public class FmsBitDecoder {

    protected enum State {

        NOISE_MARK,
        NOISE_SPACE,
        COLLECT_1ST_000,
        COLLECT_2ND_11,
        COLLECT_3RD_0,
        COLLECT_4TH_1,
        COLLECT_5TH_0,
        DECODE_BITS;
    }
    protected State state = State.NOISE_SPACE;
    protected int currentBitPos;
    protected boolean bit;
    protected int bitCount;

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @return the currentBitPos
     */
    public int getCurrentBitPos() {
        return currentBitPos;
    }

    /**
     * @return the bitCount
     */
    public int getBitCount() {
        return bitCount;
    }

    /**
     * @return the bit
     */
    public boolean isBit() {
        return bit;
    }

    public void reset() {
        state = State.NOISE_SPACE;
        currentBitPos = 0;
        bitCount = 0;
    }

    @In
    public boolean setX(boolean bit) {
        switch (state) {
            case NOISE_MARK:
                if (bit) {
                    bitCount++;
                } else {
                    if (bitCount > 3) {
                        state = State.COLLECT_1ST_000;
                        bitCount = 1;
                    } else {
                        state = State.NOISE_SPACE;
                        bitCount = 1;
                    }
                }
                break;
            case NOISE_SPACE:
                if (bit) {
                    state = State.NOISE_MARK;
                    bitCount = 1;
                } else {
                    bitCount++;
                }
                break;
            case COLLECT_1ST_000:
                if (!bit) {
                    bitCount++;
                    if (bitCount == 3) {
                        state = State.COLLECT_2ND_11;
                        bitCount = 0;
                    }
                } else {
                    state = State.NOISE_MARK;
                    bitCount = 1;
                }
                break;
            case COLLECT_2ND_11:
                if (bit) {
                    bitCount++;
                    if (bitCount == 2) {
                        state = State.COLLECT_3RD_0;
                        bitCount = 0;
                    }
                } else {
                    state = State.NOISE_SPACE;
                    bitCount = 1;
                }
                break;
            case COLLECT_3RD_0:
                if (!bit) {
                    state = State.COLLECT_4TH_1;
                    bitCount = 0;
                } else {
                    state = State.NOISE_MARK;
                    bitCount = 1;
                }
                break;
            case COLLECT_4TH_1:
                if (bit) {
                    state = State.COLLECT_5TH_0;
                    bitCount = 0;
                } else {
                    state = State.NOISE_SPACE;
                    bitCount = 1;
                }
                break;
            case COLLECT_5TH_0:
                if (!bit) {
                    state = State.DECODE_BITS;
                    currentBitPos = 0;
                    bitCount = 0;
                } else {
                    state = State.NOISE_MARK;
                    bitCount = 1;
                }
                break;
            case DECODE_BITS:
                if (this.bit != bit) {
                    bitCount = 1;
                } else {
                    bitCount++;
                }
                currentBitPos++;
                this.bit = bit;
                return true;
        }
        this.bit = bit;
        return false;
    }
}
