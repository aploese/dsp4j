/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

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
public class StereoShortFileTest {
    
    public StereoShortFileTest() {
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
        short[] data = new short[]{
            0,
            Short.MAX_VALUE / 4, 
            Short.MAX_VALUE / 2, 
            Short.MAX_VALUE,
            Short.MAX_VALUE / 2,
            Short.MAX_VALUE / 4,
            0,
            0,
            Short.MIN_VALUE / 4,
            Short.MIN_VALUE / 2,
            Short.MIN_VALUE,
            Short.MIN_VALUE / 2,
            Short.MIN_VALUE / 4,
            0};
        
        System.out.println("setX");
        int sample = 0;
        StereoShortFileSink sink = new StereoShortFileSink(File.createTempFile("test", "wav"), 8000);
        for (int i = 0; i < data.length; i++) {
            sink.setX(data[i], (short)-data[i]);
        }
        sink.close();
        
        
        StereoShortFileSource source = new StereoShortFileSource(sink.getWavOut());
        assertEquals(sink.isBigEndian(), source.isBigEndian());
        assertEquals(sink.getEncoding(), source.getEncoding());
        assertEquals(sink.getChannels(), source.getChannels());
        assertEquals(sink.getSampleRate(), source.getSampleRate(), Double.MIN_VALUE);
        assertEquals(sink.getFrameSize(), source.getFrameSize());
        assertEquals(sink.getSampleSizeInBits(), source.getSampleSizeInBits());
        
        for (int i = 0; i < data.length; i++) {
            source.clock();
            assertEquals("Error at left:" + i, data[i], source.getLeftY());
            assertEquals("Error at right:" + i, (short)-data[i], source.getRightY());
        }
        source.clock();
    }

}