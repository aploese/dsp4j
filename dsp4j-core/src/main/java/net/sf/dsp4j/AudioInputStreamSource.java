package net.sf.dsp4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author aploese
 */
public abstract class AudioInputStreamSource {

    private final boolean bigEndian;
    private final AudioInputStream ais;
    private final byte[] buffer;
    private int bufferPos;
    private int bytesReaded;
    private boolean endOfAudioData;

    public AudioInputStreamSource(AudioInputStream ais, int framesInBuffer) throws IOException {
        this.ais = ais;
        //TODO Currently everytime false!!!!
        bigEndian = ais.getFormat().isBigEndian();
        buffer = new byte[ais.getFormat().getFrameSize() * framesInBuffer];
        readBuffer();
    }

    private final void readBuffer() throws IOException {
        bytesReaded = ais.read(buffer);
        bufferPos = 0;
        endOfAudioData = bytesReaded == -1;
    }

    protected final boolean isBufferReaded() {
        return bufferPos == bytesReaded;
    }

    public AudioInputStreamSource(InputStream is, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        this(AudioSystem.getAudioInputStream(is), framesInBuffer);
    }

    public AudioInputStreamSource(File f, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        this(AudioSystem.getAudioInputStream(f), framesInBuffer);
    }

    public AudioInputStreamSource(String name, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        this(new File(name), framesInBuffer);
    }

    public AudioInputStreamSource(URL resource, int framesInBuffer) throws IOException, UnsupportedAudioFileException {
        this(resource.getFile(), framesInBuffer);
    }

    public final int getChannels() {
        return ais.getFormat().getChannels();
    }

    public final int getFrameSize() {
        return ais.getFormat().getFrameSize();
    }

    public final int getSampleSizeInBits() {
        return ais.getFormat().getSampleSizeInBits();
    }

    public final boolean isBigEndian() {
        return bigEndian;
    }

    public final Encoding getEncoding() {
        return ais.getFormat().getEncoding();
    }

    public final float getSampleRate() {
        return ais.getFormat().getSampleRate();
    }

    protected final byte readByte() throws IOException {
        byte result = buffer[bufferPos++];
        if (isBufferReaded()) {
            readBuffer();
        }
        return result;
    }

    protected final short readShort() throws IOException {
        short result;
        if (bigEndian) {
            result = (short) (((buffer[bufferPos++] & 0xFF) << 8) | (buffer[bufferPos++] & 0xFF));
        } else {
            result = (short) (((buffer[bufferPos++] & 0xFF) | ((buffer[bufferPos++] & 0xFF) << 8)));
        }
        if (isBufferReaded()) {
            readBuffer();
        }
        return result;
    }

    protected final void readShort(short[] samples) throws IOException {
        if (bigEndian) {
            for (int i = 0; i < samples.length; i++) {
                samples[i] = (short) (((buffer[bufferPos++] & 0xFF) << 8) | (buffer[bufferPos++] & 0xFF));
            }
        } else {
            for (int i = 0; i < samples.length; i++) {
                samples[i] = (short) (((buffer[bufferPos++] & 0xFF) | ((buffer[bufferPos++] & 0xFF) << 8)));
            }
        }
        if (isBufferReaded()) {
            readBuffer();
        }
    }

    protected final int readInt() throws IOException {
        int result;
        if (bigEndian) {
            result = ((buffer[bufferPos++] & 0xFF) << 24) | ((buffer[bufferPos++] & 0xFF) << 16) | ((buffer[bufferPos++] & 0xFF) << 8) | (buffer[bufferPos++] & 0xFF);
        } else {
            result = ((buffer[bufferPos++] & 0xFF)) | ((buffer[bufferPos++] & 0xFF) << 8) | ((buffer[bufferPos++] & 0xFF) << 16) | ((buffer[bufferPos++] & 0xFF) << 24);
        }
        if (isBufferReaded()) {
            readBuffer();
        }
        return result;
    }

    public final boolean isEndOfAudioData() {
        return endOfAudioData;
    }

    /**
     *
     * @return false, if the end of stream is rechead (The last frames where
     * read in the buffer).
     * @throws IOException
     */
    public abstract boolean clock() throws IOException;
}