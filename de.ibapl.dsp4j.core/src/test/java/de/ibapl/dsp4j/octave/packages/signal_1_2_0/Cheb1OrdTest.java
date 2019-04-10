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
public class Cheb1OrdTest {
    
    public Cheb1OrdTest() {
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
    public void testLP() {
        Cheb1Ord instance = new Cheb1Ord(0.1, 0.2, 0.1, 20);
        assertEquals(4, instance.getN());
        assertEquals(0.1, instance.getWc(0), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test
    public void testHP() {
        Cheb1Ord instance = new Cheb1Ord(0.2, 0.1, 0.1, 20);
        assertEquals(4, instance.getN());
        assertEquals(0.2, instance.getWc(0), Double.MIN_NORMAL);
        assertTrue(instance.isStop());
    }

    @Test
    public void testBP() {
        Cheb1Ord instance = new Cheb1Ord(0.2, 0.3, 0.1, 0.4, 0.1, 20);
        assertEquals(3, instance.getN());
        assertEquals(0.2, instance.getWc(0), Double.MIN_NORMAL);
        assertEquals(0.3, instance.getWc(1), Double.MIN_NORMAL);
        assertFalse(instance.isStop());
    }

    @Test(expected = RuntimeException.class)
    public void testBS() {
        Cheb1Ord instance = new Cheb1Ord(0.1, 0.4, 0.2, 0.3, 0.1, 20);
    }

}