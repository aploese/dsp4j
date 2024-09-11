/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) 2019-2024, Arne Plöse and individual contributors as indicated
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
import de.ibapl.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import de.ibapl.dsp4j.datatypes._short.iirfilter.DirectShortIirFilter;
import de.ibapl.dsp4j.datatypes._short.iirfilter.ShortIirFilterGenerator;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setUp() {
        deltaError = 1;
        factor = 1 << 15;
        outScale = 1.0;
        filterShort = null;
        filterDouble = null;
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    private void setValue(int i, short value) throws IOException {
        final short sample = (short) ((value * factor) >> 15);
        filterDouble.setX(sample);
        filterShort.setX(sample);
        assertEquals(filterDouble.getY(), filterShort.getY(), deltaError, "@Index: " + i);
        if (isShowResult()) {
            sfs.setShort(0, (short) (sample * outScale));
            sfs.setShort(1, (short) ((filterDouble.getY() - filterShort.getY()) * 10));
            sfs.setShort(2, (short) (filterDouble.getY() * outScale));
            sfs.setShort(3, (short) (filterShort.getY() * outScale));
            sfs.nextSample();
        }

    }

    @Disabled
    @Test
    public void testFilter() throws Exception {
        System.out.println("setX");

        ShortIirFilterGenerator genShort = new ShortIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        filterShort = genShort.getLP_ButterFc(1, 1, DirectShortIirFilter.class);
        filterDouble = genDouble.getLP_ButterFc(1, 1, DirectDoubleIirFilter.class);

        filterDouble.setX(Short.MIN_VALUE);
        filterShort.setX(Short.MIN_VALUE);
        assertEquals(filterDouble.getY(), filterShort.getY(), 1.0, "y");
        filterDouble.setX(1024);
        filterShort.setX((short) 1024);
        assertEquals(filterDouble.getY(), filterShort.getY(), 1.0);

    }

    @Disabled
    @Test
    public void testButterLP1stOrder() throws Exception {
        System.out.println("setX");
        deltaError = 100000;
        outScale = 16;

        // createFile("test", 8000, 4);
        ShortIirFilterGenerator genShort = new ShortIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        filterShort = genShort.getLP_ButterFc(1, 1, DirectShortIirFilter.class);
        filterDouble = genDouble.getLP_ButterFc(1, 1, DirectDoubleIirFilter.class);

        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) 1024);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) 0);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) -1024);
        }
    }

    @Disabled
    @Test
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
            setValue(i, (short) -1024);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) 1024);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) 0);
        }
        for (int i = 0; i < 8000; i++) {
            setValue(i, (short) -1024);
        }
    }
}
