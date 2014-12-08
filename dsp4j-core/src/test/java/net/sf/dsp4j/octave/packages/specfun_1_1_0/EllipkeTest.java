/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.specfun_1_1_0;

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