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
package de.ibapl.dsp4j.datatypes._int;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import de.ibapl.dsp4j.FileSink;

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
