package net.sf.dsp4j.octave.packages.signal_1_0_11;

import net.sf.dsp4j.ComplexUtil;
import org.apache.commons.math3.complex.Complex;
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
public class ButterTest {

    public ButterTest() {
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


    /**
     * Test of butter method, of class ButterworthFilter.
     */
    @Test
    public void testButter_1stOrder() {
        System.out.println("butter 1st order");
        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-1)};
        
        //LP
        Butter instance = new Butter(1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //HP
        instance = new Butter(1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BP
        instance = new Butter(1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BS
        instance = new Butter(1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);
    }

    /**
     * Test of butter method, of class ButterworthFilter.
     */
    @Test
    public void testButter_2ndOrder() {
        System.out.println("butter 2nd order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-0.7071067811865475, 0.7071067811865476), new Complex(-0.7071067811865477, -0.7071067811865475)};

        //LP
        Butter instance = new Butter(2, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //HP
        instance = new Butter(2, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BP
        instance = new Butter(2, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BS
        instance = new Butter(2, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);
    }
        /**
     * Test of butter method, of class ButterworthFilter.
     */
    @Test
    public void testButter_3rdOrder() {
        System.out.println("butter 2nd order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-0.4999999999999998, 0.8660254037844387), new Complex(-1, 0), new Complex(-0.5000000000000004, -0.8660254037844384)};

        //LP
        Butter instance = new Butter(3, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //HP
        instance = new Butter(3, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BP
        instance = new Butter(3, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BS
        instance = new Butter(3, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);
    }

}
