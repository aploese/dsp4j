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
package de.ibapl.dsp4j.fms4j;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author aploese
 */
public class Util {

    public final static int DEFAULT_SAMPLE_RATE = 22050;

    public static AudioInputStream getAudioStream(byte[] buffer, AudioFormat af) {
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        return new AudioInputStream(is, af, buffer.length / af.getFrameSize());
    }

    private static class AudioPlayer implements Runnable {

        private AudioInputStream ais;

        @Override
        public void run() {
            try {
                SourceDataLine sdl = AudioSystem.getSourceDataLine(ais.getFormat());
                byte[] buffer = new byte[1024];
                sdl.open(ais.getFormat(), buffer.length);
                sdl.start();
                int readed = 0;
                while ((readed = ais.read(buffer)) != -1) {
                    sdl.write(buffer, 0, readed);
                }
                sdl.drain();
                sdl.stop();
                sdl.close();
            } catch (Exception ex) {
                System.err.print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX " + ex);
            }
        }
    }

    private static class AudioWriter implements Runnable {

        private AudioInputStream ais;
        private String fileName;

        @Override
        public void run() {
            try {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new FileOutputStream(fileName));
            } catch (IOException ex) {
                System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX " + ex);
            }
        }
    }

    public static void play(AudioInputStream ais) throws LineUnavailableException {
        AudioPlayer ap = new AudioPlayer();
        ap.ais = ais;
        new Thread(ap).start();
        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void play(byte[] buffer, AudioFormat af) throws LineUnavailableException {
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af, buffer.length);
        sdl.start();
        sdl.write(buffer, 0, buffer.length);
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

    public static void write(AudioInputStream ais, String fileName) throws LineUnavailableException {
        AudioWriter aw = new AudioWriter();
        aw.ais = ais;
        aw.fileName = fileName;
        new Thread(aw).start();
    }
}
