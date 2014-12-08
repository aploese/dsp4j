package com.falstad.source;

public class TriangleWaveform extends Waveform {

    private int ix;
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
            double p2 = period / 2.;
            for (i = 0; i < p2; i++) {
                smbuf[i] = (short) (i / p2 * 64000 - 32000);
            }
            for (; i != period; i++) {
                smbuf[i] = (short) ((2 - i / p2) * 64000 - 32000);
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
