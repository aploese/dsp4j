package net.sf.dsp4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author aploese
 */
public abstract class FileSink extends AudioSink {

    private File wavOut;
    private File tempFile;
    private FileOutputStream os;
    
    
    protected FileSink(File wavOut, AudioFormat audioFormat, int framesInBuffer) {
        super(audioFormat, framesInBuffer);
        this.wavOut = wavOut;
    }

    @Override
    public void flush() {
        try {
            os.write(buffer, 0, bufferSamplePos);
            bytesWriten += bufferSamplePos;
            bufferSamplePos = 0;
        } catch (IOException ex) {
            throw new RuntimeException("Filebuffer", ex);
        }
    }

    @Override
    public void close() {
        if (os == null) {
            return;
        }
        try {
            if (isEmpty()) {
                os.close();
                os = null;
                tempFile.delete();
                return;
            }
            flush();
            os.flush();
            os.close();
            os = null;
            FileInputStream is = new FileInputStream(tempFile);
            AudioInputStream ais = new AudioInputStream(is, getAudioFormat(), tempFile.length() / getAudioFormat().getFrameSize());
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new FileOutputStream(getWavOut()));
            tempFile.delete();
        } catch (IOException ex) {
            throw new RuntimeException("Filebuffer", ex);
        }
    }

    @Override
    public void open() {
        try {
            if (tempFile == null) {
                tempFile = File.createTempFile("fileSink", "wav.raw");
                tempFile.deleteOnExit();
            }
            if (os == null) {
                os = new FileOutputStream(tempFile);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Filebuffer", ex);
        }
    }

    /**
     * @return the wavOut
     */
    public File getWavOut() {
        return wavOut;
    }

    /**
     * @param wavOut the wavOut to set
     */
    public void setWavOut(File wavOut) {
        close();
        this.wavOut = wavOut;
        open();
    }

}
