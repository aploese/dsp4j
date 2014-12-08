/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave_3_2_4.m.polynomial;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
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
public class RootsTest {
    
    public RootsTest() {
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

    /**
     * Test of roots method, of class Roots.
     */
    @Test
    public void testRoots() {
        System.out.println("roots");
        RealVector v = new ArrayRealVector(new double[]{1, 2, 3}, false);
        Complex[] expResult = new Complex[]{new Complex(-1, Math.sqrt(2)), new Complex(-1, -Math.sqrt(2))};
        Complex[] result = Roots.roots(v);
        assertArrayEquals(expResult, result);

        v = new ArrayRealVector(new double[]{0, 1, 2, 3, 0}, false);
        expResult = new Complex[]{new Complex(-1, Math.sqrt(2)), new Complex(-1, -Math.sqrt(2)), Complex.ZERO};
        result = Roots.roots(v);
        assertArrayEquals(expResult, result);
        
        v = new ArrayRealVector(new double[]{0, 1, 2, 3}, false);
        expResult = new Complex[]{new Complex(-1, Math.sqrt(2)), new Complex(-1, -Math.sqrt(2))};
        result = Roots.roots(v); 
        assertArrayEquals(expResult, result);
    }
}