/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

import static net.sf.dsp4j.fms4j.fms.Baustufenkennung.*;
import static net.sf.dsp4j.fms4j.fms.Dienstkennung.FW;
import static net.sf.dsp4j.fms4j.fms.Laenderkennung.BW;
import static net.sf.dsp4j.fms4j.fms.Richtungskennung.*;
import static net.sf.dsp4j.fms4j.fms.TaktischeKurzinformation.*;
import org.junit.After;
import org.junit.Test;

/**
 *
 * @author aploese
 */
public class FMS_16bit_Mono_11025_Test {

    final static String DIR = "FMS-lineIn_11025";
    FmsTestListener testSetup;
    final static int FILTER_DELAY = 213;

    @After
    public void tearDown() throws Exception {
        testSetup.tearDown();
    }

    @Test
    public void testFmsAudio_00_46_31_095() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.setIgnoreTxtLengthParityError(true);
        testSetup.add(FILTER_DELAY + 117000, new FmsData(FW, BW, "64", "07-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(FILTER_DELAY + 123000, new FmsData(FW, BW, "64", "07-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY);
        testSetup.add(FILTER_DELAY + 131992, new FmsData(FW, BW, "64", "07-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_III, "Unwetter Wassernot  \n\nIm Untertal 21      \nYach"));
        testSetup.add(Integer.MAX_VALUE, FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY);
        testSetup.add(FILTER_DELAY + 131992, new FmsData(FW, BW, "64", "07-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_III, "Unwetter Wassernot  \n\nIm Untertal 21      \nYach"));
        testSetup.add(FILTER_DELAY + 5466, new FmsData(FW, BW, "64", "07-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(FILTER_DELAY + 5466, new FmsData(FW, BW, "64", "07-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY);
        testSetup.add(FILTER_DELAY + 131992, new FmsData(FW, BW, "64", "07-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_III, "Unwetter Wassernot  \n\nIm Untertal 21      \nYach"));
        testSetup.add(Integer.MAX_VALUE, FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY);
        testSetup.add(FILTER_DELAY + 131992, new FmsData(FW, BW, "64", "07-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_III, "Unwetter Wassernot  \n\nIm Untertal 21      \nYach"));
        testSetup.doTest(DIR, "2013-05-06_17:05:59.772.wav");
    }

}