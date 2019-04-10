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
package de.ibapl.dsp4j.datatypes._short;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import static de.ibapl.dsp4j.AudioSink.DEFAULT_SECONDS_IN_BUFFER;
import de.ibapl.dsp4j.FileSink;

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