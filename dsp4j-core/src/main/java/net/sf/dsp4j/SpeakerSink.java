package net.sf.dsp4j;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
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
