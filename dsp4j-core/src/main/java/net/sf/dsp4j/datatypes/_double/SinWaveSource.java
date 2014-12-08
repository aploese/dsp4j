package net.sf.dsp4j.datatypes._double;

import java.util.ArrayList;
import java.util.List;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import static net.sf.dsp4j.DspConst.TWO_PI;
import net.sf.dsp4j.In;

/**
 *
 * @author aploese
 */
public class SinWaveSource extends AbstractSampleProcessingBlock {

    public static abstract class SinWaveSnipplet {
        public final double frequency;
        
        public SinWaveSnipplet(double frequency) {
            this.frequency = frequency;
        }

        public abstract double getDuration();
        
    }
    
    public static class SinWaveByPeriods extends SinWaveSnipplet {
        public final double periods;

        public SinWaveByPeriods(double frequency, double periods) {
            super(frequency);
            this.periods = periods;
        }

        @Override
        public double getDuration() {
            return periods / frequency;
        }
    }
    
    public static class SinWaveByDuration extends SinWaveSnipplet  {
        public final double durationInSec;

        public SinWaveByDuration(double frequency, double durationInSec) {
            super(frequency);
            this.durationInSec = durationInSec;
        }

        @Override
        public double getDuration() {
            return durationInSec;
        }
    }
    
    private double sampleDrift;
    private double deltaPhi;
    private double currentPhi;
    private double y;
    private int samplesLeft;
    private int frequencyIndex = -1;
    private List<SinWaveSnipplet> frequencies = new ArrayList();
    private boolean firstSample;

    public SinWaveSource(double precisition) {
        super();
    }

    @Override
    public void setSampleRate(double sampleRate) {
        super.setSampleRate(sampleRate);
        reset();
    }

    private double calcSamplesOfTime(double time) {
        final double result = getSampleRate() * time + 1;
        if (sampleDrift > 0) {
            return result - (1 - sampleDrift);
        } else {
            return result;
        }
    }
    
    private int calcMinSamplesOfTIme(double time) {
         return (int) Math.floor(calcSamplesOfTime(time));
    }

    /*
     * returns false if we exeeding the given duration
     */
    @In
    public void clock() {
        currentPhi += deltaPhi;
        if (firstSample) {
            deltaPhi = calcW(frequencies.get(frequencyIndex).frequency);
            firstSample = false;
            if (sampleDrift > 0) {
              currentPhi += (1- sampleDrift) * deltaPhi;    
            } 
        } 
        
        if (currentPhi >= TWO_PI) {
            currentPhi -= TWO_PI;
        }
        y = Math.sin(currentPhi);
        samplesLeft--;
        if (samplesLeft == 0) {
          nextFrequency();  
        } 
    }
    
    public boolean isAfterLast() {
        return samplesLeft < 0;
    }

    public boolean isLast() {
        return samplesLeft == 0;
    }

    /**
     * @return the deltaPhi
     */
    public double getDeltaPhi() {
        return deltaPhi;
    }

    /**
     * @return the nextPhi
     */
    public double getCurrentPhi() {
        return currentPhi;
    }

    /**
     * @return the frequency
     */
    public double getCurrentFrequency() {
        return frequencies.get(frequencyIndex).frequency;
    }

    protected boolean add(SinWaveSnipplet sinWaveSnipplet) {
        final boolean result = frequencies.add(sinWaveSnipplet);
        if (frequencyIndex == -1) {
            firstSample = true;
            frequencyIndex = 0;
            samplesLeft = calcMinSamplesOfTIme(sinWaveSnipplet.getDuration());
        } else if (isAfterLast() || isLast()) {
            firstSample = true;
            frequencyIndex++;
            samplesLeft = calcMinSamplesOfTIme(sinWaveSnipplet.getDuration());
        }
        return result;
    }
    
    public boolean addPeriods(double frq, double periods) {
        return add(new SinWaveByPeriods(frq, periods));
    }

    public boolean addDuration(double frq, double durationInSec) {
        return add(new SinWaveByDuration(frq, durationInSec));
    }


    public double clock(double level) {
        clock();
        return y * level;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    
    @Override
    public void reset() {
        super.reset();
        frequencies.clear();
        frequencyIndex = -1;
    }
    
    private void nextFrequency() {
        double absSamples = calcSamplesOfTime(frequencies.get(frequencyIndex).getDuration());
        sampleDrift = absSamples - Math.floor(absSamples);
        deltaPhi *= sampleDrift;
        if (frequencyIndex < frequencies.size() - 1) {
            firstSample = true;
            frequencyIndex++;
            samplesLeft = calcMinSamplesOfTIme(frequencies.get(frequencyIndex).getDuration());
        }
    }

}
