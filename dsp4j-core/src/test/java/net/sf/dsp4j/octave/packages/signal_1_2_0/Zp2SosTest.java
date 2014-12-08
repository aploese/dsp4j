/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave.packages.signal_1_2_0;

import net.sf.dsp4j.octave.packages.signal_1_0_11.Bilinear;
import net.sf.dsp4j.octave.packages.signal_1_0_11.Butter;
import net.sf.dsp4j.octave.packages.signal_1_0_11.SfTrans;
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