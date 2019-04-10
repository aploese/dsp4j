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

import java.util.Arrays;
import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.datatypes._double.iirfilter.AbstractCascadedDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;

/**
 *
 * @author aploese
 */
public class ZveiContainer extends AbstractSampleProcessingBlock {

    private final ZveiDemodulator zveiDemodulator;
    private final ZveiFolgeDetector zveiFolgeDetector;
    private final ZveiFolgeContainerListener zveiFolgeContainerListener;
    private AbstractCascadedDoubleIirFilter inHpFilter;
    private AbstractCascadedDoubleIirFilter signalFilter;
    private int sampleCount;
    private int samplesSinceLast;
    private ZveiFreqTable currentZ = ZveiFreqTable.UNBEKANNT;
    private int zCount;
    private int minZCount;
    private int maxZCount;
    private int currentZCount;
    private int minCurrentZCount;
    private ZveiFreqTable[] result = new ZveiFreqTable[5];
    private int resultIndex;

    public ZveiContainer(ZveiFolgeContainerListener zveiFolgeContainerListener) {
        this.zveiFolgeContainerListener = zveiFolgeContainerListener;
        zveiDemodulator = new ZveiDemodulator();
        zveiFolgeDetector = new ZveiFolgeDetector(5, 7);
    }

    @In
    public ZveiFreqTable[] setX(double sample) {
        zCount++;
        currentZCount++;
        samplesSinceLast++;
        final boolean oldNeg = inHpFilter.getY() < 0;
        inHpFilter.setX(sample);
        if (oldNeg && inHpFilter.getY() >= 0) {
            sampleCount = samplesSinceLast;
            samplesSinceLast = 0;
        }
        signalFilter.setX(sampleCount);

        int frq = (int) (getSampleRate() / signalFilter.getY());
        ZveiFreqTable newZ = ZveiFreqTable.find(frq);
        if (newZ == currentZ) {
            if (currentZCount == minCurrentZCount) {
                if (newZ == ZveiFreqTable.UNBEKANNT) {
                    resultIndex = 0;
                    currentZCount = 0;
                    zCount = 0;
                } else {
                    if (resultIndex == 0) {
                        zCount = currentZCount;
                    }
                    result[resultIndex++] = newZ;
                }
            }
            if (resultIndex == 5) {
                if (zCount < minZCount) {
                    resultIndex = 0;
                    currentZCount = 0;
                    zCount = 0;
                } else {
                    final ZveiFreqTable[] zt = Arrays.copyOf(result, result.length);
                    zveiFolgeContainerListener.success(zt);
                    resultIndex = 0;
                    return result;
                }

            }
        } else {
            currentZCount = 0;
        }
        if (zCount > maxZCount) {
            resultIndex = 0;
            currentZCount = 0;
            zCount = 0;
        }
        currentZ = newZ;
        return null;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        DoubleIirFilterGenerator iirGenerator = new DoubleIirFilterGenerator(sampleRate);
        inHpFilter = iirGenerator.getBP_ButterFc(4, 50, 4000, AbstractCascadedDoubleIirFilter.class); // TODO Elliptic??
        signalFilter = iirGenerator.getLP_ButterFc(2, 70, AbstractCascadedDoubleIirFilter.class);
        zveiDemodulator.setSampleRate(sampleRate);
        minCurrentZCount = (int) (0.050 * sampleRate); // 50 ms min
        minZCount = (int) (0.310 * sampleRate) + 1; // 5 * 70md = 350 ms
        maxZCount = (int) (0.350 * sampleRate) + 1; // 5 * 70md = 350 ms
    }

    @Override
    public double getSampleRate() {
        return zveiDemodulator.getSampleRate();
    }

    @Override
    public void reset() {
        zveiDemodulator.reset();
    }

    /**
     * @return the signalFilter
     */
    public AbstractCascadedDoubleIirFilter getSignalFilter() {
        return signalFilter;
    }

    /**
     * @return the sampleCount
     */
    public int getSampleCount() {
        return sampleCount;
    }

    /**
     * @return the inHpFilter
     */
    public AbstractCascadedDoubleIirFilter getInHpFilter() {
        return inHpFilter;
    }
}
