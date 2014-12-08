package com.falstad.source;

import java.util.Random;

public class NoiseWaveform extends Waveform {

    private Random random = new Random();

    @Override
    public boolean start(int samplerate) {
        super.start(samplerate);

        createBuffer();
        return true;
    }

    @Override
    public int getData(boolean logFreq, int inputValue, double inputW) {
        int i;
        for (i = 0; i != buffer.length; i++) {
            buffer[i] = (short) random.nextInt();
        }
        return buffer.length;
    }

    @Override
    public String getInputText() {
        return null;
    }

    @Override
    public boolean needsFrequency() {
        return false;
    }
}
