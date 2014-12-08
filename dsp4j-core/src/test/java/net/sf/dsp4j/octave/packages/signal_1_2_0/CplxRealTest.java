/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.signal_1_2_0;

import net.sf.dsp4j.octave.packages.signal_1_2_0.CplxReal;
import org.apache.commons.math3.complex.Complex;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.apache.commons.math3.complex.Complex.valueOf;
import org.junit.experimental.categories.Categories;

/**
 *
 * @author aploese
 */
public class CplxRealTest {
    
    public CplxRealTest() {
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
    public void doTest() {
        CplxReal instance = new CplxReal();
        instance.cplxReal(Double.MIN_VALUE, valueOf(10, 2), valueOf(10, -2), valueOf(1, -2), valueOf(2), valueOf(1, 2), valueOf(1, 3), valueOf(1, -3));
        assertArrayEquals(new Complex[]{valueOf(1, 2), valueOf(1, 3), valueOf(10, 2)}, instance.getConjComplxPair());
        assertArrayEquals(new Complex[]{valueOf(2)}, instance.getRealValues());
    }
    
    @Test
    public void doTestRealOrder() {
        CplxReal instance = new CplxReal();
        instance.cplxReal(Double.MIN_VALUE, valueOf(1), valueOf(1), valueOf(-1), valueOf(-1));
        assertArrayEquals(new Complex[0], instance.getConjComplxPair());
        assertArrayEquals(new Complex[]{valueOf(-1), valueOf(-1), valueOf(1), valueOf(1)}, instance.getRealValues());
    }
    
    @Test(expected = RuntimeException.class)
    public void doTestEx() {
        CplxReal instance = new CplxReal();
        instance.cplxReal(Double.MIN_VALUE, valueOf(1, -2), valueOf(2, 5), valueOf(1, 2), valueOf(1, 3), valueOf(1, -3));
        assertArrayEquals(new Complex[]{valueOf(1, 2), valueOf(1, 3)}, instance.getConjComplxPair());
        assertArrayEquals(new Complex[]{valueOf(2)}, instance.getRealValues());
    }

}