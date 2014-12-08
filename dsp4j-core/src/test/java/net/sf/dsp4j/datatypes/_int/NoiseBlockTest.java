/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._int;

import java.io.File;
import net.sf.dsp4j.VisualResultCheckTest;
import net.sf.dsp4j.datatypes._short.MonoShortFileSource;
import net.sf.dsp4j.datatypes._short.ShortFileSink;
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
public class NoiseBlockTest extends VisualResultCheckTest {

    public NoiseBlockTest() {
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

        FmSquelch instance = new FmSquelch(0.4, 1, 10, 1, 2);

        MonoShortFileSource msfs = new MonoShortFileSource(NoiseBlockTest.class.getResourceAsStream("/noise1.wav")); //FMS-lineIn_11025.0_2013-05-06_17:05:23.735.wav"));
        createFile("test", msfs.getSampleRate(), 5);
        instance.setSampleRate(msfs.getSampleRate());
        while (msfs.clock()) {
            FmSquelch.State s = instance.setX(msfs.getY());
            if (isShowResult()) {
                sfs.setX(msfs.getY(),
                        (short) instance.getLpFast().getY(),
                        (short) instance.getLpSlow().getY(),
                        (short) (instance.ratio),
                        (s == FmSquelch.State.PASSING) ? (short) instance.getHpIn().getY() : 0);
            }
        }
    }
}