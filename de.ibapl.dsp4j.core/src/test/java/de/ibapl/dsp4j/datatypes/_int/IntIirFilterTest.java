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

import de.ibapl.dsp4j.VisualResultCheckTest;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author aploese
 */
public class IntIirFilterTest extends VisualResultCheckTest {

	public IntIirFilterTest() {
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
	@Ignore
	public void testFilter() throws Exception {
		System.out.println("setX");

		IntIirFilterGenerator genInt = new IntIirFilterGenerator(8000);
		DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

		DirectIntIirFilter filterInt = genInt.getLP_ButterFc(1, 1000, DirectIntIirFilter.class);
		DirectDoubleIirFilter filterDouble = genDouble.getLP_ButterFc(1, 1000, DirectDoubleIirFilter.class);

		filterDouble.setX(1024);
		filterInt.setX(1024);
		assertEquals("y", filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
		filterDouble.setX(1024);
		filterInt.setX(1024);
		assertEquals(filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);

	}

	@Test
	@Ignore
	public void testButterLP1stOrder() throws Exception {
		System.out.println("setX");

		createFile("test", 8000, 3);
		IntIirFilterGenerator genInt = new IntIirFilterGenerator(8000);
		DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

		DirectIntIirFilter filterInt = genInt.getLP_ButterFc(1, 100, DirectIntIirFilter.class);
		DirectDoubleIirFilter filterDouble = genDouble.getLP_ButterFc(1, 100, DirectDoubleIirFilter.class);

		for (int i = 0; i < 8000; i++) {
			filterDouble.setX(Short.MAX_VALUE);
			filterInt.setX(Short.MAX_VALUE);
//            filterInt.setX(Q.Q_15.mul - 1);
//            assertEquals("@Index: " + i, filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
			if (isShowResult()) {
				sfs.setShort(0, (short) (1024));
				sfs.setShort(1, (short) (filterDouble.getY()));
				sfs.setShort(2, (short) (filterInt.getY()));
				sfs.nextSample();
			}
		}
		for (int i = 0; i < 8000; i++) {
			filterDouble.setX(0);
			filterInt.setX(0);
//            assertEquals("@Index: " + i, filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
			if (isShowResult()) {
				sfs.setShort(0, (short) 0);
				sfs.setShort(1, (short) (filterDouble.getY()));
				sfs.setShort(2, (short) (filterInt.getY()));
				sfs.nextSample();
			}
		}
		for (int i = 0; i < 8000; i++) {
			filterDouble.setX(Short.MIN_VALUE);
			filterInt.setX(Short.MIN_VALUE);
//            assertEquals("@Index: " + i, filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
			if (isShowResult()) {
				sfs.setShort(0, Short.MIN_VALUE);
				sfs.setShort(1, (short) (filterDouble.getY()));
				sfs.setShort(2, (short) (filterInt.getY()));
				sfs.nextSample();
			}
		}
	}
}
