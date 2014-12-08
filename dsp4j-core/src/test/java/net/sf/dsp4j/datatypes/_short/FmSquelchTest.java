/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short;

import net.sf.dsp4j.VisualResultCheckTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author aploese
 */
public class FmSquelchTest extends VisualResultCheckTest {

    public FmSquelchTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of setX method, of class NoiseBlock.
     */
    @Test
    @Ignore
    public void testSetX() throws Exception {
        System.out.println("setX");

        FmSquelch instance = new FmSquelch(900, 10, 3600);

        MonoShortFileSource msfs = new MonoShortFileSource(FmSquelchTest.class.getResourceAsStream("/noise2.wav")); //FMS-lineIn_11025.0_2013-05-06_17:05:23.735.wav"));
        createFile("test", msfs.getSampleRate(), 3);
        instance.setSampleRate(msfs.getSampleRate());
        while (msfs.clock()) {
            boolean s = instance.setX(msfs.getY());
            if (isShowResult()) {
                sfs.setX(msfs.getY(),
                        (short) instance.getLp().getY(),
                        s ? msfs.getY() : 0);
            }
        }
    }
}