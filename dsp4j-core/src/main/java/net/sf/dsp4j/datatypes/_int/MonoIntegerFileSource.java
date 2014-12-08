/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._int;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.sf.dsp4j.AudioInputStreamSource;

/**
 *
 * @author aploese
 * 32 Bit PCM
 */
public class MonoIntegerFileSource extends AudioInputStreamSource {
    
    private int y;
    
    public MonoIntegerFileSource(File f) throws IOException, UnsupportedAudioFileException {
        this(f, 1);
    }

    public MonoIntegerFileSource(File f, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        super(f, framesInBuffer);
    }
    
    @Override
    public boolean clock() throws IOException {
        y = readInt();
        return !isEndOfAudioData();
    }
    
    public int getY() {
        return y;
    }
}
