package net.sf.dsp4j.fms4j.fms;

import java.io.File;
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
public class FmsContainerTest extends VisualResultCheckTest {

    final private static Logger LOG = LoggerFactory.getLogger(FmsContainerTest.class);
    private ShortFileSink sfs;

    public FmsContainerTest() {
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

    /**
     * Test of setSampleRate method, of class CostasLoop.
     */
    @Test
    @Ignore
    public void testSetSampleRate() {
        System.out.println("setSampleRate");
        FmsContainer instance = new FmsContainer(null);
        instance.setSampleRate(44100);
        assertEquals(44100, instance.getSampleRate(), Double.MIN_VALUE);
    }

    private static short scale(double d) {
        return (short) (d * Short.MAX_VALUE);
    }

    @Test
    @Ignore
    public void testFmsAudio() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "14:57\r000155*\rKastelwald          \rWaldkirch           \r\rSuicidgefährdet; Treffpunkt Carolu");
        doTest("/FMS-test-20110207-10003-130_0_10:22:40_NUR_FMS.wav", false, 0, data);
    }

    @Test
    @Ignore
    public void testFmsAudio1() throws Exception {
        FmsData data1 = new FmsData(FW, BW, "65", "34-19", (byte) 1, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        //       FmsData data2 = new FmsData(Dienstkennung.FW, Laenderkennung.BW, "65", "34-19", (byte)15, Baustufenkennung.BEIDE_RICHTUNGEN, Richtungskennung.LST_ZU_FZG, TaktischeKurzinformation.TKI_IV);
        doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_00:46:31.wav", false, 0, data1);
    }

    @Test
    @Ignore
    public void testFmsAudio2() throws Exception {
        //Start @Sample 5314 - 522 = 4792
        //Start @Sample 91499 - 522 = 90977
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\r000472*\rDobel               13\rSiensbach           \r\r\r   ");
        doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_02:43:01.945_NUR_FMS.wav", false, 0, data);
    }

    @Test
    @Ignore
    public void testFmsAudio3() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\r000472*\rDobel               13\rSiensbach           \r\r\r   ");
        doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_02:43:01.945.wav", false, 0, data);
    }

    @Test
    @Ignore
    public void testFmsAudio_Probealarm_Teningen() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\r000472*\rDobel               13\rSiensbach           \r\r\r   ");
        doTest("/FMS-test-16bit-44.1kHz-20110416-010_0_06:34:01.325.wav", false, 0, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_2_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "36-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV);
        doTest("/teningen/20110416-hauptübung/02-fms.wav", false, 134, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_3_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "36-99", (byte) 0x0e, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/03-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_4_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "\rFW+OV,Objekt wird eingenebelt , falls BM\r");
        doTest("/teningen/20110416-hauptübung/04-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_5_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "\rFW+OV,Objekt wird eingenebelt , falls BM\r");
        doTest("/teningen/20110416-hauptübung/05-fms.wav", false, 480 + 29 + 38, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_6_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\r000477*\rOtto Graf GmbH      Carl Zeiss Str      2\rTeningen            \r**ÜBUNG**");
        doTest("/teningen/20110416-hauptübung/06-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_7_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\r000477*\rOtto Graf GmbH      Carl Zeiss Str      2\rTeningen            \r**ÜBUNG**");
        doTest("/teningen/20110416-hauptübung/07-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_8_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/08-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_9_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/09-fms.wav", false, 240, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_10_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV);
        doTest("/teningen/20110416-hauptübung/10-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_11_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-44", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/11-fms.wav", false, 1064 - 523, data);
    }

    @Test // 34-44??
    public void testFmsAudio_Probealarm_Teningen_12_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/12-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_13_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-44", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV);
        doTest("/teningen/20110416-hauptübung/13-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_14_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/14-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_15_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV);
        doTest("/teningen/20110416-hauptübung/15-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_16_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-33", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I);
        doTest("/teningen/20110416-hauptübung/16-fms.wav", false, 186, data);
    }

    @Test
    public void testFmsAudio_Probealarm_Teningen_17_fms() throws Exception {
        FmsData data = new FmsData(FW, BW, "65", "34-33", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV);
        doTest("/teningen/20110416-hauptübung/17-fms.wav", false, 186, data);
    }
    private boolean success;

    public void doTest(String resFileName, boolean showResult, final int startSample, final FmsData data) throws Exception {
        success = false;
        MonoShortFileSource msfs = new MonoShortFileSource(FmsContainerTest.class.getResourceAsStream(resFileName));

        FmsModulator m = new FmsModulator(0.001);
        m.setSampleRate(msfs.getSampleRate());

        FmsContainer fmsContainer = new FmsContainer(new FmsContainerListener() {
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
            public boolean error(FmsData fmsData) {
                if (data != FmsData.ERROR_DUMMY) {
                    org.junit.Assert.fail(fmsData.toString());
                }
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


        fmsContainer.setSampleRate(msfs.getSampleRate());

        File f = createFile("testFms", showResult);
        sfs = new ShortFileSink(f, msfs.getSampleRate(), 6);

        short synthBit = 0;
        short realBit = 0;
        short lastRealBit = 0;
        int lastRealBitCount = 0;
        int sample = 0;
        m.addData(data);
        while (msfs.clock()) {
            sample++;
            fmsContainer.setX(msfs.getY());
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
            if (sample == 4662) {
                fmsContainer.getCurrentBit();
            }
            synthBit = m.getCurrentFrequency() == 1200 ? Short.MAX_VALUE : Short.MIN_VALUE;

            if (sample >= startSample) {
                m.clock();
                synthBit = m.getCurrentFrequency() == 1200 ? Short.MAX_VALUE : Short.MIN_VALUE;
            }
            if (showResult) {
                sfs.setX(msfs.getY(),
                        AudioSink.scale0_2PI_to_short(fmsContainer.getNco().getPhi()),
                        (short) (fmsContainer.getCostasLoop().getPhiError() * Short.MAX_VALUE / Math.PI),
                        synthBit,
                        realBit,
                        (short) (fmsContainer.getSymbolFilterY() * Short.MAX_VALUE));
            }
        }

        assertTrue(success || data == null);
    }
}
