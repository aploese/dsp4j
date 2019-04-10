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

import java.io.File;
import java.util.logging.Logger;
import de.ibapl.dsp4j.VisualResultCheckTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aploese
 */
public class AutoGainTest extends VisualResultCheckTest {

    final private static Logger LOG = Logger.getLogger(AutoGainTest.class.getCanonicalName());

    public AutoGainTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    /**
     * Test of setX method, of class CostasLoop.
     */
    @Test
    public void testSetX() throws Exception {
//        gfgf
        System.out.println("setX");
//        SinWaveSource sws = new SinWaveSource(22050, 0.01);
//        sws.setSampleRate(88200);
//        sws.setSampleRate(44100);
//        sws.setSampleRate(22050);
//        sws.setSampleRate(11025);
//        sws.setSampleRate(8000);
//        sws.setPhi0(Math.PI);
        File of = File.createTempFile("autogain", ".wav");
        of.deleteOnExit();

        AutoGain instance = new AutoGain();

//        addToFileSink(sws, instance, instance.getLp());


        double a = 0.01;
//        sws.runPeriods(1000, 0.1, 100);
//        sws.runPeriods(1000, 0.5, 150);
//        sws.runPeriods(1000, 1, 150);

    }

}