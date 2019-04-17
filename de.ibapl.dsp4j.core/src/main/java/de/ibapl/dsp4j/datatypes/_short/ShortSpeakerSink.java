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

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import de.ibapl.dsp4j.SpeakerSink;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class ShortSpeakerSink extends  SpeakerSink {


    public void setShort(int channel, short sample) {
    	final int pos =  bufferSamplePos * sampleSize + channel * 2;
    	if (isBigEndian()) {
             buffer[pos] = (byte)(sample >>> 8);
             buffer[pos + 1] = (byte)sample;
         } else {
             buffer[pos] = (byte)sample;
             buffer[pos +1] = (byte)(sample >>> 8);
    }
    }

    public ShortSpeakerSink(Mixer.Info mixerInfo, AudioFormat audioFormat, int samplesInBuffer) throws LineUnavailableException {
        super(mixerInfo, audioFormat, samplesInBuffer);
    }

    public ShortSpeakerSink(AudioFormat audioFormat, int samplesInBuffer) throws LineUnavailableException {
        super(audioFormat, samplesInBuffer);
    }

	public ShortSpeakerSink(int channels, double samplerate, int samplesInBuffer) throws LineUnavailableException {
		this(new AudioFormat((float)samplerate, 2 * 8, channels, true, false), samplesInBuffer);
	}
    
}