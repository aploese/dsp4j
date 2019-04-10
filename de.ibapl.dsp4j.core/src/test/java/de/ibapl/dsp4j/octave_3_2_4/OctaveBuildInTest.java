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
package de.ibapl.dsp4j.octave_3_2_4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn.*;

/**
 *
 * @author aploese
 */
public class OctaveBuildInTest {

    public OctaveBuildInTest() {
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
    public void testFrexp_0_25() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(0.25);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(-1, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testFrexp_0_5() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(0.5);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(0, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testFrexp_1() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(1);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(1, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testFrexp_2() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(2);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(2, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testEps_0_5() throws Exception {
        assertEquals(Math.pow(2, -53), eps(0.5), Double.MIN_VALUE);
    }

    @Test
    public void testEps_1() throws Exception {
        assertEquals(Math.pow(2, -52), eps(1), Double.MIN_VALUE);
    }

    @Test
    public void testEps_2() throws Exception {
        assertEquals(Math.pow(2, -51), eps(2), Double.MIN_VALUE);
    }

    @Test
    public void testEps_MAX_VALUE() throws Exception {
        assertEquals(Math.pow(2, 971), eps(Double.MAX_VALUE), Double.MIN_VALUE);
    }

    @Test
    public void testEps_0() throws Exception {
        assertEquals(Math.pow(2, -1074), eps(0), Double.MIN_VALUE);
    }

    @Test
    public void testEps_MIN_VALUE_DIV_2() throws Exception {
        assertEquals(Math.pow(2, -1074), eps(Double.MIN_VALUE / 2), Double.MIN_VALUE);
    }

    @Test
    public void testEps_MIN_VALUE_DIV_16() throws Exception {
        assertEquals(Math.pow(2, -1074), eps(Double.MIN_VALUE / 16), Double.MIN_VALUE);
    }

    @Test
    public void testEps_POSITIVE_INFINITY() throws Exception {
        assertEquals(Double.NaN, eps(Double.POSITIVE_INFINITY), Double.MIN_VALUE);
    }

    @Test
    public void testEps_NaN() throws Exception {
        assertEquals(Double.NaN, eps(Double.NaN), Double.MIN_VALUE);
    }
}