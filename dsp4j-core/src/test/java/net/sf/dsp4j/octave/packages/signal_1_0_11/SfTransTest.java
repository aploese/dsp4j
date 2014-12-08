package net.sf.dsp4j.octave.packages.signal_1_0_11;

import net.sf.dsp4j.ComplexUtil;
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