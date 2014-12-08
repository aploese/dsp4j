package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.VisualResultCheckTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class CostasLoopTest extends VisualResultCheckTest {

    final private static Logger LOG = LoggerFactory.getLogger(CostasLoopTest.class);

    public CostasLoopTest() {
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
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of setSampleRate method, of class CostasLoop.
     */
    @Test
    public void testSetSampleRate() {
        System.out.println("setSampleRate");
        CostasLoop instance = new CostasLoop(new GenericNCO2ndOrder(1000, 1500, 2000), new GenericAtan(), 1200);
        instance.setSampleRate(44100);
        assertEquals(44100, instance.getSampleRate(), Double.MIN_VALUE);
    }

    private static short scale(double d) {
        return (short) (d * Short.MAX_VALUE);
    }

}
