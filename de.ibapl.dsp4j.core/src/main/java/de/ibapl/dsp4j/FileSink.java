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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author aploese
 */
public abstract class FileSink extends AudioSink {

	private File wavOut;
	private File tempFile;
	private FileOutputStream os;

	protected FileSink(File wavOut, AudioFormat audioFormat, int framesInBuffer) {
		super(audioFormat, framesInBuffer);
		this.wavOut = wavOut;
	}

	@Override
	public void flush() {
		try {
			os.write(buffer, 0, bufferSamplePos * sampleSize);
			bufferSamplePos = 0;
		} catch (IOException ex) {
			throw new RuntimeException("Filebuffer", ex);
		}
	}

	public void delete() {
		if (os == null) {
			return;
		}
		try {
			os.close();
			os = null;
			tempFile.delete();
		} catch (IOException ex) {
			throw new RuntimeException("Filebuffer", ex);
		}
	}

	@Override
	public void close() {
		if (os == null) {
			return;
		}
		try {
			flush();
			os.flush();
			os.close();
			os = null;
			FileInputStream is = new FileInputStream(tempFile);
			AudioInputStream ais = new AudioInputStream(is, getAudioFormat(),
					tempFile.length() / getAudioFormat().getFrameSize());
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new FileOutputStream(getWavOut()));
			tempFile.delete();
		} catch (IOException ex) {
			throw new RuntimeException("Filebuffer", ex);
		}
	}

	@Override
	public void open() {
		try {
			if (tempFile == null) {
				tempFile = File.createTempFile("fileSink", "wav.raw");
				tempFile.deleteOnExit();
			}
			if (os == null) {
				os = new FileOutputStream(tempFile);
			}
		} catch (IOException ex) {
			throw new RuntimeException("Filebuffer", ex);
		}
	}

	/**
	 * @return the wavOut
	 */
	public File getWavOut() {
		return wavOut;
	}

	/**
	 * @param wavOut the wavOut to set
	 */
	public void setWavOut(File wavOut) {
		close();
		this.wavOut = wavOut;
		open();
	}

}
