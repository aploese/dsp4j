/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.DspConst;
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
public class CordicTest {

    public CordicTest() {
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
     * Test of setReIm method, of class Cordic.
     */
    @Test
    public void testSetReIm() {
        doCordicReIm( 8, 100, 0.01);
        doCordicReIm(14, 100, 0.001);
        doCordicReIm(15, 100, 0.0001);
        doCordicReIm(18, 100, 0.00001);
        doCordicReIm(21, 100, 0.000001);
        doCordicReIm(26, 100, 0.0000001);
    }
    
    /**
     * Test of setReIm method, of class Cordic.
     */
    @Test
    public void testSetMagPhi() {
        doCordicMagPhi( 9, 100, 0.01);
        doCordicMagPhi(14, 100, 0.001);
        doCordicMagPhi(15, 100, 0.0001);
        doCordicMagPhi(18, 100, 0.00001);
        doCordicMagPhi(21, 100, 0.000001);
        doCordicMagPhi(26, 100, 0.0000001);
    }
    
    public void doCordicMagPhi(int iterations, int steps, double precistion) {
        Cordic instance = new Cordic(iterations);
        for (double phi = -Math.PI; phi  <= Math.PI; phi += Math.PI / steps) {
            instance.setMagPhi(1, phi);
            assertEquals(String.format("Error at re phi: %f", phi), Math.cos(phi), instance.getReY(), precistion);
            assertEquals(String.format("Error at im phi: %f", phi), Math.sin(phi), instance.getImY(), precistion);
        }
    }

    public void doCordicReIm(int iterations, int steps, double precistion) {
        Cordic instance = new Cordic(iterations);
        for (double phi = -Math.PI; phi  <= Math.PI; phi += Math.PI / steps) {
            instance.setReIm(Math.cos(phi), Math.sin(phi));
            assertEquals(String.format("Error at phi: %f", phi), phi, instance.getPhiY(), precistion);
            assertEquals(String.format("Error at phi: %f", phi), 1, instance.getMagnitudeY(), precistion);
        }
    }

}