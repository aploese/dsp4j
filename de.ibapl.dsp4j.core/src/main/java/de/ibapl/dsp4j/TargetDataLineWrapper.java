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
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author aploese
 * 
 * To get a sample first call clock to read from the underlying TargetDataLine or move to the next sample in the buffer
 */
public abstract class TargetDataLineWrapper {

    private TargetDataLine tdl;
    protected final byte[] buffer;
    protected final int channels;
    protected final int sampleSize;
    protected int bufferPos;
    protected int bufferLength;
    private boolean endOfAudioData;
    /*
     * Just a shorthand to avoid long access for each sample in readXXX().
     */
    protected final boolean bigEndian;
    
    public TargetDataLineWrapper(Mixer.Info mixerInfo, AudioFormat af, int samplesInBuffer) throws LineUnavailableException, IOException {
        this.tdl = AudioSystem.getTargetDataLine(af, mixerInfo);
        this.bigEndian = af.isBigEndian();
        this.channels = af.getChannels();
        this.sampleSize = af.getChannels() * af.getFrameSize();
        buffer = new byte[sampleSize * samplesInBuffer];
        tdl.open(tdl.getFormat(), buffer.length * 3);
        tdl.start();
    }

    public TargetDataLineWrapper(AudioFormat af, int samplesInBuffer) throws LineUnavailableException, IOException {
        this.tdl = AudioSystem.getTargetDataLine(af);
        this.bigEndian = af.isBigEndian();
        this.channels = af.getChannels();
        this.sampleSize = af.getChannels() * af.getFrameSize();
        buffer = new byte[sampleSize * samplesInBuffer];
        tdl.open(tdl.getFormat(), buffer.length * 3);
        tdl.start();
    }

    public TargetDataLineWrapper(TargetDataLine tdl, int samplesInBuffer) throws IOException, LineUnavailableException {
        this.tdl = tdl;
        this.bigEndian = tdl.getFormat().isBigEndian();
        this.channels = tdl.getFormat().getChannels();
        this.sampleSize = tdl.getFormat().getChannels() * tdl.getFormat().getFrameSize();
        buffer = new byte[sampleSize * samplesInBuffer];
        tdl.open(tdl.getFormat(), buffer.length * 3);
        tdl.start();
    }

    private void readBuffer() throws IOException {
        bufferLength = tdl.read(buffer, 0, buffer.length);
        if (bufferLength == 0) {
            throw new RuntimeException("Cant read datat from line ...");
        }
        bufferPos = 0;
        endOfAudioData = bufferLength == -1;
    }
    
    protected final boolean isBufferReaded() {
        return bufferPos == bufferLength;
    }

    public final int getChannels() {
        return tdl.getFormat().getChannels();
    }

    public final int getFrameSize() {
        return tdl.getFormat().getFrameSize();
    }

    public final int getSampleSizeInBits() {
        return tdl.getFormat().getSampleSizeInBits();
    }

    public final boolean isBigEndian() {
        return tdl.getFormat().isBigEndian();
    }

    public final Encoding getEncoding() {
        return tdl.getFormat().getEncoding();
    }

    public final float getSampleRate() {
        return tdl.getFormat().getSampleRate();
    }

    protected final byte getByte(int channel) {
        return buffer[bufferPos + channel];
    }

    protected final int getInt(int channel) {
        if (bigEndian) {
            return ((buffer[bufferPos + channel] << 24) & 0xFF000000) | ((buffer[bufferPos  + channel +1] << 16) & 0x00FF0000) | ((buffer[bufferPos  + channel +2] << 8) & 0x0000FF00) | (buffer[bufferPos  + channel +3] & 0x000000FF);
        } else {
            return ((buffer[bufferPos + channel] & 0x000000FF)) | ((buffer[bufferPos  + channel +1] << 8) & 0x0000FF00) | ((buffer[bufferPos  + channel +2] << 16) & 0x00FF0000) | ((buffer[bufferPos  + channel +3] << 24) & 0xFF000000);
        }
    }

    public final boolean isEndOfAudioData() {
        return endOfAudioData;
    }

    public final TargetDataLine getTargetDataLine() {
        return tdl;
    } 
    
    @Override
    protected void finalize() throws Throwable {
        if (tdl != null) {
        tdl.stop();
        tdl.close();
        }
        super.finalize();
    }
    
   /**
     * 
     * @return false, if the end of stream is reached (The last frames where read in the buffer).
     * @throws IOException 
     */
    public final boolean nextSample() throws IOException {
        if (bufferPos == bufferLength) {
        	readBuffer();
        } else {
        	bufferPos += channels;
        }
        return !endOfAudioData;
    }

}