package com.falstad.source;

public abstract class Waveform {

    public short buffer[];
    protected int sampleRate;

    public boolean start(int sampleRate) {
        this.sampleRate = sampleRate;
        return true;
    }

    public abstract int getData(boolean logFreq, int inputValue, double inputW);

    public int getChannels() {
        return 2;
    }

    protected void createBuffer() {
        buffer = new short[getPower2(sampleRate / 12) * getChannels()];
    }

    public String getInputText() {
        return "Input Frequency";
    }

    public boolean needsFrequency() {
        return true;
    }
    // TODO apl Math.power()???
    public static int getPower2(int n) {
        int o = 2;
        while (o < n) {
            o *= 2;
        }
        return o;
    }
}
