/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author aploese
 */
public abstract class AudioSink implements SampledBlock {
    
    public final static int DEFAULT_SECONDS_IN_BUFFER = 1;
    
    public static short scale0_2PI_to_short(double sample) {
        return (short)(((sample - Math.PI)/ Math.PI) * Short.MAX_VALUE);
    }

    public static short scaleBoolean_to_short(boolean value) {
        return value ? Short.MAX_VALUE : 0;
    }

    private AudioFormat audioFormat;
    protected byte[] buffer;
    protected int bufferSamplePos;
    protected int bytesWriten;
    protected final double secInBuffer;
    
    public AudioFormat.Encoding getEncoding() {
        return audioFormat.getEncoding();
    }

    public boolean isBigEndian() {
        return audioFormat.isBigEndian();
    }
    
    public int getChannels() {
        return audioFormat.getChannels();
    }

    public int getFrameSize() {
        return audioFormat.getFrameSize();
    }

    public int getSampleSizeInBits() {
        return audioFormat.getSampleSizeInBits();
    }

    @Override
    public double getSampleRate() {
        return getAudioFormat().getSampleRate();
    }

    @Override
    public void setSampleRate(double sampleRate) {
        if (getSampleRate() == sampleRate) {
            return;
        }
        setAudioFormat(new AudioFormat(audioFormat.getEncoding(), (float) sampleRate, audioFormat.getSampleSizeInBits(), audioFormat.getChannels(), audioFormat.getFrameSize(), (float)sampleRate, audioFormat.isBigEndian(), audioFormat.properties()));
    }

    protected AudioSink(AudioFormat audioFormat, double secInBuffer) {
        this.secInBuffer = secInBuffer;
        setAudioFormat(audioFormat);
    }

    protected void writeShortToBuffer(short sample) {
        if (isBigEndian()) {
            buffer[bufferSamplePos++] = (byte)(sample >>> 8);
            buffer[bufferSamplePos++] = (byte)sample;
        } else {
            buffer[bufferSamplePos++] = (byte)sample;
            buffer[bufferSamplePos++] = (byte)(sample >>> 8);
        }
    }
    
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    final protected void setAudioFormat(AudioFormat audioFormat) {
        if (this.audioFormat != null) {
            close();
        }
        this.audioFormat = audioFormat;
        buffer = new byte[audioFormat.getFrameSize() * (int)(secInBuffer * audioFormat.getSampleRate())];
        open();
    }

    public abstract void open();

    public abstract void flush();

    public abstract void close();

    public boolean isEmpty() {
        return bytesWriten == 0 && bufferSamplePos == 0;
    }
    
    public boolean isFull() {
        return bufferSamplePos == buffer.length;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
