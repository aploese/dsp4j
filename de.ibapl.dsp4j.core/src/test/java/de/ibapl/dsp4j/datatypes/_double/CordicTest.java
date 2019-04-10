/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.datatypes._double;

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