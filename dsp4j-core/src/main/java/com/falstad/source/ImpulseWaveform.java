package com.falstad.source;

public class ImpulseWaveform extends Waveform {

    private int ix;

    @Override
    public int getChannels() {
        return 1;
    }

    @Override
    public boolean start(int samplerate) {
        super.start(samplerate);
        createBuffer();
        ix = 0;
        return true;
    }

    @Override
    public int getData(boolean logFreq, int inputValue, double inputW) {
        int i;
        int ww = inputValue / 51 + 1;
        int period = 10000 / ww;
        for (i = 0; i != buffer.length; i++) {
            short q = 0;
            if (ix % period == 0) {
                q = 32767;
            }
            ix++;
            buffer[i] = q;
        }
        return buffer.length;
    }

    @Override
    public String getInputText() {
        return "Impulse Frequency";
    }

    @Override
    public boolean needsFrequency() {
        return false;
    }
}
