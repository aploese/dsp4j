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
public class EllipOrdTest {
    
    public EllipOrdTest() {
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
        EllipOrd instance = new EllipOrd(0.1, 0.2, 0.1, 20);
        assertEquals(3, instance.getN());
        assertEquals(0.1, instance.getWp(0), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test
    public void testHP() {
        EllipOrd instance = new EllipOrd(0.2, 0.1, 0.1, 20);
        assertEquals(3, instance.getN());
        assertEquals(0.2, instance.getWp(0), Double.MIN_NORMAL);
        assertTrue(instance.isStop());
    }

    @Test
    public void testBP() {
        EllipOrd instance = new EllipOrd(0.2, 0.3, 0.1, 0.4, 0.1, 20);
        assertEquals(3, instance.getN());
        assertEquals(0.2, instance.getWp(0), Double.MIN_NORMAL);
        assertEquals(0.3, instance.getWp(1), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test
    public void testBS() {
        EllipOrd instance = new EllipOrd(0.1, 0.4, 0.2, 0.3, 0.1, 20);
        assertEquals(3, instance.getN());
        assertEquals(0.1, instance.getWp(0), Double.MIN_NORMAL);
        assertEquals(0.4, instance.getWp(1), Double.MIN_NORMAL);
        assertTrue(instance.isStop());
    }

}