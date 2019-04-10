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
package de.ibapl.dsp4j.datatypes._short;

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
public class ShortFileTest {
    
    public ShortFileTest() {
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
        short[] data = new short[]{
            0,
            Short.MAX_VALUE / 4, 
            Short.MAX_VALUE / 2, 
            Short.MAX_VALUE,
            Short.MAX_VALUE / 2,
            Short.MAX_VALUE / 4,
            0,
            0,
            Short.MIN_VALUE / 4,
            Short.MIN_VALUE / 2,
            Short.MIN_VALUE,
            Short.MIN_VALUE / 2,
            Short.MIN_VALUE / 4,
            0};
        
        ShortFileSink sink = new ShortFileSink(File.createTempFile("test", "wav"), 8000, 5);
        for (int i = 0; i < data.length; i++) {
            sink.setX(data[i], (short)-data[i], Short.MIN_VALUE, (short)0, Short.MAX_VALUE);
        }
        sink.close();
        
        
        ShortFileSource source = new ShortFileSource(sink.getWavOut());
        assertEquals(sink.isBigEndian(), source.isBigEndian());
        assertEquals(sink.getEncoding(), source.getEncoding());
        assertEquals(sink.getChannels(), source.getChannels());
        assertEquals(sink.getSampleRate(), source.getSampleRate(), Double.MIN_VALUE);
        assertEquals(sink.getFrameSize(), source.getFrameSize());
        assertEquals(sink.getSampleSizeInBits(), source.getSampleSizeInBits());
        
        for (int i = 0; i < data.length; i++) {
            source.clock();
            assertEquals("Error at [0]:" + i, data[i], source.getY(0));
            assertEquals("Error at [1]:" + i, (short)-data[i], source.getY(1));
            assertEquals("Error at [2]:" + i, Short.MIN_VALUE, source.getY(2));
            assertEquals("Error at [3]:" + i, (short)0, source.getY(3));
            assertEquals("Error at [4]:" + i, Short.MAX_VALUE, source.getY(4));
        }
        source.clock();
    }

}