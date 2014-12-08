/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

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
public class FmsBitDecoderTest {

    public FmsBitDecoderTest() {
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

    /**
     * Test of reset method, of class FmsBitDecoder.
     */
    @Test
    public void testX() {
        FmsBitDecoder instance = new FmsBitDecoder();
        assertEquals(FmsBitDecoder.State.NOISE_SPACE, instance.getState());
        for (int i = 0; i < 10; i++) {
            instance.setX(true);
            assertEquals(FmsBitDecoder.State.NOISE_MARK, instance.getState());
            assertEquals(i + 1, instance.getBitCount());
            assertEquals("AT I= " + i,  true, instance.isBit());
        }
        
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_1ST_000, instance.getState());
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_1ST_000, instance.getState());
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_2ND_11, instance.getState());

        instance.setX(true);
        assertEquals(FmsBitDecoder.State.COLLECT_2ND_11, instance.getState());
        instance.setX(true);
        assertEquals(FmsBitDecoder.State.COLLECT_3RD_0, instance.getState());

        instance.setX(false);
        assertEquals(FmsBitDecoder.State.COLLECT_4TH_1, instance.getState());
        instance.setX(true);
        assertEquals(FmsBitDecoder.State.COLLECT_5TH_0, instance.getState());
        instance.setX(false);
        assertEquals(FmsBitDecoder.State.DECODE_BITS, instance.getState());

    }
}