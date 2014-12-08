package net.sf.dsp4j.fms4j.zvei;

import net.sf.dsp4j.fms4j.fms.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.dsp4j.AudioSink;
import net.sf.dsp4j.fms4j.VisualResultCheckTest;
import net.sf.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static net.sf.dsp4j.fms4j.zvei.ZveiFreqTable.*;

/**
 *
 * @author aploese
 */
public class ZveiContainerSynthTest extends VisualResultCheckTest {

    final private static Logger LOG = LoggerFactory.getLogger(ZveiContainerSynthTest.class);

    public ZveiContainerSynthTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testZveiAudio() throws Exception {
        ZveiTestListener l = new ZveiTestListener();
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_0, DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_4});
//        doTest(false, 44100, data);
//        doTest(false, 22050, data);
        doTest(false, 11025, l);
    }

    public void doTest(boolean showResult, double samplerate, final ZveiTestListener l) throws Exception {
        ZveiModulator m = new ZveiModulator(0.001);
        m.setSampleRate(samplerate);

        ZveiContainer zveiContainer = new ZveiContainer(l);

        zveiContainer.setSampleRate(m.getSampleRate());

        File f = createFile("testFms", showResult);
        ShortFileSink sfs = new ShortFileSink(f, m.getSampleRate(), 3);
        l.currentDataIndex = 0;
        for (int i = 0; i < l.data.size(); i++) {
            m.reset();
            m.addData(l.data.get(i));
            do {
                m.clock();
                zveiContainer.setX(m.getY() * Short.MAX_VALUE);

                sfs.setX((short) (m.getY() * Short.MAX_VALUE),
                        (short) (zveiContainer.getSampleCount() * Short.MAX_VALUE / 7 - Short.MAX_VALUE),
                        (short) (zveiContainer.getSignalFilter().getY() * Short.MAX_VALUE / 7 - Short.MAX_VALUE));
            } while (!m.isLast());
            assertEquals(1, l.currentDataIndex);
        }
        sfs.close();
    }
}
