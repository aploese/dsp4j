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
package de.ibapl.dsp4j.fms4j.zvei;

import de.ibapl.dsp4j.fms4j.fms.*;
import java.io.File;
import java.util.logging.Logger;
import de.ibapl.dsp4j.datatypes._short.MonoShortFileSource;
import de.ibapl.dsp4j.fms4j.VisualResultCheckTest;
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static de.ibapl.dsp4j.fms4j.zvei.ZveiFreqTable.*;

/**
 *
 * @author aploese
 */
@Ignore
public class ZveiContainerTest extends VisualResultCheckTest {

    final private static Logger LOG = Logger.getLogger(ZveiContainerTest.class.getCanonicalName());
    private ShortFileSink sfs;

    public ZveiContainerTest() {
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
    @Override
    public void tearDown() throws Exception {
        if (sfs != null) {
            sfs.close();
            sfs = null; 
        }
        super.tearDown();
    }

    @Test
    public void testZveiAudio() throws Exception {
        ZveiTestListener l = new ZveiTestListener();
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_6, DIGIT_1});
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_6, DIGIT_1});
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_6, DIGIT_0});
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_6, DIGIT_0});
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_7, WIEDERHOLUNG});
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_7, WIEDERHOLUNG});
        doTest("/teningen/20110416-hauptübung/01-zvei-alarmierung.wav", false, l);
    }

    //TODO FMS 
    @Test
    public void testZveiAudio_fms() throws Exception {
        ZveiTestListener l = new ZveiTestListener();
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_7, DIGIT_5});
        l.add(Integer.MAX_VALUE, new ZveiFreqTable[]{DIGIT_1, DIGIT_6, DIGIT_1, DIGIT_7, DIGIT_9});
        doTest("/teningen/20110416-hauptübung/03-zvei-alarmierung-mit-fms.wav", false, l);
    }

    public void doTest(String resFileName, boolean showResult, final ZveiTestListener l) throws Exception {
        MonoShortFileSource msfs = new MonoShortFileSource(FmsContainerTest.class.getResourceAsStream(resFileName));

        ZveiContainer zveiContainer = new ZveiContainer(l);

        zveiContainer.setSampleRate(msfs.getSampleRate());

        File f = createFile("testFms", showResult);
        sfs = new ShortFileSink(f, msfs.getSampleRate(), 4);
        while (msfs.clock()) {
            zveiContainer.setX(msfs.getY());

            sfs.setX(msfs.getY(),
                    (short) zveiContainer.getInHpFilter().getY(),
                    (short) (zveiContainer.getSampleCount() * Short.MAX_VALUE / 30 - Short.MAX_VALUE),
                    (short) (zveiContainer.getSignalFilter().getY() * Short.MAX_VALUE / 30 - Short.MAX_VALUE));
        }
        assertEquals(l.data.size(), l.currentDataIndex);
    }
}
