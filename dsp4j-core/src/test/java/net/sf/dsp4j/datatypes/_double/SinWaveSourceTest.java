package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.VisualResultCheckTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static net.sf.dsp4j.DspConst.HALF_PI;
import static net.sf.dsp4j.DspConst.TWO_PI;
import org.junit.After;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class SinWaveSourceTest extends VisualResultCheckTest {

    SinWaveSource instance;
    SampleCount sc;

    public SinWaveSourceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        instance = new SinWaveSource(0.01);
        sc = new SampleCount();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testA() throws Exception {
        final double sampleRate = 22050;
//      createFile("testSetX", sampleRate, 1);
        instance.setSampleRate(sampleRate);
        final double f1 = 1200;
        final double f2 = 1800;
        final double T = 1.0 / 1200;

        final double phiExpected = HALF_PI * 3;
        final double phiPhaseShiftExpected = HALF_PI;
        final double deltaphi1200 = TWO_PI * 1200 / instance.getSampleRate();
        final double deltaphi1800 = TWO_PI * 1800 / instance.getSampleRate();

        sc.reset();
        instance.addDuration(2000, 20);
        instance.addDuration(f1, 10.0 * T);
        instance.addDuration(f2, 3.0 * T);
        instance.addDuration(f1, 2.0 * T);
        instance.addDuration(f2, 1.0 * T);
        instance.addDuration(f1, 1.0 * T);
        instance.addDuration(f2, 1.0 * T);


        do {
            instance.clock();
            sc.clock();
            if (isShowResult()) {
                sfs.setX((short) (instance.getY() * Short.MAX_VALUE));
            }
        } while (!instance.isLast());

//        assertEquals(6.1549570356044905, instance.getCurrentPhi(), deltaphi1200);

    }

    @Ignore
    @Test
    public void testSinWave() {
        instance.setSampleRate(22050);

        final double f1 = 1200;
        final double f2 = 1800;
        final double T = 1.0 / 1200;


//        instance.runTime(f1, 1, T);
//        instance.runPeriods(10, 1, 1);
//        instance.runTime(f1, 1, T);
//        instance.runTime(3600, 1, T);
//        instance.runTime(f1, 1, T);
//        instance.runTime(f2, 1, T);
//        instance.runTime(f2, 1, T);
//        instance.runTime(f2, 1, T);
//        final double deltaphi1800 = TWO_PI * 1800 / instance.getSampleRate();
        fail();

    }
}