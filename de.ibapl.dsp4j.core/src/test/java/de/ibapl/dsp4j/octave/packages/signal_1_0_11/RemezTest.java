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

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class RemezTest {

    public RemezTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRemez() {
        Remez instance = new Remez(15, new double[]{0, 0.3, 0.4, 1}, new double[]{1, 1, 0, 0});
        double[] b = new double[]{
            0.0415131831103279,
            0.0581639884202646,
            -0.0281579212691008,
            -0.0535575358002337,
            -0.0617245915143180,
            0.0507753178978075,
            0.2079018331396460,
            0.3327160895375440,
            0.3327160895375440,
            0.2079018331396460,
            0.0507753178978075,
            -0.0617245915143180,
            -0.0535575358002337,
            -0.0281579212691008,
            0.0581639884202646,
            0.0415131831103279};

        assertArrayEquals(b, instance.getB(), 1e-14);

    }

    @Test
    public void testRemez4LP() {
        Remez instance = new Remez(4, new double[]{0, 1}, new double[]{1, 0});
        double[] b = new double[]{
            0.0049118450251747605,
            0.2209892636562989,
            0.49017630994965045,
            0.2209892636562989,
            0.0049118450251747605};

        assertArrayEquals(b, instance.getB(), 1e-14);

    }

    @Test
    public void testRemez4HP() {
        Remez instance = new Remez(4, new double[]{0, 1}, new double[]{0, 1});
        double[] b = new double[]{
            -0.00491184502517476058,
            -0.22098926365629884,
            0.5098236900503494,
            -0.22098926365629884,
            -0.0049118450251747605};

        assertArrayEquals(b, instance.getB(), 1e-14);

    }


@Ignore
    @Test
    public void testRemez1() {
    //remez(20, [0, 8.333333333333334E-4, 0.008333333333333333, 0.25, 0.2916666666666667, 0.3333333333333333],[0,0,1,1,0,0])
    // geht nicht ????
        Remez instance = new Remez(20, new double[]{0, 8.333333333333334E-4, 0.008333333333333333, 0.25, 0.2916666666666667, 0.3333333333333333},new double[]{0,0,1,1,0,0});
        double[] b = new double[]{
            -0.00491184502517476058,
            -0.22098926365629884,
            0.5098236900503494,
            -0.22098926365629884,
            -0.0049118450251747605};

//        assertArrayEquals(b, instance.getB(), 1e-14);

    }

@Ignore
    @Test
    public void testRemez2() {
//remez(20, [0, 0.001, 0.008, 0.25, 0.3, 1],[0.1,0.1,0.9,0.9,0.1,0.1])
        Remez instance = new Remez(20, new double[]{0, 0.001, 0.008, 0.25, 0.3, 1}, new double[]{0.1,0.1,0.9,0.9,0.1,0.1});
        double[] b = new double[]{
            -0.00491184502517476058,
            -0.22098926365629884,
            0.5098236900503494,
            -0.22098926365629884,
            -0.0049118450251747605};

//        assertArrayEquals(b, instance.getB(), 1e-14);

    }

}
