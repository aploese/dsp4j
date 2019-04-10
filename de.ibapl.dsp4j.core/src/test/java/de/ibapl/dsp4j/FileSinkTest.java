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
package de.ibapl.dsp4j;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author aploese
 */
public class FileSinkTest {

    public FileSinkTest() {
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
     * Test of flush method, of class FileSink.
     */
    @Test
    public void testData() throws Exception {
//        System.out.println("flush");
//        File outWav = File.createTempFile(FileSinkTest.class.getName(), "raw");
//        FileSink instance = new FileSink(outWav, 16, 2, true, true);
//        instance.setSampleRate(8000);
//        assertEquals(0, instance.getBufferDataLength());
//        instance.getDoubleChannel(0).setX(-2);
//        instance.getDoubleChannel(1).setX(2);
//        assertEquals(4, instance.getBufferDataLength());
//        instance.flush();
//        assertEquals(0, instance.getBufferDataLength());
//        instance.getDoubleChannel(0).setX(-1);
//        instance.getDoubleChannel(1).setX(1);
//        instance.getDoubleChannel(0).setX(-0.5);
//        instance.getDoubleChannel(1).setX(0);
//        instance.getDoubleChannel(0).setX(-0);
//        instance.getDoubleChannel(1).setX(1);
//        instance.getDoubleChannel(0).setX(0.5);
//        instance.getDoubleChannel(1).setX(-1);
//        instance.getDoubleChannel(0).setX(1);
//        instance.getDoubleChannel(1).setX(1);
//        instance.close();
//        Runtime.getRuntime().exec(new String[] {"audacity", outWav.getAbsolutePath()}).waitFor();
//TODO       fail("The test case is a prototype.");
    }

    /**
     * Test of flush method, of class FileSink.
     */
    @Test
    public void test4Channels() throws Exception {
//        System.out.println("flush");
//        File outWav = File.createTempFile(FileSinkTest.class.getName(), "raw");
//        FileSink instance = new FileSink(outWav, 16, 4, true, true);
//        instance.setSampleRate(8000);
//        instance.getDoubleChannel(0).setX(-2);
//        instance.getDoubleChannel(1).setX(2);
//        instance.getDoubleChannel(2).setX(-1);
//        instance.getDoubleChannel(3).setX(1);
//        instance.getDoubleChannel(0).setX(-0.5);
//        instance.getDoubleChannel(1).setX(0);
//        instance.getDoubleChannel(2).setX(-0);
//        instance.getDoubleChannel(3).setX(1);
//        instance.getDoubleChannel(0).setX(0.5);
//        instance.getDoubleChannel(1).setX(-1);
//        instance.getDoubleChannel(2).setX(1);
//        instance.getDoubleChannel(3).setX(1);
//        instance.close();
//        Runtime.getRuntime().exec(new String[] {"audacity", outWav.getAbsolutePath()}).waitFor();
//TODO        fail("The test case is a prototype.");
    }

    /**
     * Test of flush method, of class FileSink.
     */
    @Ignore
    @Test
    public void testFlush() {
        System.out.println("flush");
        FileSink instance = null;
        instance.flush();
        // TODO review the generated test code and remove the default call to fail.
//TODO        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class FileSink.
     */
    @Ignore
    @Test
    public void testClose() {
        System.out.println("close");
        FileSink instance = null;
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
//TODO        fail("The test case is a prototype.");
    }

}