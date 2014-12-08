package net.sf.dsp4j.octave_3_2_4.m.polynomial;

import org.apache.commons.math3.complex.Complex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.sf.dsp4j.octave_3_2_4.OctaveBuildIn.real;

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