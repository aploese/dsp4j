/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import net.sf.dsp4j.SpeakerSink;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class MonoShortSpeakerSink extends  SpeakerSink {


    public void setX(short sample) {
        writeShortToBuffer(sample);
        if (isFull()) {
            flush();
        }
    }

    public MonoShortSpeakerSink(Mixer.Info mInfo, double sampleRate, boolean signed, boolean bigEndian, int framesInBuffer) throws LineUnavailableException {
        super(mInfo, new AudioFormat((float)sampleRate, 2 * 8, 1, signed, bigEndian), framesInBuffer);
    }

    public MonoShortSpeakerSink(double sampleRate, boolean signed, boolean bigEndian, int framesInBuffer) throws LineUnavailableException {
        super(new AudioFormat((float)sampleRate, 2 * 8, 1, signed, bigEndian), framesInBuffer);
    }

    public MonoShortSpeakerSink(Mixer.Info mInfo, double sampleRate) throws LineUnavailableException {
        super(mInfo, new AudioFormat((float)sampleRate, 2 * 8, 1, true, false), DEFAULT_SECONDS_IN_BUFFER);
    }
    
    public MonoShortSpeakerSink(double sampleRate) throws LineUnavailableException {
        super(new AudioFormat((float)sampleRate, 2 * 8, 1, true, false), DEFAULT_SECONDS_IN_BUFFER);
    }

}
