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
package de.ibapl.dsp4j.octave.packages.specfun_1_1_0;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class EllipkeTest {

    public EllipkeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test1() {
        double[] m = new double[]{0.0};
        double[] k = new double[]{1.5707963267948966};
        double[] e = new double[]{1.5707963267948966};
        Ellipke ellipke = new Ellipke(m);

        assertArrayEquals(k, ellipke.getK(), Double.MIN_VALUE);
        assertArrayEquals(e, ellipke.getE(), Double.MIN_VALUE);
    }

    @Test
    public void test2() {
        double[] m = new double[]{0.1, 0.2};
        double[] k = new double[]{1.6124413487202194, 1.6596235986105279};
        double[] e = new double[]{1.5307576368977631, 1.4890350580958527};
        Ellipke ellipke = new Ellipke(m);

        assertArrayEquals(k, ellipke.getK(), Double.MIN_VALUE);
        assertArrayEquals(e, ellipke.getE(), Double.MIN_VALUE);
    }

    @Test
    public void test2N() {
        double[] m = new double[]{0.1, -0.2};
        double[] k = new double[]{1.6124413487202194, 1.5000268912867474};
        double[] e = new double[]{1.5307576368977631, 1.6466124685552685};
        Ellipke ellipke = new Ellipke(m);

        assertArrayEquals(k, ellipke.getK(), Double.MIN_VALUE);
        assertArrayEquals(e, ellipke.getE(), Double.MIN_VALUE);
    }

    @Test
    public void test3() {
        double[] m = new double[]{0.0, 0.1, 0.2, 1.0};
        double[] k = new double[]{1.5707963267948966, 1.6124413487202194, 1.6596235986105279, Double.POSITIVE_INFINITY};
        double[] e = new double[]{1.5707963267948966, 1.5307576368977631, 1.4890350580958527, 1.0};
        Ellipke ellipke = new Ellipke(m);

        assertArrayEquals(k, ellipke.getK(), Double.MIN_VALUE);
        assertArrayEquals(e, ellipke.getE(), Double.MIN_VALUE);
    }

    @Test
    public void test4() {
        double[] m = new double[]{0.0, 0.01, 0.1, 0.5, 0.9, 0.99, 1.0};
        double[] k = new double[]{1.5707963267948966, 1.574745561517356, 1.6124413487202194, 1.8540746773013717, 2.578092113348173, 3.6956373629898738, Double.POSITIVE_INFINITY};
        double[] e = new double[]{1.5707963267948966, 1.566861942021668, 1.5307576368977631, 1.350643881047675, 1.1047747327040733, 1.0159935450252238, 1.0};
        Ellipke ellipke = new Ellipke(m);

        assertArrayEquals(k, ellipke.getK(), Double.MIN_VALUE);
        assertArrayEquals(e, ellipke.getE(), Double.MIN_VALUE);
    }
}