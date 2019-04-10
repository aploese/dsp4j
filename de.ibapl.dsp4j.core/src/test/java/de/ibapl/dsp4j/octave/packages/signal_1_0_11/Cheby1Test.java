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
package de.ibapl.dsp4j.octave.packages.signal_1_0_11;

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
public class Cheby1Test {

    public Cheby1Test() {
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
    public void testCheby1_1stOrder() {
        System.out.println("cheby1 1st order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-6.5522032168027735, 0)};

        //LP
        Cheby1 instance = new Cheby1(1, 0.1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.5522032168027735, instance.getGain(), Double.MIN_VALUE);
   
        //HP
        instance = new Cheby1(1, 0.1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.5522032168027735, instance.getGain(), Double.MIN_VALUE);
   
        //BP
        instance = new Cheby1(1, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.5522032168027735, instance.getGain(), Double.MIN_VALUE);

        //BS
        instance = new Cheby1(1, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(6.5522032168027735, instance.getGain(), Double.MIN_VALUE);
//TODO  terst in PoleZeroGain*
/* 
        assertEquals(2, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], 0.0);
        assertEquals(2.414213562373095, instance.getW()[1], 0.0);
*/
    }

    @Test
    public void testCheby1_2ndOrder() {
        System.out.println("cheby1 2nd order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-1.1861781226119985, -1.3809484199503343), new Complex(-1.1861781226119985, 1.3809484199503343)};
        
        //LP
        Cheby1 instance = new Cheby1(2, 0.1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(3.2761016084013863, instance.getGain(), Double.MIN_VALUE);

        //HP
        instance = new Cheby1(2, 0.1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(3.2761016084013863, instance.getGain(), Double.MIN_VALUE);

        //BP
        instance = new Cheby1(2, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(3.2761016084013863, instance.getGain(), Double.MIN_VALUE);

        //BS
        instance = new Cheby1(2, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(3.2761016084013863, instance.getGain(), Double.MIN_VALUE);
    }
    
    @Test
    public void testCheby1_3rdOrder() {
        System.out.println("cheby1 3rd order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-0.484702854515027, -1.2061552849965238), new Complex(-0.9694057090300537, 0.0), new Complex(-0.484702854515027, 1.2061552849965238)};
        
        //LP
        Cheby1 instance = new Cheby1(3, 0.1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1.638050804200693, instance.getGain(), Double.MIN_VALUE);

        //HP
        instance = new Cheby1(3, 0.1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1.638050804200693, instance.getGain(), Double.MIN_VALUE);

        //BP
        instance = new Cheby1(3, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1.638050804200693, instance.getGain(), Double.MIN_VALUE);

        //BS
        instance = new Cheby1(3, 0.1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1.638050804200693, instance.getGain(), Double.MIN_VALUE);
    }
}