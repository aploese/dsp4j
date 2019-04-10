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
public class ButterTest {

    public ButterTest() {
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
     * Test of butter method, of class ButterworthFilter.
     */
    @Test
    public void testButter_1stOrder() {
        System.out.println("butter 1st order");
        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-1)};
        
        //LP
        Butter instance = new Butter(1, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //HP
        instance = new Butter(1, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BP
        instance = new Butter(1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BS
        instance = new Butter(1, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);
    }

    /**
     * Test of butter method, of class ButterworthFilter.
     */
    @Test
    public void testButter_2ndOrder() {
        System.out.println("butter 2nd order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-0.7071067811865475, 0.7071067811865476), new Complex(-0.7071067811865477, -0.7071067811865475)};

        //LP
        Butter instance = new Butter(2, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //HP
        instance = new Butter(2, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BP
        instance = new Butter(2, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BS
        instance = new Butter(2, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);
    }
        /**
     * Test of butter method, of class ButterworthFilter.
     */
    @Test
    public void testButter_3rdOrder() {
        System.out.println("butter 2nd order");

        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-0.4999999999999998, 0.8660254037844387), new Complex(-1, 0), new Complex(-0.5000000000000004, -0.8660254037844384)};

        //LP
        Butter instance = new Butter(3, 2.0 * 500 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //HP
        instance = new Butter(3, 2.0 * 500 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BP
        instance = new Butter(3, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, false);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);

        //BS
        instance = new Butter(3, 2.0 * 500 / 8000, 2.0 * 3000 / 8000, true, true);
        assertArrayEquals(zero, instance.getZero());
        assertArrayEquals(pole, instance.getPole());
        assertEquals(1, instance.getGain(), 0.0);
    }

}
