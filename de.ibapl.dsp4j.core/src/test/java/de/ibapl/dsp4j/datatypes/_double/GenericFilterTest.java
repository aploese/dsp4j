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

import de.ibapl.dsp4j.datatypes._double.iirfilter.GenericDirectDoubleIirFilter;
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
public class GenericFilterTest {

    public GenericFilterTest() {
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
     * Test of setX method, of class AbstractDoubleFilter.
     */
    @Test
    public void testSetX() throws Exception {
        System.out.println("setX");
        GenericDirectDoubleIirFilter filter = new GenericDirectDoubleIirFilter(new double[]{1, 2, 3}, new double[]{4, 5, 6});
        filter.setX(1);
        assertEquals(4, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(1, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(1, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(10, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(-8, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(1, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(37, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(-62, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(28, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(145, filter.getY(), Double.MIN_VALUE);

        assertArrayEquals(new double[]{-363, -429}, filter.getSi(), 0.01);
    }

}