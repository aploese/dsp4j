/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.signal_1_2_0;

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
public class Cheb2OrdTest {
    
    public Cheb2OrdTest() {
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
    public void testLP() {
        Cheb2Ord instance = new Cheb2Ord(0.1, 0.2, 0.1, 20);
        assertEquals(4, instance.getN());
        assertEquals(0.2, instance.getWc(0), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test
    public void testHP() {
        Cheb2Ord instance = new Cheb2Ord(0.2, 0.1, 0.1, 20);
        assertEquals(4, instance.getN());
        assertEquals(0.1, instance.getWc(0), Double.MIN_NORMAL);
        assertTrue(instance.isStop());
    }

    @Test
    public void testBP() {
        Cheb2Ord instance = new Cheb2Ord(0.2, 0.3, 0.1, 0.4, 0.1, 20);
        assertEquals(2, instance.getN());
        assertEquals(0.1, instance.getWc(0), Double.MIN_NORMAL);
        assertEquals(0.4, instance.getWc(1), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test(expected = RuntimeException.class)
    public void testBS() {
        Cheb2Ord instance = new Cheb2Ord(0.1, 0.4, 0.2, 0.3, 0.1, 20);
    }

}