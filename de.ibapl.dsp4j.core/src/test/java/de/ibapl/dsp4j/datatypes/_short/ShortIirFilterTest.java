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

import de.ibapl.dsp4j.datatypes._short.iirfilter.DirectShortIirFilter;
import de.ibapl.dsp4j.datatypes._short.iirfilter.ShortIirFilterGenerator;
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
public class ShortIirFilterTest extends VisualResultCheckTest {

    public ShortIirFilterTest() {
    }
    private int deltaError;
    private int factor;
    private double outScale;
    private DirectShortIirFilter filterShort;
    private DirectDoubleIirFilter filterDouble;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    deltaError = 1;
    factor = 1 << 15;
    outScale = 1.0;
    filterShort  = null;
    filterDouble  = null;
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    private void setValue(int i, short value) {
        final short sample = (short)((value * factor) >> 15);
        filterDouble.setX(sample);
        filterShort.setX(sample);
        assertEquals("@Index: " + i, filterDouble.getY(), filterShort.getY(), deltaError);
        if (isShowResult()) {
            sfs.setX((short) (sample * outScale),
                    (short) ((filterDouble.getY() - filterShort.getY()) * 10),
                    (short) (filterDouble.getY() * outScale),
                    (short) (filterShort.getY() * outScale));
        }

    }

    @Test
    @Ignore
    public void testFilter() throws Exception {
        System.out.println("setX");

        ShortIirFilterGenerator genShort = new ShortIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        filterShort = genShort.getLP_ButterFc(1, 1, DirectShortIirFilter.class);
        filterDouble = genDouble.getLP_ButterFc(1, 1, DirectDoubleIirFilter.class);

        filterDouble.setX(Short.MIN_VALUE);
        filterShort.setX(Short.MIN_VALUE);
        assertEquals("y", filterDouble.getY(), filterShort.getY(), 1.0);
        filterDouble.setX(1024);
        filterShort.setX((short) 1024);
        assertEquals(filterDouble.getY(), filterShort.getY(), 1.0);


    }

    @Test
    @Ignore
    public void testButterLP1stOrder() throws Exception {
        System.out.println("setX");
        deltaError = 100000;
        outScale = 16;

    //    createFile("test", 8000, 4);
        ShortIirFilterGenerator genShort = new ShortIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        filterShort = genShort.getLP_ButterFc(1, 1, DirectShortIirFilter.class);
        filterDouble = genDouble.getLP_ButterFc(1, 1, DirectDoubleIirFilter.class);

        for (int i = 0; i < 8000; i++) {
            setValue(i, (short)1024);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short)0);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) - 1024);
        }
    }
    
    @Test
    @Ignore
    public void testButterHP1stOrder() throws Exception {
        System.out.println("setX");
        deltaError = 100000;
        outScale = 16;

        createFile("test", 8000, 4);
        ShortIirFilterGenerator genShort = new ShortIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        filterShort = genShort.getHP_ButterFc(2, 100, DirectShortIirFilter.class);
        filterDouble = genDouble.getHP_ButterFc(2, 100, DirectDoubleIirFilter.class);

        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) - 1024);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short)1024);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short)0);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) - 1024);
        }
    }
}
