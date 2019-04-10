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
import static de.ibapl.dsp4j.fms4j.fms.Laenderkennung.BW;
import static de.ibapl.dsp4j.fms4j.fms.Richtungskennung.*;
import static de.ibapl.dsp4j.fms4j.fms.TaktischeKurzinformation.*;
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