package net.sf.dsp4j.octave_3_2_4;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
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
public class EigTest {

    public EigTest() {
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
     * Test of eig method, of class Eig.
     */
    @Test
    public void testEig() throws Exception {
        RealMatrix d = new Array2DRowRealMatrix(new double[][] {{1,2}, {2,1}}, false);
        Complex c[] = Eig.eig(d);
//        assertArrayEquals(new double[] {2.9999999999999996, -0.9999999999999996}, Eig.eig(new double[][] {{1, 2}, {2, 1}}), Double.MIN_VALUE);
    }

}