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
package de.ibapl.dsp4j.fms4j.zvei;

/**
 *
 * @author aploese
 */
public enum ZveiFreqTable {

    UNBEKANNT("-", 0),
    SIRENEN_DOPPEL_TON_I("SI", 675),
    LETTER_C("C", 810),
    SIRENEN_DOPPEL_TON_IV("SIV", 825),
    SIRENEN_DOPPEL_TON_VI("SVI", 1010),
    DIGIT_1("1", 1060),
    DIGIT_2("2", 1160),
    SIRENEN_DOPPEL_TON_II("SII", 1240),
    DIGIT_3("3", 1270),
    DIGIT_4("4", 1400),
    DIGIT_5("5", 1530),
    DIGIT_6("6", 1670),
    DIGIT_7("7", 1830),
    SIRENEN_DOPPEL_TON_III("SIII", 1860),
    DIGIT_8("8", 2000),
    DIGIT_9("9", 2200),
    SIRENEN_DOPPEL_TON_V("SV", 2280),
    DIGIT_0("0", 2400),
    WIEDERHOLUNG("Wdh", 2600),
    GRUPPENRUF("Gruppenruf", 2800);
    public final static double DEFAULT_TON_LENGTH_IN_S = 0.070;
    final String label;
    final int f;

    private ZveiFreqTable(String label, int f) {
        this.label = label;
        this.f = f;
    }

    static public String zveiFolgeToString(ZveiFreqTable[] folge) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < folge.length; i++) {
            if (folge[i] != null) {
                sb.append(folge[i].label);
            } else {
                sb.append("null");
            }
            if (i < folge.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static ZveiFreqTable find(int frequency) {
        if (frequency > 3100) {
            return UNBEKANNT;
        } else if (frequency < 790) {
            return UNBEKANNT;
        }
        if (frequency >= 1600) {
            if (frequency >= 2100) {
                if (frequency >= 2500) {
                    if (frequency >= 2700) {
                        return GRUPPENRUF;
                    } else {
                        return WIEDERHOLUNG;
                    }
                } else if (frequency >= 2300) {
                    return DIGIT_0;
                } else {
                    return DIGIT_9;
                }
            } else if (frequency >= 1750) {
                if (frequency >= 1915) {
                    return DIGIT_8;
                } else {
                    return DIGIT_7;
                }
            } else {
                return DIGIT_6;
            }
        } else if (frequency >= 1110) {
            if (frequency >= 1335) {
                if (frequency >= 1465) {
                    return DIGIT_5;
                } else {
                    return DIGIT_4;
                }
            } else {
                if (frequency >= 1215) {
                    return DIGIT_3;
                } else {
                    return DIGIT_2;
                }
            }
        } else if (frequency >= 935) {
            return DIGIT_1;
        } else {
            return LETTER_C;
        }
    }

    public static enum SierenenAusloesung {

        FEUERALARM(SIRENEN_DOPPEL_TON_I, SIRENEN_DOPPEL_TON_II),
        ENTWARNUNG(SIRENEN_DOPPEL_TON_I, SIRENEN_DOPPEL_TON_VI),
        PROBEALARM(SIRENEN_DOPPEL_TON_I, SIRENEN_DOPPEL_TON_III),
        ZIVILSCHUTZALARM(SIRENEN_DOPPEL_TON_I, SIRENEN_DOPPEL_TON_IV),
        ZIVILSCHUTZWARNUNG(SIRENEN_DOPPEL_TON_I, SIRENEN_DOPPEL_TON_V);
        final ZveiFreqTable z1;
        final ZveiFreqTable z2;

        private SierenenAusloesung(ZveiFreqTable z1, ZveiFreqTable z2) {
            this.z1 = z1;
            this.z2 = z2;
        }
    }
    
    public static int toId(ZveiFreqTable[] data) {
        return Integer.parseInt(toString(data));
    }

    public static String toString(ZveiFreqTable[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        for (int i = 0; i < data.length; i++) {
            if (data[i] == WIEDERHOLUNG) {
                sb.append(data[i - 1].label);
            } else {
                sb.append(data[i].label);
            }
        }
        return sb.toString();
    }
}
