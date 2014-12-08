package net.sf.dsp4j.octave.packages.signal_1_0_11;

import net.sf.dsp4j.ComplexUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author aploese
 */
public class PoleZeroGainIirFilterFactoryTest {

    public PoleZeroGainIirFilterFactoryTest() {
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
    public void tearDown() {
    }


    @Test
    public void testW() {
        //LP
        PoleZeroGainIIRFilterGenerator instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000}, true, false);
        assertEquals(1, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);

        //HP
        instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000}, true, true);
        assertEquals(1, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);

        //BP
        instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000, 2.0 * 3000 / 8000}, true, false);
        assertEquals(2, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);
        assertEquals(2.414213562373095, instance.getW()[1], Double.MIN_VALUE);

        //BS
        instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000, 2.0 * 3000 / 8000}, true, true);
        assertEquals(2, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);
        assertEquals(2.414213562373095, instance.getW()[1], Double.MIN_VALUE);
    }

}
