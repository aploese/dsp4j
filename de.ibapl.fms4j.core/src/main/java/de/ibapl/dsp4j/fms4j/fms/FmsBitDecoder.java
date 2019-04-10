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

import de.ibapl.dsp4j.In;

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
