/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class Cheby2Test {

    public Cheby2Test() {
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
    public void testCheby2_1stOrder() {
        System.out.println("cheby2 1st order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-6.552203216802745, -2.659227989712135E-15)};
       
        //LP
        Cheby2 instance = new Cheby2(1, 0.1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.552203216802745, instance.getGain(), Double.MIN_VALUE);

        //HP
        instance = new Cheby2(1, 0.1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.552203216802745, instance.getGain(), Double.MIN_VALUE);

        //BP
        instance = new Cheby2(1, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.552203216802745, instance.getGain(), Double.MIN_VALUE);

        //BS
        instance = new Cheby2(1, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.552203216802745, instance.getGain(), Double.MIN_VALUE);
    }

    @Test
    public void testCheby2_2ndOrder() {
        System.out.println("cheby2 2nd order");

        Complex[] zero = new Complex[] {new Complex(0.0, 1.414213562373095), new Complex(-0.0, -1.4142135623730951)};
        Complex[] pole = new Complex[] {new Complex(-0.10637609553432686, -1.4020664447923816), new Complex(-0.10637609553432692, 1.402066444792382)};
        
        //LP
        Cheby2 instance = new Cheby2(2, 0.1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(0.9885530946569387, instance.getGain(), Double.MIN_VALUE);
 
        //HP
        instance = new Cheby2(2, 0.1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(0.9885530946569387, instance.getGain(), Double.MIN_VALUE);
 
        //BP
        instance = new Cheby2(2, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(0.9885530946569387, instance.getGain(), Double.MIN_VALUE);
 
        //BS
        instance = new Cheby2(2, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(0.9885530946569387, instance.getGain(), Double.MIN_VALUE);
    }

        @Test
    public void testCheby2_3rdOrder() {
        System.out.println("cheby2 3rd order");

        Complex[] zero = new Complex[] {new Complex(0.0, 1.1547005383792515), new Complex(-0.0, -1.1547005383792515)};
        Complex[] pole = new Complex[] {new Complex(-0.03368436145309, -1.1522346104126553), new Complex(-19.723978373314417, -2.385213966626884E-14), new Complex(-0.03368436145309, 1.1522346104126553)};
        
        //LP
        Cheby2 instance = new Cheby2(3, 0.1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(19.65660965040823, instance.getGain(), Double.MIN_VALUE);

        //HP
        instance = new Cheby2(3, 0.1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(19.65660965040823, instance.getGain(), Double.MIN_VALUE);

        //BP
        instance = new Cheby2(3, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(19.65660965040823, instance.getGain(), Double.MIN_VALUE);

        //BS
        instance = new Cheby2(3, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(19.65660965040823, instance.getGain(), Double.MIN_VALUE);
    }

}