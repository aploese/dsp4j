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
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class RemezFilterTest {
    
    public RemezFilterTest() {
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
     * Test of getW method, of class RemezFilter.
     */
    @Test
    @Ignore
    public void testGetW() {
        System.out.println("getW");
        RemezFilter instance = new RemezFilter(120, new double[] {900, 1200, 1800, 2100}, new double[] {0, 1, 1,0});
        double[] expResult = null;
        double[] result = instance.getW();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSampleRate method, of class RemezFilter.
     */
    @Test
    @Ignore
    public void testSetSampleRate() {
        System.out.println("setSampleRate");
        double sampleRate = 0.0;
        RemezFilter instance = new RemezFilter(120, new double[] {900, 1200, 1800, 2100}, new double[] {0, 1, 1,0});
        instance.setSampleRate(sampleRate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFc method, of class RemezFilter.
     */
    @Test
    @Ignore
    public void testSetFc() {
        System.out.println("setFc");
        int order = 120;
        float fc = 0.0F;
        double with = 0.0;
        boolean high = false;
        RemezFilter instance = new RemezFilter(120, new double[] {0, 900, 1200, 1800, 2100}, new double[] {0, 0, 1, 1, 0, 0});
        instance.setSampleRate(22100);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
