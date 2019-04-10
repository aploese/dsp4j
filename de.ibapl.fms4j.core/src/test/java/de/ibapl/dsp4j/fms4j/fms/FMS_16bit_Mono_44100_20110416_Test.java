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

import static de.ibapl.dsp4j.fms4j.fms.Baustufenkennung.*;
import static de.ibapl.dsp4j.fms4j.fms.Dienstkennung.FW;
import static de.ibapl.dsp4j.fms4j.fms.Dienstkennung.F_W_T;
import static de.ibapl.dsp4j.fms4j.fms.Laenderkennung.BW;
import static de.ibapl.dsp4j.fms4j.fms.Richtungskennung.*;
import static de.ibapl.dsp4j.fms4j.fms.TaktischeKurzinformation.*;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author aploese
 */
public class FMS_16bit_Mono_44100_20110416_Test {

    final static String DIR = "FMS-test-16bit-44.1kHz-20110416-010_0";
    FmsTestListener testSetup;
    final static int FILTER_DELAY = 213;

    @After
    public void tearDown() throws Exception {
        testSetup.tearDown();
    }

    @Test
    public void testFmsAudio_00_46_31_095() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(FILTER_DELAY + 5466, new FmsData(FW, BW, "65", "34-19", (byte) 0x01, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(FILTER_DELAY + 814, new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "00:46:31.095.wav");
    }

    @Test
    public void testFmsAudio_01_04_59_399() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "18-44", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, FmsData.CRC_ERROR_DUMMY);
        testSetup.add(FILTER_DELAY + 814, new FmsData(FW, BW, "65", "34-19", (byte) 0x01, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(FILTER_DELAY + 814, new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "01:04:59.399.wav");
    }

    @Test
    public void testFmsAudio_01_06_33_606() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "18-44", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.doTest(DIR, "01:06:33.606.wav");
    }

    @Test
    public void testFmsAudio_01_26_09_154() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "64", "07-44", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.doTest(DIR, "01:26:09.154.wav");
    }

    @Test //TODO ZVEI???
    public void testFmsAudio_02_43_01_945() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\n000472*\nDobel               13\nSiensbach           \n\n\n"));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "12:09\n000472*\nDobel               13\nSiensbach           \n\n\n"));
        testSetup.doTest(DIR, "02:43:01.945.wav");
    }

    @Test
    public void testFmsAudio_03_02_00_798() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "45-19", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 29132, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "03:02:00.798.wav");
    }

    @Test
    public void testFmsAudio_03_07_15_734() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "45-19", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 29132, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "03:07:15.734.wav");
    }

    @Test
    public void testFmsAudio_03_10_54_807() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "45-19", (byte) 0x01, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 29132, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x01, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "03:10:54.807.wav");
    }

    @Test
    public void testFmsAudio_03_16_58_828() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "45-19", (byte) 0x02, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 29132, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x02, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(FILTER_DELAY + 4540, new FmsData(FW, BW, "65", "45-19", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "03:16:58.828.wav");
    }

    @Test
    public void testFmsAudio_03_26_16_909() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "58-45", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "58-45", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "58-45", (byte) 0x02, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "58-45", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "58-45", (byte) 0x02, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "58-45", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "03:26:16.909.wav");
    }

    @Test
    public void testFmsAudio_04_31_10_156() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "13:57\n000473*\nRosenweg            23\nEmmendingen         \n\nAbwasserrohr verstopft\n"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "13:57\n000473*\nRosenweg            23\nEmmendingen         \n\nAbwasserrohr verstopft\n"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "13:57\n000473*\nRosenweg            23\nEmmendingen         \n\nAbwasserrohr verstopft\n"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "13:57\n000473*\nRosenweg            23\nEmmendingen         \n\nAbwasserrohr verstopft\n"));
        testSetup.doTest(DIR, "04:31:10.156.wav");
    }

    @Test
    public void testFmsAudio_04_35_14_786() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "04:35:14.786.wav");
    }

    @Test
    public void testFmsAudio_04_40_50_857() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "04:40:50.857.wav");
    }

    @Test
    public void testFmsAudio_04_49_34_818() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x01, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "04:49:34.818.wav");
    }

    @Test
    public void testFmsAudio_04_54_50_006() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x02, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "17-50", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "04:54:50.006.wav");
    }

    @Test
    public void testFmsAudio_06_03_43_984() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, FmsData.CRC_ERROR_DUMMY);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "43-23", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "43-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_II));
        testSetup.doTest(DIR, "06:03:43.984.wav");
    }

    @Test
    public void testFmsAudio_06_19_19_603() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "49-02", (byte) 0x01, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "49-02", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "06:19:19.603.wav");
    }
    
   @Test
    public void testFmsAudio_06_34_01_325() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", (byte) 0x03, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", (byte) 0x0e, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "\nFW+OV,Objekt wird eingenebelt , falls BM\n"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "\nFW+OV,Objekt wird eingenebelt , falls BM\n"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\n000477*\nOtto Graf GmbH      Carl Zeiss Str      2\nTeningen            \n**ÜBUNG**"));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "36-99", NUR_VOM_FZG, LST_ZU_FZG, TKI_IV, "16:00\n000477*\nOtto Graf GmbH      Carl Zeiss Str      2\nTeningen            \n**ÜBUNG**"));
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
        testSetup.doTest(DIR, "06:34:01.325.wav");
    }

   @Test
    public void testFmsAudio_06_35_51_251() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-10", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-10", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-10", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-19", (byte) 0x0f, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-23", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "1f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "1e", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "2f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "2f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "2f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "2f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "24", "00-00", (byte) 0x04, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "2e", "00-00", (byte) 0x04, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_II));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "3f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "3f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "34", "00-00", (byte) 0x00, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "3e", "00-00", (byte) 0x00, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "4f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "44", "00-00", (byte) 0x00, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "4f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "44", "00-00", (byte) 0x00, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "4e", "00-00", (byte) 0x00, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "5f", "00-00", (byte) 0x00, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(F_W_T, BW, "5e", "00-00", (byte) 0x04, NUR_VOM_FZG, LST_ZU_FZG, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-44", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, FmsData.CRC_ERROR_DUMMY);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "37-11", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-44", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-44", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-45", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-45", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "37-11", (byte) 0x03, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "37-11", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-33", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "06:35:51.251.wav");
    }

    @Test
    public void testFmsAudio_06_39_50_020() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "64", "07-51", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "06:39:50.020.wav");
    }
    
    @Test
    public void testFmsAudio_06_41_39_979() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, FmsData.CRC_ERROR_DUMMY);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-33", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_I));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "34-33", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "06:41:30.979.wav");
    }
    
    @Test
    public void testFmsAudio_06_44_11_241() throws Exception {
        testSetup = new FmsTestListener(false, false);
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-45", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-45", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-44", (byte) 0x04, BEIDE_RICHTUNGEN, FZG_ZU_LST, TKI_III));
        testSetup.add(Integer.MAX_VALUE, new FmsData(FW, BW, "65", "38-44", (byte) 0x0f, BEIDE_RICHTUNGEN, LST_ZU_FZG, TKI_IV));
        testSetup.doTest(DIR, "06:44:11.241.wav");
    }
    
}