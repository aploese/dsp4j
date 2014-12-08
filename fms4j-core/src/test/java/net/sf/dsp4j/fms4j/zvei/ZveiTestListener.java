/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.zvei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class ZveiTestListener implements ZveiFolgeContainerListener {
    List<ZveiFreqTable[]> data = new ArrayList();
    int[] startIndices = new int[0];
    int currentDataIndex;

    void add(int startIndex, ZveiFreqTable[] zveiData) {
        data.add(zveiData);
        startIndices = Arrays.copyOf(startIndices, startIndices.length + 1);
        startIndices[startIndices.length - 1] = startIndex;
    }

    @Override
    public void success(ZveiFreqTable[] result) {
        assertTrue("more data received than expected", currentDataIndex < data.size());
        assertArrayEquals(String.format("Index %d", currentDataIndex), data.get(currentDataIndex), result);
        currentDataIndex++;
    }

    @Override
    public void fail(ZveiFreqTable[] result) {
        assertArrayEquals(String.format("Index %d expected: %s , but was %s", currentDataIndex, data.get(currentDataIndex), result), data.get(currentDataIndex), null);
        currentDataIndex++;
    }
    
}
