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
package de.ibapl.dsp4j.octave.packages.signal_1_2_0;

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
public class NCauerTest {
    
    public NCauerTest() {
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
    public void testN_1() {
        NCauer instance = new NCauer(20, 20, 1);
        assertArrayEquals(new Complex[0] , instance.getZer());
        assertArrayEquals(new Complex[] {Complex.valueOf(-0.681559268318051)}, instance.getPol());
        assertEquals(0.681559268318051, instance.getT0(), Double.MIN_VALUE);
    }
    @Test
    public void testN_2() {
        NCauer instance = new NCauer(20, 20, 2);
        assertArrayEquals(new Complex[] {Complex.valueOf(0.0, 1.000171227205055), Complex.valueOf(0.0, -1.000171227205055)}, instance.getZer());
        assertArrayEquals(new Complex[] {Complex.valueOf(-9.641115831307598E-5, 0.9998585295307023), Complex.valueOf(-9.641115831307598E-5, -0.9998585295307023)}, instance.getPol());
        assertEquals(0.09993748187559487, instance.getT0(), Double.MIN_VALUE);
    }
}