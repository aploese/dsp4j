package net.sf.dsp4j.octave_3_2_4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.sf.dsp4j.octave_3_2_4.OctaveBuildIn.*;

/**
 *
 * @author aploese
 */
public class OctaveBuildInTest {

    public OctaveBuildInTest() {
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
    public void testFrexp_0_25() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(0.25);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(-1, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testFrexp_0_5() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(0.5);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(0, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testFrexp_1() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(1);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(1, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testFrexp_2() throws Exception {
        FrExp frExp = new FrExp();
        frExp.frexp(2);
        assertEquals(0.5, frExp.getMantissa(), Double.MIN_VALUE);
        assertEquals(2, frExp.getExponent(), Double.MIN_VALUE);
    }

    @Test
    public void testEps_0_5() throws Exception {
        assertEquals(Math.pow(2, -53), eps(0.5), Double.MIN_VALUE);
    }

    @Test
    public void testEps_1() throws Exception {
        assertEquals(Math.pow(2, -52), eps(1), Double.MIN_VALUE);
    }

    @Test
    public void testEps_2() throws Exception {
        assertEquals(Math.pow(2, -51), eps(2), Double.MIN_VALUE);
    }

    @Test
    public void testEps_MAX_VALUE() throws Exception {
        assertEquals(Math.pow(2, 971), eps(Double.MAX_VALUE), Double.MIN_VALUE);
    }

    @Test
    public void testEps_0() throws Exception {
        assertEquals(Math.pow(2, -1074), eps(0), Double.MIN_VALUE);
    }

    @Test
    public void testEps_MIN_VALUE_DIV_2() throws Exception {
        assertEquals(Math.pow(2, -1074), eps(Double.MIN_VALUE / 2), Double.MIN_VALUE);
    }

    @Test
    public void testEps_MIN_VALUE_DIV_16() throws Exception {
        assertEquals(Math.pow(2, -1074), eps(Double.MIN_VALUE / 16), Double.MIN_VALUE);
    }

    @Test
    public void testEps_POSITIVE_INFINITY() throws Exception {
        assertEquals(Double.NaN, eps(Double.POSITIVE_INFINITY), Double.MIN_VALUE);
    }

    @Test
    public void testEps_NaN() throws Exception {
        assertEquals(Double.NaN, eps(Double.NaN), Double.MIN_VALUE);
    }
}