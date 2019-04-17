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
package de.ibapl.dsp4j.datatypes._int;

import java.io.File;
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
public class MonoIntegerFileTest {

    public MonoIntegerFileTest() {
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
    public void tearDown() throws Exception {
    }

    /**
     * Test of setX method, of class MonoIntegerFileSink.
     */
    @Test
    public void testSetX() throws Exception {
        int[] data = new int[]{
            0,
            Integer.MAX_VALUE / 4,
            Integer.MAX_VALUE / 2,
            Integer.MAX_VALUE,
            Integer.MAX_VALUE / 2,
            Integer.MAX_VALUE / 4,
            0,
            0,
            Integer.MIN_VALUE / 4,
            Integer.MIN_VALUE / 2,
            Integer.MIN_VALUE,
            Integer.MIN_VALUE / 2,
            Integer.MIN_VALUE / 4,
            0};

        System.out.println("setX");
        IntegerFileSink sink = new IntegerFileSink(File.createTempFile("test", "wav"), 1, 8000.0, 3);
        for (int i = 0; i < data.length; i++) {
            sink.setInt(0, data[i]);
            sink.nextSample();
        }
        sink.close();


        IntegerSampledSource source = new IntegerSampledSource(sink.getWavOut());
        assertEquals(sink.isBigEndian(), source.isBigEndian());
        assertEquals(sink.getEncoding(), source.getEncoding());
        assertEquals(sink.getChannels(), source.getChannels());
        assertEquals(sink.getSampleRate(), source.getSampleRate(), Double.MIN_VALUE);
        assertEquals(sink.getFrameSize(), source.getFrameSize());
        assertEquals(sink.getSampleSizeInBits(), source.getSampleSizeInBits());

        for (int i = 0; i < data.length; i++) {
            assertTrue("At Idx: " + i, source.nextSample());
            assertEquals("Error at:" + i, data[i], source.getInt(0));
        }
        assertFalse(source.nextSample());

    }
}