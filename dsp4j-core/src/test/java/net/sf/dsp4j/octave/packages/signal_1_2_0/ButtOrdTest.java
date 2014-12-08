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
public class ButtOrdTest {
    
    public ButtOrdTest() {
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
        ButtOrd instance = new ButtOrd(0.1, 0.2, 0.1, 20);
        assertEquals(6, instance.getN());
        assertEquals(0.1358301375542518, instance.getWc(0), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test
    public void testHP() {
        ButtOrd instance = new ButtOrd(0.2, 0.1, 0.1, 20);
        assertEquals(6, instance.getN());
        assertEquals(0.14846264649277907, instance.getWc(0), Double.MIN_NORMAL);
        assertTrue(instance.isStop());
    }

    @Test
    public void testBP() {
        ButtOrd instance = new ButtOrd(0.2, 0.3, 0.1, 0.4, 0.1, 20);
        assertEquals(6, instance.getN());
        assertEquals(0.14846264649277907, instance.getWc(0), Double.MIN_NORMAL);
        assertEquals(0.38751502489530454, instance.getWc(1), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test
    public void testBS() {
        ButtOrd instance = new ButtOrd(0.1, 0.4, 0.2, 0.3, 0.1, 20);
        assertEquals(6, instance.getN());
        assertEquals(0.1358301375542518, instance.getWc(0), Double.MIN_NORMAL);
        assertEquals(0.3108208237991308, instance.getWc(1), Double.MIN_NORMAL);
        assertTrue(instance.isStop());
    }

}