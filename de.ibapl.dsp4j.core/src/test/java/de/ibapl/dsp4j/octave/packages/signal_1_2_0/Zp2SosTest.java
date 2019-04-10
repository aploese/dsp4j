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
package de.ibapl.dsp4j.octave.packages.signal_1_2_0;

import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Bilinear;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.Butter;
import de.ibapl.dsp4j.octave.packages.signal_1_0_11.SfTrans;
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
public class Zp2SosTest {

    public Zp2SosTest() {
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
    public void testButterLP1StOrder() {
        Butter b = new Butter(1, 0.2, true, false);
        SfTrans sfTrans = new SfTrans(b.getZero(), b.getPole(), b.getGain(), b.getW(), b.isStop());
        Bilinear bilinear = new Bilinear(sfTrans.getSZero(), sfTrans.getSPole(), sfTrans.getSGain(), b.getT());
        Zp2Sos zp2Sos = new Zp2Sos(bilinear.getZZero(), bilinear.getZPole(), bilinear.getZGain());
        assertEquals(1, zp2Sos.getRowCount());
        assertEquals(0.24523727525278557, zp2Sos.getGain(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, 1, 0, 1, -0.5095254494944288, 0}, zp2Sos.getRow(0), Double.MIN_VALUE);
    }
    
    @Test
    public void testButterBP2StOrder() {
        Butter b = new Butter(2, 0.2, 0.4, true, false);
        SfTrans sfTrans = new SfTrans(b.getZero(), b.getPole(), b.getGain(), b.getW(), b.isStop());
        Bilinear bilinear = new Bilinear(sfTrans.getSZero(), sfTrans.getSPole(), sfTrans.getSGain(), b.getT());
        Zp2Sos zp2Sos = new Zp2Sos(bilinear.getZZero(), bilinear.getZPole(), bilinear.getZGain());
        assertEquals(2, zp2Sos.getRowCount());
        assertEquals(0.06745527388907192, zp2Sos.getGain(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, 2, 1, 1, -0.6344484417402887, 0.5918264073655373}, zp2Sos.getRow(0), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -2, 1, 1, -1.3080203348075954, 0.6975045265954563}, zp2Sos.getRow(1), Double.MIN_VALUE);
    }

    @Test
    public void testButterHP3StOrder() {
        Butter b = new Butter(3, 0.2, true, true);
        SfTrans sfTrans = new SfTrans(b.getZero(), b.getPole(), b.getGain(), b.getW(), b.isStop());
        Bilinear bilinear = new Bilinear(sfTrans.getSZero(), sfTrans.getSPole(), sfTrans.getSGain(), b.getT());
        Zp2Sos zp2Sos = new Zp2Sos(bilinear.getZZero(), bilinear.getZPole(), bilinear.getZGain());
        assertEquals(2, zp2Sos.getRowCount());
        assertEquals(0.5276243825019432, zp2Sos.getGain(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -2, 1, 1, -1.25051643084874, 0.5457233155094574}, zp2Sos.getRow(0), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -1, 0, 1, -0.5095254494944288, 0}, zp2Sos.getRow(1), Double.MIN_VALUE);
    }
    
    @Test
    public void testButterBS4StOrder() {
        Butter b = new Butter(4, 0.2, 0.4, true, true);
        SfTrans sfTrans = new SfTrans(b.getZero(), b.getPole(), b.getGain(), b.getW(), b.isStop());
        Bilinear bilinear = new Bilinear(sfTrans.getSZero(), sfTrans.getSPole(), sfTrans.getSGain(), b.getT());
        Zp2Sos zp2Sos = new Zp2Sos(bilinear.getZZero(), bilinear.getZPole(), bilinear.getZGain());
        assertEquals(4, zp2Sos.getRowCount());
        assertEquals(0.4328466449902918, zp2Sos.getGain(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -1.2360679774997898, 1, 1, -0.5823570199238857, 0.7556237313349994}, zp2Sos.getRow(0), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -1.2360679774997898, 1, 1, -0.7573574834457177, 0.5088146254217133}, zp2Sos.getRow(1), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -1.2360679774997898, 1, 1, -1.126780672834907, 0.5820201361472738}, zp2Sos.getRow(2), Double.MIN_VALUE);
        assertArrayEquals(new double[]{1, -1.2360679774997898, 1, 1, -1.4700803540185425, 0.8373728439780258}, zp2Sos.getRow(3), Double.MIN_VALUE);
    }
    
}