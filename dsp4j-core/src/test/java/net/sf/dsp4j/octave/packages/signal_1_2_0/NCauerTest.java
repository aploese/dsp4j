/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.signal_1_2_0;

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
public class NCauerTest {
    
    public NCauerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testN_1() {
        NCauer instance = new NCauer(20, 20, 1);
        assertArrayEquals(new Complex[0] , instance.getZer());
        assertArrayEquals(new Complex[] {Complex.valueOf(-0.681559268318051)}, instance.getPol());
        assertEquals(0.681559268318051, instance.getT0(), Double.MIN_VALUE);
    }
    @Test
    public void testN_2() {
        NCauer instance = new NCauer(20, 20, 2);
        assertArrayEquals(new Complex[] {Complex.valueOf(0.0, 1.000171227205055), Complex.valueOf(0.0, -1.000171227205055)}, instance.getZer());
        assertArrayEquals(new Complex[] {Complex.valueOf(-9.641115831307598E-5, 0.9998585295307023), Complex.valueOf(-9.641115831307598E-5, -0.9998585295307023)}, instance.getPol());
        assertEquals(0.09993748187559487, instance.getT0(), Double.MIN_VALUE);
    }
}