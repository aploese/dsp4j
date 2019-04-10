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
package de.ibapl.dsp4j.octave_3_2_4.m.polynomial;

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