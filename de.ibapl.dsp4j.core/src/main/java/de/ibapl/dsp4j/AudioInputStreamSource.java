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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat.Encoding;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author aploese
 */
public abstract class AudioInputStreamSource implements Sampled {

	protected final boolean bigEndian;
	protected final AudioInputStream ais;
	protected final int sampleSize;
	protected final byte[] buffer;
	protected int bufferPos = -1;
	protected int bytesReaded;

	public AudioInputStreamSource(AudioInputStream ais, int samplesInBuffer) throws IOException {
		this.ais = ais;
		bigEndian = ais.getFormat().isBigEndian();
		this.sampleSize = ais.getFormat().getFrameSize();
		buffer = new byte[sampleSize * samplesInBuffer];
	}

	public AudioInputStreamSource(InputStream is, int samplesInBuffer)
			throws IOException, UnsupportedAudioFileException {
		this(AudioSystem.getAudioInputStream(is), samplesInBuffer);
	}

	public AudioInputStreamSource(File f, int samplesInBuffer) throws IOException, UnsupportedAudioFileException {
		this(AudioSystem.getAudioInputStream(f), samplesInBuffer);
	}

	public AudioInputStreamSource(String name, int samplesInBuffer) throws IOException, UnsupportedAudioFileException {
		this(new File(name), samplesInBuffer);
	}

	public AudioInputStreamSource(URL resource, int samplesInBuffer) throws IOException, UnsupportedAudioFileException {
		this(resource.getFile(), samplesInBuffer);
	}

	protected final boolean isBufferReaded() {
		return bufferPos == bytesReaded;
	}

	public final int getChannels() {
		return ais.getFormat().getChannels();
	}

	public final int getFrameSize() {
		return ais.getFormat().getFrameSize();
	}

	public final int getSampleSizeInBits() {
		return ais.getFormat().getSampleSizeInBits();
	}

	public final boolean isBigEndian() {
		return bigEndian;
	}

	public final Encoding getEncoding() {
		return ais.getFormat().getEncoding();
	}

	public final float getSampleRate() {
		return ais.getFormat().getSampleRate();
	}

	/**
	 *
	 * @return false, if the end of stream is reached (The last frames where read in
	 *         the buffer).
	 * @throws IOException
	 */
	public boolean nextSample() throws IOException {
		if ((bufferPos == -1) || ((bufferPos +1) * sampleSize >= bytesReaded)) {
			bytesReaded = ais.read(buffer);
			if (bytesReaded > 0) {
				bufferPos = 0;
				return true;
			} else {
				bufferPos = -1;
				return false;
			}
		}
		bufferPos++;
		return true;
	}
	
}