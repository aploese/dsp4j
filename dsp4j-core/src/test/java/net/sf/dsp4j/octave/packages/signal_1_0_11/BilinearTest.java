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