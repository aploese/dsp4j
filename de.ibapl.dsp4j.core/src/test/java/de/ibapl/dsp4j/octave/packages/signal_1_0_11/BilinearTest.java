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
public class BilinearTest {

    public BilinearTest() {
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
     * Test of bilinear method, of class BilinearTransform.
     */
    @Test
    public void testBilinear_4args() {
        System.out.println("bilinear");
        Complex[] Sz = new Complex[0];
        Complex[] Sp = new Complex[]{new Complex(-0.15838, 0)};
        double Sg = 0.15838;
        double T = 2.0;
        Bilinear instance = new Bilinear(Sz, Sp, Sg, T);
        assertEquals(1, instance.getZZero().length);
        ComplexUtil.assertComplexEquals(-1.0, -0.0, instance.getZZero()[0]);
        assertEquals(1, instance.getZPole().length);
        ComplexUtil.assertComplexEquals(0.7265491462214473, 0, instance.getZPole()[0]);
        assertEquals(0.1367254268892764, instance.getZGain(), Double.MIN_VALUE);
    }

    @Test
    public void testBilinear1stOrder() {
        Complex [] sPole;
        Complex [] sZero;
        double sGain;


        //LP
        sPole = new Complex[]{new Complex(-0.198912367379658, 0)};
        sZero = new Complex[0];
        sGain = 0.198912367379658;
        double T = 2.0;

        Bilinear instance = new Bilinear(sZero, sPole, sGain, T);
        assertEquals(1, instance.getZZero().length);
        ComplexUtil.assertComplexEquals(-1.0, -0.0, instance.getZZero()[0]);
        assertEquals(1, instance.getZPole().length);
        ComplexUtil.assertComplexEquals(0.6681786379192989, 0.0, instance.getZPole()[0]);
        assertEquals(0.16591068104035053, instance.getZGain(), Double.MIN_VALUE);

/*
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
 */
    }


}