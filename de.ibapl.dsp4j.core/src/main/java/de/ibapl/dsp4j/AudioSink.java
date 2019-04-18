/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author aploese
 */
public abstract class AudioSink implements SampledBlock, Sampled {
    
    public static short scale0_2PI_to_short(double sample) {
        return (short)(((sample - Math.PI)/ Math.PI) * Short.MAX_VALUE);
    }

    public static short scaleBoolean_to_short(boolean value) {
        return value ? Short.MAX_VALUE : 0;
    }

    private AudioFormat audioFormat;
    protected final byte[] buffer;
    protected int bufferSamplePos;
    protected int sampleSize;
    
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

    protected AudioSink(AudioFormat audioFormat, int framesInBuffer) {
        setAudioFormat(audioFormat);
        buffer = new byte[sampleSize * framesInBuffer];
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    final protected void setAudioFormat(AudioFormat audioFormat) {
    	if (this.audioFormat != null) {
            close();
        }
        this.audioFormat = audioFormat;
        this.sampleSize = audioFormat.getFrameSize();
        open();
    }

    @Override
    public boolean nextSample() throws IOException {
    	bufferSamplePos++;
    	if (bufferSamplePos * sampleSize >= buffer.length) {
    		flush();
    	}
    	return true;
    }
    
    public abstract void open();

    public abstract void flush();

    public abstract void close();

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
