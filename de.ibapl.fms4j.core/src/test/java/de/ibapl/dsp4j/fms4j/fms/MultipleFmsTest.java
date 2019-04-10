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

import java.util.logging.Logger;
import de.ibapl.dsp4j.fms4j.VisualResultCheckTest;
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import org.junit.Ignore;

/**
 *
 * @author aploese
 */
@Ignore
public class MultipleFmsTest extends VisualResultCheckTest {
    final private static Logger LOG = Logger.getLogger(MultipleFmsTest.class.getCanonicalName());
    private ShortFileSink sfs;
/*
    public MultipleFmsTest() {
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
    @Override
    public void tearDown() throws Exception {
        if (sfs != null) {
            sfs.close();
        }
        super.tearDown();
    }

    private static short scale(double d) {
        return (short) (d * Short.MAX_VALUE);
    }

    @Test
    @Ignore
    public void testFmsAudio() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "14:57\r000155*\rKastelwald          \rWaldkirch           \r\rSuicidgefährdet; Treffpunkt Carolu");
        //    doTest("/FMS-test-20110207-10003-130_0_10:22:40_NUR_FMS.wav", false, 0, data);
    }

    @Test
    @Ignore
    public void testFmsAudio1() throws Exception {
        FmsData data1 = new FmsData(FW, BW, "65", "34-19", (byte) 1, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        //       FmsData data2 = new FmsData(Dienstkennung.FW, Laenderkennung.BW, "65", "34-19", (byte)15, Baustufenkennung.BEIDE_RICHTUNGEN, Richtungskennung.LST_ZU_FZG, TaktischeKurzinformation.TKI_IV);
        //      doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_00:46:31.wav", false, 0, data1);
    }

    @Test
    @Ignore
    public void testFmsAudio2() throws Exception {
        //Start @Sample 5314 - 522 = 4792
        //Start @Sample 91499 - 522 = 90977
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\r000472*\rDobel               13\rSiensbach           \r\r\r   ");
//        doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_02:43:01.945_NUR_FMS.wav", true, 0, data);
    }

    @Test
    public void testFmsAudio_Dobel_13() throws Exception {

        FmsTestListener testSetup = new FmsTestListener();
        testSetup.add(448561 - 784, new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\r000472*\rDobel               13\rSiensbach           \r\r\r"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\r000472*\rDobel               13\rSiensbach           \r\r\r"));

        doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_02:43:01.945.wav", false, testSetup);
    }
        
    @Test
    @Ignore //TODO 44 kHz ??? in schnipsel 36 hinten
    public void testFmsAudio_Probealarm_Teningen_zvei_fms() throws Exception {
        FmsTestListener testSetup = new FmsTestListener();
        testSetup.add(557110 - 522, new FmsData(F_W_T, BW, "65", "36-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(569105 - 522, new FmsData(F_W_T, BW, "65", "36-99", (byte) 0x0e, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        doTest("/teningen/20110416-hauptübung/03-zvei-alarmierung-mit-fms.wav", false, testSetup);
    }
    
    @Test
    public void testFmsAudio_Probealarm_Teningen_fms_11kHz() throws Exception {
        FmsTestListener testSetup = new FmsTestListener();
        testSetup.add(814, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\r000477*\rOtto Graf GmbH      Carl Zeiss Str      2\rTeningen            \r**ÜBUNG**"));
        doTest("/teningen/20110416-hauptübung/fms-11kHz.wav", false, testSetup);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_alles() throws Exception {

        FmsTestListener testSetup = new FmsTestListener();
        testSetup.add(557110 - 522, new FmsData(FW, BW, "65", "36-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(569105 - 522, new FmsData(FW, BW, "65", "36-99", (byte) 0x0e, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(622560 - 784, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "\rFW+OV,Objekt wird eingenebelt , falls BM\r"));
        testSetup.add(699930 - 784, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "\rFW+OV,Objekt wird eingenebelt , falls BM\r"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\r000477*\rOtto Graf GmbH      Carl Zeiss Str      2\rTeningen            \r**ÜBUNG**"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\r000477*\rOtto Graf GmbH      Carl Zeiss Str      2\rTeningen            \r**ÜBUNG**"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-19", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-44", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-44", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-33", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-33", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));

        doTest("/teningen/20110416-hauptübung/alles.wav", false, testSetup);
    }
*/
}
