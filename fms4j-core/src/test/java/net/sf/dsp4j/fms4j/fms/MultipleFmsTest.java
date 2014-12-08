package net.sf.dsp4j.fms4j.fms;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.dsp4j.AudioSink;
import static net.sf.dsp4j.fms4j.fms.Laenderkennung.*;
import static net.sf.dsp4j.fms4j.fms.Baustufenkennung.*;
import static net.sf.dsp4j.fms4j.fms.Dienstkennung.*;
import static net.sf.dsp4j.fms4j.fms.Richtungskennung.*;
import static net.sf.dsp4j.fms4j.fms.TaktischeKurzinformation.*;
import net.sf.dsp4j.datatypes._short.MonoShortFileSource;
import net.sf.dsp4j.fms4j.VisualResultCheckTest;
import net.sf.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author aploese
 */
@Ignore
public class MultipleFmsTest extends VisualResultCheckTest {
    final private static Logger LOG = LoggerFactory.getLogger(MultipleFmsTest.class);
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
