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
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.ibapl.dsp4j.AudioInputStreamSource;

/**
 *
 * @author aploese 16 Bit PCM
 */
public class ShortSampledSource extends AudioInputStreamSource {

	public ShortSampledSource(AudioInputStream ais, int samplesInBuffer) throws IOException {
		super(ais, samplesInBuffer);
	}

	public ShortSampledSource(File f, int samplesInBuffer) throws IOException, UnsupportedAudioFileException {
		super(f, samplesInBuffer);
	}

	public ShortSampledSource(InputStream is, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
		super(is, framesInBuffer);
	}

	public short getShort(int channel) {
		final int pos = bufferPos * sampleSize + channel * 2;
		if (bigEndian) {
			return (short) (((buffer[pos] & 0xFF) << 8) | (buffer[pos + 1] & 0xFF));
		} else {
			return (short) (((buffer[pos] & 0xFF) | ((buffer[pos + 1] & 0xFF) << 8)));
		}
	}


}
