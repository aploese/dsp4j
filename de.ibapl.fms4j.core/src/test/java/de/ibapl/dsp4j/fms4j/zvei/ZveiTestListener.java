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
package de.ibapl.dsp4j.fms4j.zvei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class ZveiTestListener implements ZveiFolgeContainerListener {
    List<ZveiFreqTable[]> data = new ArrayList<>();
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
