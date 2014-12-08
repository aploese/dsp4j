package com.falstad.source;

public class SweepWaveform extends Waveform {

    private int ix;
    private double omega, nextOmega, t, startOmega;

    @Override
    public int getChannels() {
        return 1;
    }

    @Override
    public boolean start(int samplerate) {
        super.start(samplerate);
        createBuffer();
        ix = 0;
        startOmega = nextOmega = omega = 2 * Math.PI * 40 / sampleRate;
        t = 0;
        return true;
    }

    @Override
    public int getData(boolean logFreq, int inputValue, double inputW) {
        int i;
        double nmul = 1;
        double nadd = 0;
        double maxspeed = 1 / (.66 * sampleRate);
        double minspeed = 1 / (sampleRate * 16);
        if (logFreq) {
            nmul = Math.pow(2 * Math.PI / startOmega,
                    2 * (minspeed + (maxspeed - minspeed) * inputValue / 1000.));
        } else {
            nadd = (2 * Math.PI - startOmega)
                    * (minspeed + (maxspeed - minspeed) * inputValue / 1000.);
        }
        for (i = 0; i != buffer.length; i++) {
            ix++;
            t += omega;
            if (t > 2 * Math.PI) {
                t -= 2 * Math.PI;
                omega = nextOmega;
                if (nextOmega > Math.PI) {
                    omega = nextOmega = startOmega;
                }
            }
            buffer[i] = (short) (Math.sin(t) * 32000);
            nextOmega = nextOmega * nmul + nadd;
        }
        return buffer.length;
    }

    @Override
    public String getInputText() {
        return "Sweep Speed";
    }

    @Override
    public boolean needsFrequency() {
        return false;
    }
}
