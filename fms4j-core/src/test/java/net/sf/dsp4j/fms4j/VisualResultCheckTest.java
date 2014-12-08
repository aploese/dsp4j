package net.sf.dsp4j.fms4j;

import java.io.File;
import java.io.IOException;
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
    private File f;
    private boolean showResult;
    
    @After
    public void tearDown() throws Exception {
        if (isShowResult()) {
            Runtime.getRuntime().exec(new String[]{"audacity", f.getAbsolutePath()}).waitFor();
        }
    }

    /**
     * @return the showTests
     */
    protected boolean isShowResult() {
        return showResult;
    }

    /**
     * @param showTestName the showTests to set
     */
    protected File createFile(String name, boolean showResult) throws IOException{
        this.showResult = showResult;
        this.f = File.createTempFile(String.format("%s_%s", getClass().getSimpleName(), name), ".wav");
        f.deleteOnExit();
        return f;
    }

    /**
     * @return the fs
     */
    protected File getFile() {
        return f;
    }

}
