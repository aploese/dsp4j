package com.falstad.source;

public class SquareWaveform extends Waveform {

    private int ix;
    private double omega;
    private short smbuf[];

    @Override
    public int getChannels() {
        return 1;
    }

    @Override
    public boolean start(int samplerate) {
        super.start(samplerate);
        createBuffer();
        ix = 0;
        smbuf = new short[1];
        return true;
    }

    @Override
    public int getData(boolean logFreq, int inputValue, double inputW) {
        int i;
        int period = (int) (2 * Math.PI / inputW);
        if (period != smbuf.length) {
            smbuf = new short[period];
            for (i = 0; i != period / 2; i++) {
                smbuf[i] = 32000;
            }
            if ((period & 1) > 0) {
                smbuf[i++] = 0;
            }
            for (; i != period; i++) {
                smbuf[i] = -32000;
            }
        }
        for (i = 0; i != buffer.length; i++, ix++) {
            if (ix >= period) {
                ix = 0;
            }
            buffer[i] = smbuf[ix];
        }
        return buffer.length;
    }
}