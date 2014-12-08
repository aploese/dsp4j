/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import net.sf.dsp4j.datatypes._int.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.sf.dsp4j.AudioInputStreamSource;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public class StereoShortFileSource extends AudioInputStreamSource {
    
    private final short[] y = new short[2];
    
    public StereoShortFileSource(File f) throws IOException, UnsupportedAudioFileException {
        this(f, 1);
    }

    public StereoShortFileSource(File f, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        super(f, framesInBuffer);
    }
    
    @Override
    public boolean clock() throws IOException {
        readShort(y);
        return !isEndOfAudioData();
    }
    
    public short getLeftY() {
        return y[0];
    }
    
    public short getRightY() {
        return y[1];
    }
}
