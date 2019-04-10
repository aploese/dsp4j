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
 */
public abstract class TargetDataLineWrapper {

    private TargetDataLine tdl;
    private final byte[] buffer;
    private int bufferPos;
    private int bufferLength;
    private boolean endOfAudioData;
    /*
     * Just a shorthand to avoid long access for each sample in readXXX().
     */
    private final boolean bigEndian;
    
    public TargetDataLineWrapper(Mixer.Info mixerInfo, AudioFormat af, double secInBuffer) throws LineUnavailableException, IOException {
        this.tdl = AudioSystem.getTargetDataLine(af, mixerInfo);
        this.bigEndian = af.isBigEndian();
        buffer = new byte[tdl.getFormat().getFrameSize() * (int)(secInBuffer * af.getSampleRate())];
        tdl.open(tdl.getFormat(), buffer.length * 3);
        tdl.start();
        readBuffer();
    }

    public TargetDataLineWrapper(AudioFormat af, double secInBuffer) throws LineUnavailableException, IOException {
        this.tdl = AudioSystem.getTargetDataLine(af);
        this.bigEndian = af.isBigEndian();
        buffer = new byte[tdl.getFormat().getFrameSize() * (int)(secInBuffer * af.getSampleRate())];
        tdl.open(tdl.getFormat(), buffer.length * 3);
        tdl.start();
        readBuffer();
    }

    public TargetDataLineWrapper(TargetDataLine tdl, int framesInBuffer) throws IOException, LineUnavailableException {
        this.tdl = tdl;
        this.bigEndian = tdl.getFormat().isBigEndian();
        buffer = new byte[tdl.getFormat().getFrameSize() * framesInBuffer];
        tdl.open(tdl.getFormat(), buffer.length * 3);
        tdl.start();
        readBuffer();
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

    protected final byte readByte() throws IOException {
        byte result = buffer[bufferPos++];
        if (isBufferReaded()) {
            readBuffer();
        }
        return result;
    }

    protected final short readShort() throws IOException {
        short result;
        if (bigEndian) {
            result = (short)(((buffer[bufferPos++] << 8) & 0xFF00) | (buffer[bufferPos++] & 0x00FF));
        } else {
            result = (short)(((buffer[bufferPos++] & 0x00FF) | ((buffer[bufferPos++] << 8) & 0xFF00)));
        }
        if (isBufferReaded()) {
            readBuffer();
        } 
        return result;
    }

    protected final int readInt() throws IOException {
        int result; 
        if (bigEndian) {
            result = ((buffer[bufferPos++] << 24) & 0xFF000000) | ((buffer[bufferPos++] << 16) & 0x00FF0000) | ((buffer[bufferPos++] << 8) & 0x0000FF00) | (buffer[bufferPos++] & 0x000000FF);
        } else {
            result = ((buffer[bufferPos++] & 0x000000FF)) | ((buffer[bufferPos++] << 8) & 0x0000FF00) | ((buffer[bufferPos++] << 16) & 0x00FF0000) | ((buffer[bufferPos++] << 24) & 0xFF000000);
        }
        if (isBufferReaded()) {
            readBuffer();
        }
        return result;
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
     * @return false, if the end of stream is rechead (The last frames where read in the buffer).
     * @throws IOException 
     */
    public abstract boolean clock() throws IOException;

}