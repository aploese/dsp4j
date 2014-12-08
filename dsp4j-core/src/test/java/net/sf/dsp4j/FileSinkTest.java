package net.sf.dsp4j;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class FileSinkTest {

    public FileSinkTest() {
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
     * Test of flush method, of class FileSink.
     */
    @Test
    public void testData() throws Exception {
//        System.out.println("flush");
//        File outWav = File.createTempFile(FileSinkTest.class.getName(), "raw");
//        FileSink instance = new FileSink(outWav, 16, 2, true, true);
//        instance.setSampleRate(8000);
//        assertEquals(0, instance.getBufferDataLength());
//        instance.getDoubleChannel(0).setX(-2);
//        instance.getDoubleChannel(1).setX(2);
//        assertEquals(4, instance.getBufferDataLength());
//        instance.flush();
//        assertEquals(0, instance.getBufferDataLength());
//        instance.getDoubleChannel(0).setX(-1);
//        instance.getDoubleChannel(1).setX(1);
//        instance.getDoubleChannel(0).setX(-0.5);
//        instance.getDoubleChannel(1).setX(0);
//        instance.getDoubleChannel(0).setX(-0);
//        instance.getDoubleChannel(1).setX(1);
//        instance.getDoubleChannel(0).setX(0.5);
//        instance.getDoubleChannel(1).setX(-1);
//        instance.getDoubleChannel(0).setX(1);
//        instance.getDoubleChannel(1).setX(1);
//        instance.close();
//        Runtime.getRuntime().exec(new String[] {"audacity", outWav.getAbsolutePath()}).waitFor();
//TODO       fail("The test case is a prototype.");
    }

    /**
     * Test of flush method, of class FileSink.
     */
    @Test
    public void test4Channels() throws Exception {
//        System.out.println("flush");
//        File outWav = File.createTempFile(FileSinkTest.class.getName(), "raw");
//        FileSink instance = new FileSink(outWav, 16, 4, true, true);
//        instance.setSampleRate(8000);
//        instance.getDoubleChannel(0).setX(-2);
//        instance.getDoubleChannel(1).setX(2);
//        instance.getDoubleChannel(2).setX(-1);
//        instance.getDoubleChannel(3).setX(1);
//        instance.getDoubleChannel(0).setX(-0.5);
//        instance.getDoubleChannel(1).setX(0);
//        instance.getDoubleChannel(2).setX(-0);
//        instance.getDoubleChannel(3).setX(1);
//        instance.getDoubleChannel(0).setX(0.5);
//        instance.getDoubleChannel(1).setX(-1);
//        instance.getDoubleChannel(2).setX(1);
//        instance.getDoubleChannel(3).setX(1);
//        instance.close();
//        Runtime.getRuntime().exec(new String[] {"audacity", outWav.getAbsolutePath()}).waitFor();
//TODO        fail("The test case is a prototype.");
    }

    /**
     * Test of flush method, of class FileSink.
     */
    @Ignore
    @Test
    public void testFlush() {
        System.out.println("flush");
        FileSink instance = null;
        instance.flush();
        // TODO review the generated test code and remove the default call to fail.
//TODO        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class FileSink.
     */
    @Ignore
    @Test
    public void testClose() {
        System.out.println("close");
        FileSink instance = null;
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
//TODO        fail("The test case is a prototype.");
    }

}