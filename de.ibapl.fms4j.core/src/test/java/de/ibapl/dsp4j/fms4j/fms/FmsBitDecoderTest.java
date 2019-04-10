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
package de.ibapl.dsp4j.fms4j.fms;

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
public class FmsBitDecoderTest {

    public FmsBitDecoderTest() {
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
     * Test of reset method, of class FmsBitDecoder.
     */
    @Test
    public void testX() {
        FmsBitDecoder instance = new FmsBitDecoder();
        assertEquals(FmsBitDecoder.State.NOISE_SPACE, instance.getState());
        for (int i = 0; i < 10; i++) {
            instance.setX(true);
            assertEquals(FmsBitDecoder.State.NOISE_MARK, instance.getState());
            assertEquals(i + 1, instance.getBitCount());
            assertEquals("AT I= " + i,  true, instance.isBit());
        }
        
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_1ST_000, instance.getState());
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_1ST_000, instance.getState());
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_2ND_11, instance.getState());

        instance.setX(true);
        assertEquals(FmsBitDecoder.State.COLLECT_2ND_11, instance.getState());
        instance.setX(true);
        assertEquals(FmsBitDecoder.State.COLLECT_3RD_0, instance.getState());

        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_4TH_1, instance.getState());
        instance.setX(true);
        assertEquals(FmsBitDecoder.State.COLLECT_5TH_0, instance.getState());
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.DECODE_BITS, instance.getState());

    }
}