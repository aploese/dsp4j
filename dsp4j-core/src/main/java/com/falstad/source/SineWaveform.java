package com.falstad.source;

public class SineWaveform extends Waveform {

    int ix;

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
        for (i = 0; i != buffer.length; i++) {
            ix++;
            buffer[i] = (short) (Math.sin(ix * inputW) * 32000);
        }
        return buffer.length;
    }
}
