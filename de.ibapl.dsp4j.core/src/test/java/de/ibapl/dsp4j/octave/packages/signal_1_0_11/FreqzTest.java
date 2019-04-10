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

import org.apache.commons.math3.complex.Complex;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class FreqzTest {

    public FreqzTest() {
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
     * Test of setResponseIIR method, of class Freqz.
     */
    @Test
    public void testSetResponseIIR() throws Exception {
        System.out.println("setResponseIIR");
        double[] b = new double[]{0.292893218813452, 0.585786437626905, 0.292893218813452};
        double[] a = new double[]{1, 0, 0.171572875253810};
        Complex[] H = new Complex[]{
            new Complex(0.999999999999999, 0),
            new Complex(0.9975807419950482, -0.06947545254501845),
            new Complex(0.9902062642659809, -0.13927477253774964),
            new Complex(0.9775231602362022, -0.20967724868190102),
            new Complex(0.9589326814781685, -0.280864879703556),
            new Complex(0.9335809853760831, -0.3528529539471224),
            new Complex(0.9003569851763376, -0.4253949474128567),
            new Complex(0.8579135337281909, -0.49785369072132274),
            new Complex(0.8047378541243646, -0.5690355937288489),
            new Complex(0.7393094553410734, -0.6369979766860443),
            new Complex(0.6603928065285595, -0.6988673466085266),
            new Complex(0.5675038241520144, -0.750752931823165),
            new Complex(0.4615391919957226, -0.7878966844392574),
            new Complex(0.3454412013113517, -0.8052298876115738),
            new Complex(0.22460214559948477, -0.7984304963768957),
            new Complex(0.10660035218982354, -0.7653239708337984),
            new Complex(6.869154506741516E-17, -0.7071067811865481),
            new Complex(-0.08756847864979014, -0.628687001716399),
            new Complex(-0.15127261710561163, -0.5377538600154088),
            new Complex(-0.19000838962140323, -0.44291310254620453),
            new Complex(-0.206060030201157, -0.3517664748879001),
            new Complex(-0.20387728598446392, -0.26971002426934576),
            new Complex(-0.18867564761206207, -0.1996679066045898),
            new Complex(-0.16538033077658662, -0.1424936950654225),
            new Complex(-0.13807118745769942, -0.09763107293781828),
            new Complex(-0.10983432370948107, -0.06373768599851709),
            new Complex(-0.08285013890408806, -0.03914450719272179),
            new Complex(-0.05857634135389744, -0.02213930596477555),
            new Complex(-0.037941255037442514, -0.011112736313738688),
            new Complex(-0.021508994589768654, -0.004613647012114638),
            new Complex(-0.009605551848085888, -0.0013510428049387094),
            new Complex(-0.002407608616408153, -1.6767534810442123E-4)};
        double[] w = new double[]{
            0.0,
            0.09817477042468103,
            0.19634954084936207,
            0.2945243112740431,
            0.39269908169872414,
            0.4908738521234052,
            0.5890486225480862,
            0.6872233929727672,
            0.7853981633974483,
            0.8835729338221293,
            0.9817477042468103,
            1.0799224746714913,
            1.1780972450961724,
            1.2762720155208536,
            1.3744467859455345,
            1.4726215563702154,
            1.5707963267948966,
            1.6689710972195777,
            1.7671458676442586,
            1.8653206380689396,
            1.9634954084936207,
            2.061670178918302,
            2.1598449493429825,
            2.2580197197676637,
            2.356194490192345,
            2.454369260617026,
            2.552544031041707,
            2.650718801466388,
            2.748893571891069,
            2.84706834231575,
            2.945243112740431,
            3.043417883165112
        };
        int n = 32;
        Freqz instance = new Freqz(b, a, n);
        assertArrayEquals(H, instance.H);
        assertArrayEquals(w, instance.w, Double.MIN_VALUE);
    }

    /**
     * Test of setResponseFIR method, of class Freqz.
     */
    @Test
    @Ignore
    public void testSetResponseFIR() throws Exception {
        System.out.println("setResponseFIR");
        double[] b = new double[]{1, 0, 1};
        int n = 32;
        Freqz instance = new Freqz(b, n);
        for (Complex c : instance.H) {
            System.out.println(c);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetResponseCom() {
        Complex c0 = new Complex(1,1);
        Complex c1 = new Complex(0,1);
        Complex c2 = c0.divide(c1);

    }
}
