package net.sf.dsp4j.datatypes._double;

import java.io.File;
import net.sf.dsp4j.VisualResultCheckTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aploese
 */
public class AutoGainTest extends VisualResultCheckTest {

    final private static Logger LOG = LoggerFactory.getLogger(AutoGainTest.class);

    public AutoGainTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    /**
     * Test of setX method, of class CostasLoop.
     */
    @Test
    public void testSetX() throws Exception {
//        gfgf
        System.out.println("setX");
//        SinWaveSource sws = new SinWaveSource(22050, 0.01);
//        sws.setSampleRate(88200);
//        sws.setSampleRate(44100);
//        sws.setSampleRate(22050);
//        sws.setSampleRate(11025);
//        sws.setSampleRate(8000);
//        sws.setPhi0(Math.PI);
        File of = File.createTempFile("autogain", ".wav");
        of.deleteOnExit();

        AutoGain instance = new AutoGain();

//        addToFileSink(sws, instance, instance.getLp());


        double a = 0.01;
//        sws.runPeriods(1000, 0.1, 100);
//        sws.runPeriods(1000, 0.5, 150);
//        sws.runPeriods(1000, 1, 150);

    }

}