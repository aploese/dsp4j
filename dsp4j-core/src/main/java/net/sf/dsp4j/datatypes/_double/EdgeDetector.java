package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;

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
