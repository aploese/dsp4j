/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import net.sf.dsp4j.FileSink;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class MonoShortFileSink extends  FileSink {


    public void setX(short sample) {
        writeShortToBuffer(sample);
        if (isFull()) {
            flush();
        }
    }

    public MonoShortFileSink(File wavOut, double sampleRate, boolean signed, boolean bigEndian, int framesInBuffer) {
        super(wavOut, new AudioFormat((float)sampleRate, 2 * 8, 1, signed, bigEndian), framesInBuffer);
    }

    public MonoShortFileSink(File wavOut, double sampleRate) {
        super(wavOut, new AudioFormat((float)sampleRate, 2 * 8, 1, true, false), DEFAULT_SECONDS_IN_BUFFER);
    }
}
