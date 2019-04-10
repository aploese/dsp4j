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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author aploese
 */
public class SpeakerSink extends AudioSink {

    private SourceDataLine sdl;

    protected SpeakerSink(Mixer.Info mixerInfo, AudioFormat audioFormat, double secInBuffer) throws LineUnavailableException {
        super(audioFormat, secInBuffer);
    }

    protected SpeakerSink(AudioFormat audioFormat, double secInBuffer) throws LineUnavailableException {
        super(audioFormat, secInBuffer);
    }

    @Override
    public void flush() {
        sdl.write(buffer, 0, bufferSamplePos);
        bytesWriten += bufferSamplePos;
        bufferSamplePos = 0;
    }

    @Override
    public void close() {
        if (sdl != null) {
            flush();
            sdl.drain();
            sdl.stop();
            sdl.close();
            sdl = null;
        }
    }

    @Override
    public void open() {
        try {
            sdl = AudioSystem.getSourceDataLine(getAudioFormat());
            sdl.open(getAudioFormat());
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SpeakerSink.class.getName()).log(Level.SEVERE, null, ex);
        }
        sdl.start();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
