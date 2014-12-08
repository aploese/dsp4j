/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

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
