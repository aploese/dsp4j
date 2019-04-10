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
package de.ibapl.dsp4j.octave_3_2_4.m.polynomial;

import org.apache.commons.math3.complex.Complex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn.real;

/**
 *
 * @author aploese
 */
public class PolyTest {

    public PolyTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testBilinear1stOrder() {
        Complex [] zPole;
        Complex [] zZero;
        double zGain;


        //LP
        zPole = new Complex[]{new Complex(-0.198912367379658, 0)};
        zZero = new Complex[0];
        zGain = 0.198912367379658;

        zZero = new Complex[]{new Complex(-1.0, -0.0)};
        zPole = new Complex[]{new Complex(0.6681786379192989, 0.0)};
        zGain = 0.16591068104035053;

        double[] a = real(Poly.poly(zPole));

        Complex[] bCplx = Poly.poly(zZero);
        for (int i= 0; i< bCplx.length; i++) {
            bCplx[i] = bCplx[i].multiply(zGain);
        }

        double[] b = real(bCplx);

        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, a, Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.16591068104035053, 0.16591068104035053}, b, Double.MIN_VALUE);

    }

    @Test
    public void octaveTests() {
        
        assertArrayEquals(new double[]{1, -6, 11, -6}, Poly.poly(new double[] {1, 2, 3}), Double.MIN_VALUE);
        assertArrayEquals(new Complex[]{new Complex(1), new Complex(-6), new Complex(11), new Complex(-6)}, Poly.poly(new Complex[] {new Complex(1), new Complex(2), new Complex(3)}));
//        assertArrayEquals(new double[]{1, -5, 2}, Poly.poly(new double[][] {{1, 2},{3 , 4}}));
//%!assert(all (all (abs (poly ([1, 2; 3, 4]) - [1, -5, -2]) < sqrt (eps))));

//%!error poly ([1, 2, 3; 4, 5, 6]);

//%!assert(poly ([]),1);

    }
}