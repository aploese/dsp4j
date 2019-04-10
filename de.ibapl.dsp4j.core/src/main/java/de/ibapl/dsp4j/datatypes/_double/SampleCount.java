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
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class SampleCount extends AbstractSampleProcessingBlock {

    private long sampleCount;
    private long overflowCount;

    @In
    public void clock() {
        sampleCount++;
        if (sampleCount < 0) {
            sampleCount = 0;
            overflowCount++;
        }
    }

    @Override
    public void setSampleRate(double sampleRate) {
        super.setSampleRate(sampleRate);
        sampleCount = 0;
        overflowCount = 0;
    }

    @Override
    public void reset() {
        super.reset();
        sampleCount = 0;
        overflowCount = 0;
    }

    /**
     * @return the sampleCount
     */
    @Out
    public long getSampleCount() {
        return sampleCount;
    }

    @Out
    public double getTime() {
        //TODO overrun !!!
        return (double)sampleCount / getSampleRate();
    }

    public String getTimeStr() {
        double t = getTime();
        int ms = ((int)(t * 1000)) % 1000;
        long lt = (long)t;
        int s = (int)(lt % 60);
        lt /= 60;
        int min = (int)(lt % 60);
        lt /= 60;
        int h = (int)(lt % 24);
        lt /= 24;
        return String.format("%d %d %02d:%02d:%02d.%03d", overflowCount, (int)lt, h,min,s,ms);
    }

}
