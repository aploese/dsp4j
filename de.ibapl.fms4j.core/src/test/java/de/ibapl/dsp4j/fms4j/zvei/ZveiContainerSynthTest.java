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

import java.io.File;
import java.util.logging.Logger;
import de.ibapl.dsp4j.fms4j.VisualResultCheckTest;
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.ibapl.dsp4j.fms4j.zvei.ZveiFreqTable.*;

/**
 *
 * @author aploese
 */
public class ZveiContainerSynthTest extends VisualResultCheckTest {

	final private static Logger LOG = Logger.getLogger(ZveiContainerSynthTest.class.getCanonicalName());

	public ZveiContainerSynthTest() {
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
		super.tearDown();
	}

	@Test
	public void testZveiAudio() throws Exception {
		ZveiTestListener l = new ZveiTestListener();
		l.add(Integer.MAX_VALUE, new ZveiFreqTable[] { DIGIT_0, DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_4 });
//        doTest(false, 44100, data);
//        doTest(false, 22050, data);
		doTest(false, 11025, l);
	}

	public void doTest(boolean showResult, double samplerate, final ZveiTestListener l) throws Exception {
		ZveiModulator m = new ZveiModulator(0.001);
		m.setSampleRate(samplerate);

		ZveiContainer zveiContainer = new ZveiContainer(l);

		zveiContainer.setSampleRate(m.getSampleRate());

		File f = createFile("testFms", showResult);
		ShortFileSink sfs = new ShortFileSink(f, 3, m.getSampleRate(), 1024);
		l.currentDataIndex = 0;
		for (int i = 0; i < l.data.size(); i++) {
			m.reset();
			m.addData(l.data.get(i));
			do {
				m.clock();
				zveiContainer.setX(m.getY() * Short.MAX_VALUE);

				sfs.setShort(0, (short) (m.getY() * Short.MAX_VALUE));
				sfs.setShort(1, (short) (zveiContainer.getSampleCount() * Short.MAX_VALUE / 7 - Short.MAX_VALUE));
				sfs.setShort(2,
						(short) (zveiContainer.getSignalFilter().getY() * Short.MAX_VALUE / 7 - Short.MAX_VALUE));
				sfs.nextSample();
			} while (!m.isLast());
			assertEquals(1, l.currentDataIndex);
		}
		sfs.close();
	}
}
