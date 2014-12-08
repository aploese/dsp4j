package com.falstad.source;

import java.util.Random;

public class PeriodicNoiseWaveform extends Waveform {

    private short smbuf[];
    private int ix;
    private Random random = new Random();

    @Override
    public int getChannels() {
        return 1;
    }

    @Override
    public boolean start(int samplerate) {
        super.start(samplerate);
        createBuffer();
        smbuf = new short[1];
        ix = 0;
        return true;
    }

    @Override
    public int getData(boolean logFreq, int inputValue, double inputW) {
        int period = (int) (2 * Math.PI / inputW);
        if (period != smbuf.length) {
            smbuf = new short[period];
            int i;
            for (i = 0; i != period; i++) {
                smbuf[i] = (short) random.nextInt();
            }
        }
        int i;
        for (i = 0; i != buffer.length; i++, ix++) {
            if (ix >= period) {
                ix = 0;
            }
            buffer[i] = smbuf[ix];
        }
        return buffer.length;
    }
}
