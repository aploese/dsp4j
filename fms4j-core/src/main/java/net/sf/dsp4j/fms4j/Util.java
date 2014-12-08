/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import javax.sound.sampled.UnsupportedAudioFileException;

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
