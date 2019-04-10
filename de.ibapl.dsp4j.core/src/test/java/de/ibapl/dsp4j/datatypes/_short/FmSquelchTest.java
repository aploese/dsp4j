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

import de.ibapl.dsp4j.VisualResultCheckTest;
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
public class FmSquelchTest extends VisualResultCheckTest {

    public FmSquelchTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of setX method, of class NoiseBlock.
     */
    @Test
    @Ignore
    public void testSetX() throws Exception {
        System.out.println("setX");

        FmSquelch instance = new FmSquelch(900, 10, 3600);

        MonoShortFileSource msfs = new MonoShortFileSource(FmSquelchTest.class.getResourceAsStream("/noise2.wav")); //FMS-lineIn_11025.0_2013-05-06_17:05:23.735.wav"));
        createFile("test", msfs.getSampleRate(), 3);
        instance.setSampleRate(msfs.getSampleRate());
        while (msfs.clock()) {
            boolean s = instance.setX(msfs.getY());
            if (isShowResult()) {
                sfs.setX(msfs.getY(),
                        (short) instance.getLp().getY(),
                        s ? msfs.getY() : 0);
            }
        }
    }
}