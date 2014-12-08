/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import static net.sf.dsp4j.AudioSink.DEFAULT_SECONDS_IN_BUFFER;
import net.sf.dsp4j.FileSink;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class ShortFileSink extends  FileSink {


    public void setX(short x1) {
        writeShortToBuffer(x1);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short x1, short x2) {
        writeShortToBuffer(x1);
        writeShortToBuffer(x2);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short x1, short x2, short x3) {
        writeShortToBuffer(x1);
        writeShortToBuffer(x2);
        writeShortToBuffer(x3);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short x1, short x2, short x3, short x4) {
        writeShortToBuffer(x1);
        writeShortToBuffer(x2);
        writeShortToBuffer(x3);
        writeShortToBuffer(x4);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short x1, short x2, short x3, short x4, short x5) {
        writeShortToBuffer(x1);
        writeShortToBuffer(x2);
        writeShortToBuffer(x3);
        writeShortToBuffer(x4);
        writeShortToBuffer(x5);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short x1, short x2, short x3, short x4, short x5, short x6) {
        writeShortToBuffer(x1);
        writeShortToBuffer(x2);
        writeShortToBuffer(x3);
        writeShortToBuffer(x4);
        writeShortToBuffer(x5);
        writeShortToBuffer(x6);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short x1, short x2, short x3, short x4, short x5, short x6, short x7) {
        writeShortToBuffer(x1);
        writeShortToBuffer(x2);
        writeShortToBuffer(x3);
        writeShortToBuffer(x4);
        writeShortToBuffer(x5);
        writeShortToBuffer(x6);
        writeShortToBuffer(x7);
        if (isFull()) {
            flush();
        }
    }

    public void setX(short ... x) {
        for (short s : x) {
            writeShortToBuffer(s);
        }
        if (isFull()) {
            flush();
        }
    }

    public ShortFileSink(File wavOut, double sampleRate, int channels, boolean signed, boolean bigEndian, int framesInBuffer) {
        super(wavOut, new AudioFormat((float)sampleRate, 2 * 8, channels, signed, bigEndian), framesInBuffer);
    }

    public ShortFileSink(File wavOut, double sampleRate, int channels) {
        super(wavOut, new AudioFormat((float)sampleRate, 2 * 8, channels, true, false ), DEFAULT_SECONDS_IN_BUFFER);
    }

}
