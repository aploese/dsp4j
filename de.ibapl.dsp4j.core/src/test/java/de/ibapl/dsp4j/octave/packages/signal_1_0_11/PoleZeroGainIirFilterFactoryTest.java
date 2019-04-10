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
public class PoleZeroGainIirFilterFactoryTest {

    public PoleZeroGainIirFilterFactoryTest() {
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
    public void testW() {
        //LP
        PoleZeroGainIIRFilterGenerator instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000}, true, false);
        assertEquals(1, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);

        //HP
        instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000}, true, true);
        assertEquals(1, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);

        //BP
        instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000, 2.0 * 3000 / 8000}, true, false);
        assertEquals(2, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);
        assertEquals(2.414213562373095, instance.getW()[1], Double.MIN_VALUE);

        //BS
        instance = new PoleZeroGainIIRFilterGenerator(new double[]{2.0 * 500 / 8000, 2.0 * 3000 / 8000}, true, true);
        assertEquals(2, instance.getW().length);
        assertEquals(0.198912367379658, instance.getW()[0], Double.MIN_VALUE);
        assertEquals(2.414213562373095, instance.getW()[1], Double.MIN_VALUE);
    }

}
