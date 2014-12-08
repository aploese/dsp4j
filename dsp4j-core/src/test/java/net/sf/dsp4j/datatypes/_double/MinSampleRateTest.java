/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j.datatypes._double;

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
public class MinSampleRateTest {

    public MinSampleRateTest() {
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
    public void testSetMinSampleRate() {
        MinSampleRate instance = new MinSampleRate(64000);
        instance.setSampleRate(8000);
        assertEquals(8, instance.getMultiplier());
        instance.setSampleRate(64000);
        assertEquals(1, instance.getMultiplier());
        instance.setMinOutSampleRate(8000);
        assertEquals(1, instance.getMultiplier());

    }

}