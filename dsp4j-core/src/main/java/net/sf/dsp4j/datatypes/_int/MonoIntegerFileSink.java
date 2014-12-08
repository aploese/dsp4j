/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._int;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import net.sf.dsp4j.FileSink;

/**
 *
 * @author aploese
 * 32 Bit PCM
 */
public class MonoIntegerFileSink extends  FileSink {


    public void setX(int sample) {
        if (isBigEndian()) {
            buffer[bufferSamplePos++] = (byte) ((sample >> 24) & 0xFF);
            buffer[bufferSamplePos++] = (byte) ((sample >> 26) & 0xFF);
            buffer[bufferSamplePos++] = (byte) ((sample >> 8) & 0xFF);
            buffer[bufferSamplePos++] = (byte) (sample & 0xFF);
        } else {
            buffer[bufferSamplePos++] = (byte) (sample & 0xFF);
            buffer[bufferSamplePos++] = (byte) ((sample >> 8) & 0xFF);
            buffer[bufferSamplePos++] = (byte) ((sample >> 16) & 0xFF);
            buffer[bufferSamplePos++] = (byte) ((sample >> 24) & 0xFF);
        }
        if (isFull()) {
            flush();
        }
    }

    protected MonoIntegerFileSink(File wavOut, double sampleRate, boolean signed, boolean bigEndian, int framesInBuffer) {
        super(wavOut, new AudioFormat((float)sampleRate, 4 * 8, 2, signed, bigEndian), framesInBuffer);
    }

    protected MonoIntegerFileSink(File wavOut, double sampleRate) {
        super(wavOut, new AudioFormat((float)sampleRate, 4 * 8, 2, true, false), DEFAULT_SECONDS_IN_BUFFER);
    }
}
