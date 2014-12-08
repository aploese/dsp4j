package net.sf.dsp4j;

import java.io.File;
import java.io.IOException;
import net.sf.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.Ignore;

/**
 *
 * @author aploese
 *
 * set setShowTests(true) in before... to show all tests
 */
@Ignore
public class VisualResultCheckTest {
    protected ShortFileSink sfs;
    private File f;
    
    @After
    public void tearDown() throws Exception {
        if (sfs != null) {
            sfs.close();
        }
        if (isShowResult()) {
            Runtime.getRuntime().exec(new String[]{"audacity", f.getAbsolutePath()}).waitFor();
        }
        sfs = null;
        if (f != null) {
            f.delete();
        }
        f = null;
    }

    /**
     * @return the showTests
     */
    protected boolean isShowResult() {
        return sfs != null;
    }

    /**
     * @param showTestName the showTests to set
     */
    protected void createFile(String name, double samplerate, int channels) throws IOException{
        this.f = File.createTempFile(String.format("%s_%s", getClass().getSimpleName(), name), ".wav");
        sfs = new ShortFileSink(f, samplerate, channels);
        f.deleteOnExit();
    }

    /**
     * @return the fs
     */
    protected File getFile() {
        return f;
    }

}
