/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.zvei;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO         detector = new ZveiFolgeDetector(DEFAULT_ZVEI_SIZE, (int)Math.round(ZveiFreqTable.DEFAULT_TON_LENGTH_IN_S / DEFAULT_BLOCK_LENGTH_IN_S));
/**
 *
 * @author aploese
 *
 * hang on first change of z in foundStack
 *
 */
public class ZveiFolgeDetector {

    /**
     *
     * @param z the latest value
     * @return max z of the #foundStack
     *
     * remove oldest (#foundStack[0]) z from #detectionMap shift found stack by
     * 1 and add z on top of it. find maxZ
     *
     */
    private MapValue addToAndProcessDetectionStack(ZveiFreqTable z) {
        //remove
        MapValue mv = tokenMap.get(tokenStack[0]);

        mv.count--;
        if (mv.count == 0) {
            tokenMap.remove(mv.z);
        }

        //shift
        for (int i = 1; i < tokenStack.length; i++) {
            tokenStack[i - 1] = tokenStack[i];
        }

        //add
        tokenStack[tokenStack.length - 1] = z;
        mv = tokenMap.get(z);
        if (mv == null) {
            tokenMap.put(z, new MapValue(z));
        } else {
            mv.count++;
        }

        //find maxZ
        MapValue maxZ = null;
        for (MapValue m : tokenMap.values()) {
            LOG.trace("Anzahl: " + m.count + " | " + m.z);
            if (maxZ == null) {
                maxZ = m;
            } else if (maxZ.count < m.count) {
                maxZ = m;
            }
        }
        LOG.debug(String.format("Curr max: %s %d", maxZ.z.label, maxZ.count));
        return maxZ;
    }

    /**
     *
     * @return the estimated count of Tokens
     *
     * minimum tokens that are passed it could be up to (#tokenStack.lenght -
     * detectionThreshold) * more
     *
     */
    private ZveiFreqTable[] foundStackToZveiFreqTable() {
        ZveiFreqTable[] result = new ZveiFreqTable[stateStackPos];
        for (int i = 0; i < stateStackPos; i++) {
            result[i] = stateStack[i];
        }
        return result;
    }

    private void addToTokenStack(ZveiFreqTable z) {
        LOG.debug(String.format("add to foundstack[%d] %s", stateStackPos, z.label));
        if (tokenIndex < firstStateTransitionIndex + (stateStackPos - 1) * tokenStack.length + 1) {
            //TODO     sink.fail(Arrays.copyOf(stateStack, stateStackPos));
            reset(false);
        }
        stateStack[stateStackPos++] = z;
        if (stateStackPos == 1) {
            firstStateTransitionIndex = tokenIndex;
        }
    }

    private boolean checkLength() {
        return tokenIndex <= firstStateTransitionIndex + stateStackPos * tokenStack.length + 2;
    }

    int getStatesCount() {
        return stateStack.length;
    }

    private class MapValue {

        private final ZveiFreqTable z;
        private int count;

        private MapValue(ZveiFreqTable z) {
            this.z = z;
            this.count = 1;
        }

        private MapValue(ZveiFreqTable z, int count) {
            this.z = z;
            this.count = count;
        }
    }
    final static int DEFAULT_DETECTION_THRESHOLD = 5;
    private final static Logger LOG = LoggerFactory.getLogger(ZveiFolgeDetector.class);
    private ZveiFreqTable[] stateStack;
    private int stateStackPos;
    private ZveiFreqTable[] tokenStack;
    private Map<ZveiFreqTable, MapValue> tokenMap = new EnumMap(ZveiFreqTable.class);
    private int tokenIndex;
    private Set<ZveiFreqTable> validInputTokens = EnumSet.noneOf(ZveiFreqTable.class);
    private int detectionThreshold = DEFAULT_DETECTION_THRESHOLD;
    private int firstStateTransitionIndex = -1;

    public ZveiFolgeDetector(int statesCount, int tokensPerState) {
        stateStack = new ZveiFreqTable[statesCount];
        tokenStack = new ZveiFreqTable[tokensPerState];
        validInputTokens.add(ZveiFreqTable.UNBEKANNT);

        validInputTokens.add(ZveiFreqTable.DIGIT_1);
        validInputTokens.add(ZveiFreqTable.DIGIT_2);
        validInputTokens.add(ZveiFreqTable.DIGIT_3);
        validInputTokens.add(ZveiFreqTable.DIGIT_4);
        validInputTokens.add(ZveiFreqTable.DIGIT_5);
        validInputTokens.add(ZveiFreqTable.DIGIT_6);
        validInputTokens.add(ZveiFreqTable.DIGIT_7);
        validInputTokens.add(ZveiFreqTable.DIGIT_8);
        validInputTokens.add(ZveiFreqTable.DIGIT_9);
        validInputTokens.add(ZveiFreqTable.DIGIT_0);

        validInputTokens.add(ZveiFreqTable.GRUPPENRUF);

        validInputTokens.add(ZveiFreqTable.LETTER_C);

        validInputTokens.add(ZveiFreqTable.WIEDERHOLUNG);
        reset(true);
    }

    public void reset(boolean cleanTokenStack) {
        LOG.debug("reset");
        Arrays.fill(stateStack, null);
        stateStackPos = 0;
        firstStateTransitionIndex = -1;
        if (cleanTokenStack) {
            tokenMap.clear();
            tokenMap.put(ZveiFreqTable.UNBEKANNT, new MapValue(ZveiFreqTable.UNBEKANNT, tokenStack.length));
            Arrays.fill(tokenStack, ZveiFreqTable.UNBEKANNT);
        }
    }

    public ZveiFreqTable[] setX(ZveiFreqTable token) {
        LOG.debug("Put value: " + token.label);
        if (!validInputTokens.contains(token)) {
            LOG.debug(String.format("Replace value: %s %s", token.label, ZveiFreqTable.UNBEKANNT.label));
            token = ZveiFreqTable.UNBEKANNT;
        }
        tokenIndex++;
        MapValue maxZ = addToAndProcessDetectionStack(token);

        if ((ZveiFreqTable.UNBEKANNT.equals(maxZ.z)) || (maxZ.count < detectionThreshold)) {
            if ((stateStackPos > 0) && !checkLength()) {
                ZveiFreqTable[] result = Arrays.copyOf(stateStack, stateStackPos);
                reset(false);
                return result;
            }
            return null;
        }

        if (stateStackPos == 0) {
            //foundstack empty so add the first entry
            addToTokenStack(token);
            return null;
        }

        if (stateStack[stateStackPos - 1].equals(maxZ.z)) {
            //found entry again , so increment lastSeen...

            if (stateStackPos == 1) {
                for (int i = tokenStack.length - 1; i >= 0; i--) {
                    if (tokenStack[i].equals(maxZ.z)) {
                        firstStateTransitionIndex = tokenIndex - i + tokenStack.length - 1;
                        break;
                    }
                }
            } else if (!checkLength()) {
                ZveiFreqTable[] result = Arrays.copyOf(stateStack, stateStackPos);
                reset(false);
                addToTokenStack(token);
                return result;
            }
            return null;
        }


        if (checkLength()) {
            addToTokenStack(token);
            if (stateStackPos == stateStack.length) {
                ZveiFreqTable[] result = Arrays.copyOf(stateStack, stateStackPos);
                reset(true);
                return result;
            }
        } else {
            ZveiFreqTable[] result = Arrays.copyOf(stateStack, stateStackPos);
            reset(false);
            addToTokenStack(token);
            return result;
        }
        return null;
    }
}