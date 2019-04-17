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

import de.ibapl.dsp4j.VisualResultCheckTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static de.ibapl.dsp4j.DspConst.HALF_PI;
import static de.ibapl.dsp4j.DspConst.TWO_PI;
import org.junit.After;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class SinWaveSourceTest extends VisualResultCheckTest {

    SinWaveSource instance;
    SampleCount sc;

    public SinWaveSourceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        instance = new SinWaveSource(0.01);
        sc = new SampleCount();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testA() throws Exception {
        final double sampleRate = 22050;
//      createFile("testSetX", sampleRate, 1);
        instance.setSampleRate(sampleRate);
        final double f1 = 1200;
        final double f2 = 1800;
        final double T = 1.0 / 1200;

        final double phiExpected = HALF_PI * 3;
        final double phiPhaseShiftExpected = HALF_PI;
        final double deltaphi1200 = TWO_PI * 1200 / instance.getSampleRate();
        final double deltaphi1800 = TWO_PI * 1800 / instance.getSampleRate();

        sc.reset();
        instance.addDuration(2000, 20);
        instance.addDuration(f1, 10.0 * T);
        instance.addDuration(f2, 3.0 * T);
        instance.addDuration(f1, 2.0 * T);
        instance.addDuration(f2, 1.0 * T);
        instance.addDuration(f1, 1.0 * T);
        instance.addDuration(f2, 1.0 * T);


        do {
            instance.clock();
            sc.clock();
            if (isShowResult()) {
                sfs.setShort(0, (short) (instance.getY() * Short.MAX_VALUE));
                sfs.nextSample();
            }
        } while (!instance.isLast());

//        assertEquals(6.1549570356044905, instance.getCurrentPhi(), deltaphi1200);

    }

    @Ignore
    @Test
    public void testSinWave() {
        instance.setSampleRate(22050);

        final double f1 = 1200;
        final double f2 = 1800;
        final double T = 1.0 / 1200;


//        instance.runTime(f1, 1, T);
//        instance.runPeriods(10, 1, 1);
//        instance.runTime(f1, 1, T);
//        instance.runTime(3600, 1, T);
//        instance.runTime(f1, 1, T);
//        instance.runTime(f2, 1, T);
//        instance.runTime(f2, 1, T);
//        instance.runTime(f2, 1, T);
//        final double deltaphi1800 = TWO_PI * 1800 / instance.getSampleRate();
        fail();

    }
}