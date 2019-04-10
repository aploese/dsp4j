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
import static de.ibapl.dsp4j.AudioSink.DEFAULT_SECONDS_IN_BUFFER;
import de.ibapl.dsp4j.TargetDataLineWrapper;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class MonoShortTargetDataLineWrapper extends TargetDataLineWrapper {
    
    private short y;
    
    public MonoShortTargetDataLineWrapper(Mixer.Info mixerInfo, double sampleRate, boolean signed, boolean bigEndian, int framesInBuffer) throws IOException, LineUnavailableException {
        super(mixerInfo, new AudioFormat((float)sampleRate, 2 * 8, 1, signed, bigEndian), framesInBuffer);
    }
    
    public MonoShortTargetDataLineWrapper(Mixer.Info mixerInfo, double sampleRate) throws IOException, LineUnavailableException {
        super(mixerInfo, new AudioFormat((float)sampleRate, 2 * 8, 1, true, false), DEFAULT_SECONDS_IN_BUFFER);
    }

    public MonoShortTargetDataLineWrapper(Mixer.Info mixerInfo, double sampleRate, int framesInBuffer) throws IOException, LineUnavailableException {
        super(mixerInfo, new AudioFormat((float)sampleRate, 2 * 8, 1, true, false), framesInBuffer);
    }

    public MonoShortTargetDataLineWrapper(double sampleRate) throws IOException, LineUnavailableException {
        super(new AudioFormat((float)sampleRate, 2 * 8, 1, true, false), DEFAULT_SECONDS_IN_BUFFER);
    }
    
    @Override
    public boolean clock() throws IOException {
        y = readShort();
        return !isEndOfAudioData();
    }
    
    public short getY() {
        return y;
    }
}
