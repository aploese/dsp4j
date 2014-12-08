/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.signal_1_0_11;

import org.apache.commons.math3.complex.Complex;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author aploese
 */
public class BesselAllPoleTest {
    
    public BesselAllPoleTest() {
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
    public void testSomeMethod() {
        Bessel bessel = new Bessel(3, 0.5, false, false);
        assertEquals(1, bessel.getGain(), 0.00001);
        assertEquals(3, bessel.getPole().length);
        assertEquals(0, bessel.getZero().length);
        assertEquals(Complex.valueOf(-0.7456403858480786, 0.7113666249728333), bessel.getPole()[0]);
        assertEquals(Complex.valueOf(-0.7456403858480786, -0.7113666249728333), bessel.getPole()[1]);
        assertEquals(Complex.valueOf(-0.9416000265332063, 0.00000), bessel.getPole()[2]);
    }
}