/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import static net.sf.dsp4j.AudioSink.DEFAULT_SECONDS_IN_BUFFER;
import net.sf.dsp4j.TargetDataLineWrapper;

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
