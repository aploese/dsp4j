/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.sf.dsp4j.AudioInputStreamSource;

/**
 *
 * @author aploese
 * 16 Bit PCM
 */
public final class MonoShortFileSource extends AudioInputStreamSource {
    
    private short y;
    
    public MonoShortFileSource(File f) throws IOException, UnsupportedAudioFileException {
        this(f, 1);
    }

    public MonoShortFileSource(InputStream is) throws IOException, UnsupportedAudioFileException {
        this(is, 1);
    }

    public MonoShortFileSource(File f, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        super(f, framesInBuffer);
    }
    
    public MonoShortFileSource(InputStream is, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        super(is, framesInBuffer);
    }
    
    @Override
    public final boolean clock() throws IOException {
        y = readShort();
        return !isEndOfAudioData();
    }
    
    public final short getY() {
        return y;
    }
}
