/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class RemezFilterTest {
    
    public RemezFilterTest() {
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
     * Test of getW method, of class RemezFilter.
     */
    @Test
    @Ignore
    public void testGetW() {
        System.out.println("getW");
        RemezFilter instance = new RemezFilter(120, new double[] {900, 1200, 1800, 2100}, new double[] {0, 1, 1,0});
        double[] expResult = null;
        double[] result = instance.getW();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSampleRate method, of class RemezFilter.
     */
    @Test
    @Ignore
    public void testSetSampleRate() {
        System.out.println("setSampleRate");
        double sampleRate = 0.0;
        RemezFilter instance = new RemezFilter(120, new double[] {900, 1200, 1800, 2100}, new double[] {0, 1, 1,0});
        instance.setSampleRate(sampleRate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFc method, of class RemezFilter.
     */
    @Test
    @Ignore
    public void testSetFc() {
        System.out.println("setFc");
        int order = 120;
        float fc = 0.0F;
        double with = 0.0;
        boolean high = false;
        RemezFilter instance = new RemezFilter(120, new double[] {0, 900, 1200, 1800, 2100}, new double[] {0, 0, 1, 1, 0, 0});
        instance.setSampleRate(22100);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
