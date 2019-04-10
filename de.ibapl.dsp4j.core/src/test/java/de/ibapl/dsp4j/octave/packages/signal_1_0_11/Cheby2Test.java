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