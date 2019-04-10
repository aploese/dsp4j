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
public class ButterworthDoubleFilterTest {

    public ButterworthDoubleFilterTest() {
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

    @Test
    @Ignore
    public void test1stOrder() throws Exception {
        System.out.println("setX");
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(8000);
        //LP
        DirectDoubleIirFilter filter = gen.getLP_ButterFc(1, 500, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.16591068104035053, 0.16591068104035053}, filter.getB(), Double.MIN_VALUE);

//        filter.setFc(5, 500, false);
//        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, filter.getA(), Double.MIN_VALUE);
//        assertArrayEquals(new double[]{0.16591068104035053, 0.16591068104035053}, filter.getB(), Double.MIN_VALUE);

        //HP
        filter = gen.getHP_ButterFc(1, 500, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.8340893189596494, -0.8340893189596494}, filter.getB(), Double.MIN_VALUE);
        //BP
        filter = gen.getBP_ButterFc(1, 500, 3000, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.00000, -0.281304567672052, -0.19891236737965798}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.5994561836898289, 0.00000, -0.5994561836898289}, filter.getB(), Double.MIN_VALUE);
        //BS
        filter = gen.getBS_ButterFc(1, 500, 3000, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.00000, -0.281304567672052, -0.19891236737965798}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.40054381631017105, -0.281304567672052, 0.40054381631017105}, filter.getB(), Double.MIN_VALUE);
    }

}
