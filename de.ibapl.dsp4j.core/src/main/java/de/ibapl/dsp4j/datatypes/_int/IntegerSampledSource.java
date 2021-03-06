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
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import de.ibapl.dsp4j.AudioInputStreamSource;

/**
 *
 * @author aploese
 * 32 Bit PCM
 */
public class IntegerSampledSource extends AudioInputStreamSource {
    
    public IntegerSampledSource(File f) throws IOException, UnsupportedAudioFileException {
        this(f, 1);
    }

    public IntegerSampledSource(File f, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        super(f, framesInBuffer);
    }
    
	protected final int getInt(int channel) {
		final int pos = bufferPos * sampleSize + channel * 4;
		if (bigEndian) {
			return ((buffer[pos] & 0xFF) << 24) | ((buffer[pos + 1] & 0xFF) << 16)
					| ((buffer[pos + 2] & 0xFF) << 8) | (buffer[pos + 3] & 0xFF);
		} else {
			return ((buffer[pos + channel] & 0xFF)) | ((buffer[pos + 1] & 0xFF) << 8)
					| ((buffer[pos + 2] & 0xFF) << 16)
					| ((buffer[pos + 3] & 0xFF) << 24);
		}
	}

}
