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
package de.ibapl.dsp4j.datatypes._double;

import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import de.ibapl.dsp4j.In;

/**
 *
 * @author aploese
 */
public class EdgeDetector extends AbstractSampleProcessingBlock {
    
    enum State {

        IDLE,
        HIGH_EDGE_LOWER_TRIGGERED,
        HIGH_EDGE_UPPER_TRIGGERED,
        LOW_EDGE_UPPER_TRIGGERED,
        LOW_EDGE_LOWER_TRIGGERED;
    }
    State state = State.IDLE;
    double highEdgeLower;
    double highEdgeUpper;
    double lowEdgeUpper;
    double lowEdgeLower;
    double minHoldTime;
    int samplesLeft;
    TriState y;
    

    public EdgeDetector(double lowThreshold, double highThreshold, double minHoldTime) {
        super();
        highEdgeLower = lowThreshold;
        highEdgeUpper = highThreshold;
        lowEdgeUpper = -lowThreshold;
        lowEdgeLower = -highThreshold;
        this.minHoldTime = minHoldTime;
    }

    @In
    public TriState setX(double data) {
        switch (state) {
            case IDLE:
                if (data > highEdgeLower) {
                    state = State.HIGH_EDGE_LOWER_TRIGGERED;
                } else if (data < lowEdgeUpper) {
                    state = State.LOW_EDGE_UPPER_TRIGGERED;
                } else {
                    y = TriState.ZERO;
                }
                break;
            case HIGH_EDGE_LOWER_TRIGGERED:
                if (data > highEdgeUpper) {
                    state = State.HIGH_EDGE_UPPER_TRIGGERED;
                    y = TriState.POSITIVE;
                    samplesLeft = (int)(getSampleRate() * minHoldTime);
                } else if (data < highEdgeLower) {
                    state = State.IDLE;
                    y = TriState.ZERO;
                } else {
                    y = TriState.ZERO;
                }
                break;
            case HIGH_EDGE_UPPER_TRIGGERED:
                samplesLeft--;
                if (data < highEdgeLower) {
                    state = State.IDLE;
                    if (samplesLeft > 0) {
//TODO                        sink.reset();
                    }
                }
                y = TriState.ZERO;
                break;
            case LOW_EDGE_UPPER_TRIGGERED:
                if (data < lowEdgeLower) {
                    state = State.LOW_EDGE_LOWER_TRIGGERED;
                    y = TriState.NEGATIVE;
                    samplesLeft = (int)(getSampleRate() * minHoldTime);
                } else if (data > lowEdgeUpper) {
                    state = State.IDLE;
                    y = TriState.ZERO;
                } else {
                    y = TriState.ZERO;
                }
                break;
            case LOW_EDGE_LOWER_TRIGGERED:
                samplesLeft--;
                if (data > lowEdgeUpper) {
                    state = State.IDLE;
                    if (samplesLeft > 0) {
                     //TODO   sink.reset();
                    }
                }
                y = TriState.ZERO;
                break;
        }
        return y;
    }

    @Override
    public void reset() {
        state = State.IDLE;
        super.reset();
    }
}
