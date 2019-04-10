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
package de.ibapl.dsp4j.octave.packages.signal_1_0_11;

import de.ibapl.dsp4j.ComplexUtil;
import org.apache.commons.math3.complex.Complex;
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
public class SfTransTest {

    public SfTransTest() {
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

    /**
     * Test of sftrans method, of class SfTrans.
     */
    @Test
    public void testSftrans1stOrder() {
        Complex[] zero = new Complex[0];
        Complex[] pole = new Complex[] {new Complex(-1, 0)};
        double gain = 1;
        double[] W = new double[] {0.198912367379658};

        //LP
        SfTrans sfTrans = new SfTrans(zero, pole, gain, W, false);
        assertEquals(0, sfTrans.getSZero().length);
        assertEquals(1, sfTrans.getSPole().length);
        ComplexUtil.assertComplexEquals(-0.198912367379658, 0, sfTrans.getSPole()[0]);
        assertEquals(0.198912367379658, sfTrans.getSGain(), Double.MIN_VALUE);

        //HP
        sfTrans = new SfTrans(zero, pole, gain, W, true);
        assertEquals(1, sfTrans.getSZero().length);
        ComplexUtil.assertComplexEquals(0.0, 0.0, sfTrans.getSZero()[0]);
        assertEquals(1, sfTrans.getSPole().length);
        ComplexUtil.assertComplexEquals(-0.198912367379658, -0.0, sfTrans.getSPole()[0]);
        assertEquals(1.0, sfTrans.getSGain(), Double.MIN_VALUE);

        W = new double[] {0.198912367379658, 2.414213562373095};

        //BP
        sfTrans = new SfTrans(zero, pole, gain, W, false);
        assertEquals(1, sfTrans.getSZero().length);
        ComplexUtil.assertComplexEquals(0.0, 0.0, sfTrans.getSZero()[0]);
        assertEquals(2, sfTrans.getSPole().length);
        ComplexUtil.assertComplexEquals(-0.24354822446263902, -1.7388063843636265E-16, sfTrans.getSPole()[0]);
        ComplexUtil.assertComplexEquals(-1.9717529705307977, 1.7388063843636265E-16, sfTrans.getSPole()[1]);
        assertEquals(2.2153011949934367, sfTrans.getSGain(), Double.MIN_VALUE);

        //BS
        sfTrans = new SfTrans(zero, pole, gain, W, true);
        assertEquals(2, sfTrans.getSZero().length);
        ComplexUtil.assertComplexEquals(0.0, 0.6929768647304974, sfTrans.getSZero()[0]);
        ComplexUtil.assertComplexEquals(-0.0, -0.6929768647304974, sfTrans.getSZero()[1]);
        assertEquals(2, sfTrans.getSPole().length);
        ComplexUtil.assertComplexEquals(-0.24354822446263902, 1.7388063843636265E-16, sfTrans.getSPole()[0]);
        ComplexUtil.assertComplexEquals(-1.9717529705307977, -1.7388063843636265E-16, sfTrans.getSPole()[1]);
        assertEquals(1, sfTrans.getSGain(), Double.MIN_VALUE);
    }

}