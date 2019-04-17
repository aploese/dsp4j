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

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import de.ibapl.dsp4j.TargetDataLineWrapper;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class ShortTargetDataLineWrapper extends TargetDataLineWrapper {
    
    public ShortTargetDataLineWrapper(Mixer.Info mixerInfo, int channels, double sampleRate, boolean signed, boolean bigEndian, int framesInBuffer) throws IOException, LineUnavailableException {
        super(mixerInfo, new AudioFormat((float)sampleRate, 2 * 8, channels, signed, bigEndian), framesInBuffer);
    }
    
    public ShortTargetDataLineWrapper(Mixer.Info mixerInfo, int channels, double sampleRate, int framesInBuffer) throws IOException, LineUnavailableException {
        super(mixerInfo, new AudioFormat((float)sampleRate, 2 * 8, channels, true, false), framesInBuffer);
    }

    public ShortTargetDataLineWrapper(int channels, double sampleRate, int framesInBuffer) throws IOException, LineUnavailableException {
        super(new AudioFormat((float)sampleRate, 2 * 8, channels, true, false), framesInBuffer);
    }
    
    public final short getShort(int channel){
    	final int pos = bufferPos * sampleSize + channel * 2; 
        if (bigEndian) {
            return (short)(((buffer[bufferPos + channel] << 8) & 0xFF00) | (buffer[bufferPos + channel + 1] & 0x00FF));
        } else {
            return (short)(((buffer[bufferPos + channel] & 0x00FF) | ((buffer[bufferPos + channel + 1] << 8) & 0xFF00)));
        }
    }

}
