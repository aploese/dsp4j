/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.fms4j.fms;

import java.io.File;
import java.util.logging.Logger;
import de.ibapl.dsp4j.AudioSink;
import de.ibapl.dsp4j.datatypes._double.iirfilter.AbstractCascadedDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import de.ibapl.dsp4j.datatypes._short.MonoShortSpeakerSink;
import de.ibapl.dsp4j.fms4j.VisualResultCheckTest;
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static de.ibapl.dsp4j.fms4j.fms.Laenderkennung.*;
import static de.ibapl.dsp4j.fms4j.fms.Baustufenkennung.*;
import static de.ibapl.dsp4j.fms4j.fms.Dienstkennung.*;
import static de.ibapl.dsp4j.fms4j.fms.Richtungskennung.*;
import static de.ibapl.dsp4j.fms4j.fms.TaktischeKurzinformation.*;

/**
 *
 * @author aploese
 */
public class FmsContainerSynthTest extends VisualResultCheckTest {

    final private static Logger LOG = Logger.getLogger(FmsContainerSynthTest.class.getCanonicalName());

    public FmsContainerSynthTest() {
    }
    MonoShortSpeakerSink msss;
    ShortFileSink sfs;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        if (msss != null) {
            msss.close();
        }
        if (sfs != null) {
            sfs.close();
        }
        sfs = null;
        super.tearDown();
    }

    @Test
    @Ignore
    public void testFmsAudio3() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "ABCDE");
        doTest(false, false, 0, Short.MAX_VALUE, 0, 44100, data);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 22050, data);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 11025, data);
    }

    @Test
    @Ignore
    public void testFmsAudio4() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "ABCDEF");
        doTest(false, false, 0, Short.MAX_VALUE, 0, 44100, data);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 22050, data);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 11025, data);
    }

    @Test
    @Ignore
    public void testFmsAudio5() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "ABCDEFG");
        doTest(false, false, 0, Short.MAX_VALUE, 0, 44100, data);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 22050, data);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 11025, data);
    }

    @Test
    @Ignore
    public void testFmsAudio6() throws Exception {
        FmsData data = new FmsData(FW, BW, "64", "17-50", (byte) 15, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II);
        doTest(false, false, 0, Short.MAX_VALUE, 0, 11025, data);
    }

    @Test
    @Ignore
    public void testFmsAudio7() throws Exception {
        FmsData data = new FmsData(FW, BW, "64", "17-50", (byte) 1, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II);
        doTest(false, false, -0.5, Short.MAX_VALUE / 3, 0, 44100, data);
        //       doTest(false, false, 0, Short.MAX_VALUE, 0, 22050, data);
        //       doTest(false, false, 0, Short.MAX_VALUE, 0, 11025, data);
    }
    
    @Test
    @Ignore
    public void testFmsAudio8() throws Exception {
        FmsData data = new FmsData(FW, BW, "64", "07-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_III, "Unwetter Wassernot  \n\nIm Untertal 21      \nYach");
        doTest(false, false, -0.5, Short.MAX_VALUE / 3, 0, 11025, data);
        //       doTest(false, false, 0, Short.MAX_VALUE, 0, 22050, data);
        //       doTest(false, false, 0, Short.MAX_VALUE, 0, 11025, data);
    }
    

    boolean success;

    //TODO noise
    public void doTest(boolean soundOut, boolean showResult, double offset, double signalPower, double noiseRatio, double samplerate, final FmsData data) throws Exception {
        success = false;
        if (soundOut) {
            msss = new MonoShortSpeakerSink(samplerate);
        }
        FmsModulator m = new FmsModulator(0.001);
        m.setSampleRate(samplerate);
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(samplerate);
        AbstractCascadedDoubleIirFilter filter = gen.getLP_ButterFc(7, 1500, AbstractCascadedDoubleIirFilter.class); //FMS32 7.ordnung
        DirectDoubleIirFilter sFilter = gen.getHP_ButterFc(1, 1200, DirectDoubleIirFilter.class);

        FmsContainer fmsContainer = new FmsContainer(new FmsContainerListener() {
            @Override
            public boolean error(FmsData fmsData) {
                org.junit.Assert.fail(fmsData.toString());
                return true;
            }

            @Override
            public void success(FmsData fmsData) {
                assertEquals(data, fmsData);
                success = true;
            }

            @Override
            public boolean crcError(FmsData fmsData) {
                org.junit.Assert.fail(fmsData.toString());
                return true;
            }

            @Override
            public boolean txtCrcError(FmsData fmsData, String txt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean txtLengthParityError(FmsData fmsData) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean txtCharParityError(FmsData fmsData, String txt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean txtError(FmsData fmsData, String txt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        fmsContainer.setSampleRate(m.getSampleRate());

        if (showResult) {
            File f = createFile("testFms", showResult);
            sfs = new ShortFileSink(f, m.getSampleRate(), 8);
        }

        short synthBit = 0;
        short realBit = 0;
        short lastRealBit = 0;
        int lastRealBitCount = 0;
        double oldSymbol = 0;

        m.addData(data);
        do {
            m.clock();
            filter.setX((m.getY() + offset) * signalPower);
            fmsContainer.setX(m.getY());
//            fmsContainer.setX(filter.getY());
//filter raus            fmsContainer.setX((m.getY() + offset) * signalPower);
            if (soundOut) {
                msss.setX((short) (filter.getY() / 5));
            }
            if (showResult) {
                if (fmsContainer.getFmsBD().getState() == FmsBitDecoder.State.DECODE_BITS) {
                    if (lastRealBitCount < fmsContainer.getFmsBD().bitCount) {
                        realBit = (short) (fmsContainer.getFmsBD().bit ? Short.MAX_VALUE / 2 : Short.MIN_VALUE / 2);
                        lastRealBitCount = fmsContainer.getFmsBD().bitCount;
                    } else {
                        realBit = fmsContainer.getFmsBD().bit ? Short.MAX_VALUE : Short.MIN_VALUE;
                        if (lastRealBit != realBit) {
                            lastRealBitCount = 0;
                        }
                    }
                    lastRealBit = fmsContainer.getFmsBD().bit ? Short.MAX_VALUE : Short.MIN_VALUE;
                } else {
                    lastRealBit = 0;
                    lastRealBitCount = 0;
                    realBit = 0;
                    lastRealBitCount = 0;
                }
                synthBit = m.getCurrentFrequency() == 1200 ? Short.MAX_VALUE : Short.MIN_VALUE;

                sfs.setX((short) (m.getY() * Short.MAX_VALUE),
                        (short) filter.getY(),
                        AudioSink.scale0_2PI_to_short(fmsContainer.getNco().getPhi()),
                        (short) (synthBit * 0.9),
                        (short) (realBit * 0.9),
                        (short) (fmsContainer.getCostasLoop().getPhiError() * Short.MAX_VALUE / Math.PI),
                        (short) (fmsContainer.getSymbolFilterY() * Short.MAX_VALUE / Math.PI),
                        (short) ((fmsContainer.getSymbolFilterY() - oldSymbol) * Short.MAX_VALUE * 2));
//                        (short) (sFilter.setX(fmsContainer.getSymbolFilterY()) * Short.MAX_VALUE / Math.PI));
                oldSymbol = fmsContainer.getSymbolFilterY();
            }
        } while (!m.isLast());
        assertTrue(success);
    }

    @Test
    @Ignore
    public void inFilterTest() throws Exception {
        double samplerate = 11025;
        File f = createFile("testFms", true);
        sfs = new ShortFileSink(f, samplerate, 1);
        msss = new MonoShortSpeakerSink(samplerate);
        DirectDoubleIirFilter iF = new DoubleIirFilterGenerator(samplerate).getHP_ButterFc(2, 50, DirectDoubleIirFilter.class);

        int T = (int) (samplerate / 40);
        short D = 200;
        short MIN = (short) Short.MIN_VALUE / 2;
        short MAX = (short) Short.MAX_VALUE / 2;
        for (int i = 0; i < T; i++) {
            iF.setX(0);
            sfs.setX((short) iF.getY());
            msss.setX((short) (0));
        }
        for (int n = 0; n < 10; n++) {
            for (int i = 0; i < T; i++) {
                iF.setX(MAX);
                sfs.setX((short) iF.getY());
                msss.setX(D);
            }
            iF.setX(MIN);
            msss.setX((short) -D);
            sfs.setX((short) iF.getY());
            for (int i = 0; i < T; i++) {
                iF.setX(MAX);
                sfs.setX((short) iF.getY());
                msss.setX(D);
            }
            for (int i = 0; i < T; i++) {
                iF.setX(MIN);
                sfs.setX((short) iF.getY());
                msss.setX((short) -D);
            }
            iF.setX(MAX);
            sfs.setX((short) iF.getY());
            msss.setX(D);
            for (int i = 0; i < T; i++) {
                iF.setX(MIN);
                sfs.setX((short) iF.getY());
                msss.setX((short) -D);
            }
        }


    }
}
