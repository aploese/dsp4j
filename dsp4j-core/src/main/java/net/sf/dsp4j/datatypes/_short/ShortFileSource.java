/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.sf.dsp4j.AudioInputStreamSource;

/**
 *
 * @author aploese 16 Bit PCM
 */
public class ShortFileSource extends AudioInputStreamSource {

    private short[] y;

    public ShortFileSource(File f) throws IOException, UnsupportedAudioFileException {
        this(f, 1);
    }

    public ShortFileSource(File f, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        super(f, framesInBuffer);
        y = new short[getChannels()];
    }

    @Override
    public boolean clock() throws IOException {
        readShort(y);
        return !isEndOfAudioData();
    }

    public short getY(int i) {
        return y[i];
    }

    public short[] getY() {
        return y;
    }
}
