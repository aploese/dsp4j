/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._int;

import java.io.File;
import net.sf.dsp4j.VisualResultCheckTest;
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
public class MonoIntegerFileTest {

    public MonoIntegerFileTest() {
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
    public void tearDown() throws Exception {
    }

    /**
     * Test of setX method, of class MonoIntegerFileSink.
     */
    @Test
    public void testSetX() throws Exception {
        int[] data = new int[]{
            0,
            Integer.MAX_VALUE / 4,
            Integer.MAX_VALUE / 2,
            Integer.MAX_VALUE,
            Integer.MAX_VALUE / 2,
            Integer.MAX_VALUE / 4,
            0,
            0,
            Integer.MIN_VALUE / 4,
            Integer.MIN_VALUE / 2,
            Integer.MIN_VALUE,
            Integer.MIN_VALUE / 2,
            Integer.MIN_VALUE / 4,
            0};

        System.out.println("setX");
        MonoIntegerFileSink sink = new MonoIntegerFileSink(File.createTempFile("test", "wav"), 8000);
        for (int i = 0; i < data.length; i++) {
            sink.setX(data[i]);
        }
        sink.close();


        MonoIntegerFileSource source = new MonoIntegerFileSource(sink.getWavOut());
        assertEquals(sink.isBigEndian(), source.isBigEndian());
        assertEquals(sink.getEncoding(), source.getEncoding());
        assertEquals(sink.getChannels(), source.getChannels());
        assertEquals(sink.getSampleRate(), source.getSampleRate(), Double.MIN_VALUE);
        assertEquals(sink.getFrameSize(), source.getFrameSize());
        assertEquals(sink.getSampleSizeInBits(), source.getSampleSizeInBits());

        for (int i = 0; i < data.length; i++) {
            if (i < data.length - 1) {
                assertTrue("At Idx: " + i, source.clock());
            } else {
                assertFalse(source.clock());
            }
            assertEquals("Error at:" + i, data[i], source.getY());
        }
        assertFalse(source.clock());

    }
}