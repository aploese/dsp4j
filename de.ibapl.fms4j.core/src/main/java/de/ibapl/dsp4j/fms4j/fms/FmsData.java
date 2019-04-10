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

/**
 *
 * @author aploese
 */
public class FmsData {

    public final static FmsData ERROR_DUMMY = new FmsData();
    public final static FmsData CRC_ERROR_DUMMY = new FmsData();
    public final static FmsData TXT_LENGTH_PARITY_ERROR_DUMMY = new FmsData();
    public final static FmsData TXT_CHAR_PARITY_ERROR_DUMMY = new FmsData();
    public final static FmsData TXT_CRC_ERROR_DUMMY = new FmsData();
    public final static FmsData TXT_ERROR_DUMMY = new FmsData();

    public static String formatOrtskennung(byte ortskennung) {
        return String.format("%02x", ortskennung);
    }

    public static byte parseOrtskennung(String ortskennung) {
        return (byte) Short.parseShort(ortskennung, 16);
    }

    public static short parseFahrzeugkennung(String fahrzeugkennung) {
        int result = 0;
        String[] s = fahrzeugkennung.split("-");
        for (String i : s) {
            result <<= 8;
            result |= Short.parseShort(i, 16);
        }
        return (short) result;
    }

    public static String formatFahrzeugkennung(short fahrzeugkennung) {
        StringBuilder sb = new StringBuilder();
        int value1 = (fahrzeugkennung >> 8) & 0xFF;
        sb.append(String.format("%02x", value1));
        int value2 = fahrzeugkennung & 0xFF;
        sb.append('-');
        sb.append(String.format("%02x", value2));
        return sb.toString();
    }
    private Dienstkennung dienstkennung;
    private Laenderkennung laenderkennung;
    private byte ortskennung;
    private short fahrzeugkennung;
    private byte status;
    private Baustufenkennung baustufenkennung;
    private Richtungskennung richtungskennung;
    private TaktischeKurzinformation taktischeKurzinfirmation;
    private String text;

    public FmsData() {
    }

    FmsData(Dienstkennung dienstkennung, Laenderkennung laenderkennung, byte ortskennung, short fahrzeugkennung, byte status, Baustufenkennung baustufenkennung, Richtungskennung richtungskennung, TaktischeKurzinformation taktischeKurzinfirmation) {
        this.dienstkennung = dienstkennung;
        this.laenderkennung = laenderkennung;
        this.ortskennung = ortskennung;
        this.fahrzeugkennung = fahrzeugkennung;
        this.status = status;
        this.baustufenkennung = baustufenkennung;
        this.richtungskennung = richtungskennung;
        this.taktischeKurzinfirmation = taktischeKurzinfirmation;
    }

    FmsData(Dienstkennung dienstkennung, Laenderkennung laenderkennung, String ortskennung, String fahrzeugkennung, byte status, Baustufenkennung baustufenkennung, Richtungskennung richtungskennung, TaktischeKurzinformation taktischeKurzinfirmation) {
        this(dienstkennung, laenderkennung, FmsData.parseOrtskennung(ortskennung), FmsData.parseFahrzeugkennung(fahrzeugkennung), status, baustufenkennung, richtungskennung, taktischeKurzinfirmation);
    }

    FmsData(Dienstkennung dienstkennung, Laenderkennung laenderkennung, byte ortskennung, short fahrzeugkennung, Baustufenkennung baustufenkennung, Richtungskennung richtungskennung, TaktischeKurzinformation taktischeKurzinfirmation, String text) {
        this(dienstkennung, laenderkennung, ortskennung, fahrzeugkennung, (byte) 10, baustufenkennung, richtungskennung, taktischeKurzinfirmation);
        this.text = text;
    }

    FmsData(Dienstkennung dienstkennung, Laenderkennung laenderkennung, String ortskennung, String fahrzeugkennung, Baustufenkennung baustufenkennung, Richtungskennung richtungskennung, TaktischeKurzinformation taktischeKurzinfirmation, String text) {
        this(dienstkennung, laenderkennung, ortskennung, fahrzeugkennung, (byte) 10, baustufenkennung, richtungskennung, taktischeKurzinfirmation);
        this.text = text;
    }

    /**
     * @return the dienstkennung
     */
    public Dienstkennung getDienstkennung() {
        return dienstkennung;
    }

    /**
     * @param dienstkennung the dienstkennung to set
     */
    public void setDienstkennung(Dienstkennung dienstkennung) {
        this.dienstkennung = dienstkennung;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dienstkennung: ").append(dienstkennung).append(' ');
        sb.append("Länderkennung: ").append(laenderkennung).append(' ');
        sb.append("Ortskennung: ").append(formatOrtskennung(ortskennung)).append(' ');
        sb.append("Fahrzeugkennung: ").append(formatFahrzeugkennung(fahrzeugkennung)).append(' ');
        sb.append("Meldung: ").append(getMsg()).append(' ');
        sb.append("Status: ").append(status).append(' ');
        sb.append("Richtungskennung: ").append(richtungskennung).append(' ');
        sb.append("Baustufenkennung: ").append(baustufenkennung).append(' ');
        sb.append("TaktischeKurzinformation: ").append(taktischeKurzinfirmation);
        if (text != null) {
            sb.append(" text: \"").append(text).append("\"");
        }
        return sb.toString();
    }

    public String toCsvString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dienstkennung).append(", ");
        sb.append(laenderkennung).append(", ");
        sb.append(formatOrtskennung(ortskennung)).append(", ");
        sb.append(formatFahrzeugkennung(fahrzeugkennung)).append(", ");
        sb.append(getMsg()).append(", ");
        sb.append(status).append(", ");
        sb.append(richtungskennung).append(", ");
        sb.append(baustufenkennung).append(", ");
        sb.append(taktischeKurzinfirmation);
        if (text != null) {
            sb.append(", \"").append(text).append("\"");
        } else {
            sb.append(", \"\"");
        }
        return sb.toString();
    }

    public static String getCsvHead() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dienstkennung, ");
        sb.append("Länderkennung, ");
        sb.append("Ortskennung, ");
        sb.append("Fahrzeugkennung, ");
        sb.append("Medung, ");
        sb.append("Status, ");
        sb.append("Richtungskennung, ");
        sb.append("Baustufenkennung, ");
        sb.append("TaktischeKurzinformation, ");
        sb.append("Text");
        return sb.toString();
    }

    /**
     * @return the laenderkennung
     */
    public Laenderkennung getLaenderkennung() {
        return laenderkennung;
    }

    /**
     * @param laenderkennung the laenderkennung to set
     */
    public void setLaenderkennung(Laenderkennung laenderkennung) {
        this.laenderkennung = laenderkennung;
    }

    /**
     * @return the ortskennung
     */
    public byte getOrtskennung() {
        return ortskennung;
    }

    /**
     * @param ortskennung the ortskennung to set
     */
    public void setOrtskennung(byte ortskennung) {
        this.ortskennung = ortskennung;
    }

    /**
     * @return the fahrzeugkennung
     */
    public short getFahrzeugkennung() {
        return fahrzeugkennung;
    }

    /**
     * @param fahrzeugkennung the fahrzeugkennung to set
     */
    public void setFahrzeugkennung(short fahrzeugkennung) {
        this.fahrzeugkennung = fahrzeugkennung;
    }

    /**
     * @return the status
     */
    public byte getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(byte status) {
        this.status = status;
    }

    /**
     * @return the baustufenkennung
     */
    public Baustufenkennung getBaustufenkennung() {
        return baustufenkennung;
    }

    /**
     * @param baustufenkennung the baustufenkennung to set
     */
    public void setBaustufenkennung(Baustufenkennung baustufenkennung) {
        this.baustufenkennung = baustufenkennung;
    }

    /**
     * @return the richtungskennung
     */
    public Richtungskennung getRichtungskennung() {
        return richtungskennung;
    }

    /**
     * @param richtungskennung the richtungskennung to set
     */
    public void setRichtungskennung(Richtungskennung richtungskennung) {
        this.richtungskennung = richtungskennung;
    }

    /**
     * @return the taktischeKurzinfirmation
     */
    public TaktischeKurzinformation getTaktischeKurzinfirmation() {
        return taktischeKurzinfirmation;
    }

    /**
     * @param taktischeKurzinfirmation the taktischeKurzinfirmation to set
     */
    public void setTaktischeKurzinfirmation(TaktischeKurzinformation taktischeKurzinfirmation) {
        this.taktischeKurzinfirmation = taktischeKurzinfirmation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.dienstkennung != null ? this.dienstkennung.hashCode() : 0);
        hash = 79 * hash + (this.laenderkennung != null ? this.laenderkennung.hashCode() : 0);
        hash = 79 * hash + this.ortskennung;
        hash = 79 * hash + this.fahrzeugkennung;
        hash = 79 * hash + this.status;
        hash = 79 * hash + (this.baustufenkennung != null ? this.baustufenkennung.hashCode() : 0);
        hash = 79 * hash + (this.richtungskennung != null ? this.richtungskennung.hashCode() : 0);
        hash = 79 * hash + (this.taktischeKurzinfirmation != null ? this.taktischeKurzinfirmation.hashCode() : 0);
        hash = 79 * hash + (this.text != null ? this.text.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FmsData other = (FmsData) obj;
        if (this.dienstkennung != other.dienstkennung) {
            return false;
        }
        if (this.laenderkennung != other.laenderkennung) {
            return false;
        }
        if (this.ortskennung != other.ortskennung) {
            return false;
        }
        if (this.fahrzeugkennung != other.fahrzeugkennung) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.baustufenkennung != other.baustufenkennung) {
            return false;
        }
        if (this.richtungskennung != other.richtungskennung) {
            return false;
        }
        if (this.taktischeKurzinfirmation != other.taktischeKurzinfirmation) {
            return false;
        }
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text)) {
            return false;
        }
        return true;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    public String getMsg() {
        if (dienstkennung == null) {
            return "";
        }
        switch (dienstkennung) {
            case FW:
                switch (richtungskennung) {
                    case FZG_ZU_LST:
                        switch (status) {
                            case 0x00:
                                return "Notruf";
                            case 0x01:
                                return "Einsatzbereit über Funk";
                            case 0x02:
                                return "Einsatzbereit auf Wache";
                            case 0x03:
                                return "Einsatzauftrag übernommen";
                            case 0x04:
                                return "Einsatzstelle erreicht";
                            case 0x05:
                                return "Sprechwunsch";
                            case 0x06:
                                return "Fahrzeug ausser Betrieb/nicht einsatzbereit";
                            case 0x07:
                                return "Mit Patient zum Krankenhaus/Wohnung";
                            case 0x08:
                                return "Krankenhaus/Wohnung angekommen";
                            case 0x09:
                                return "Anmeldung/Handquittung";
                            case 0x0a:
                                return "Status 10";
                            case 0x0b:
                                return "Status 11";
                            case 0x0c:
                                return "Status 12";
                            case 0x0d:
                                return "Status 13";
                            case 0x0e:
                                return "automatische Quittung bei selektivem Anruf";
                            case 0x0f:
                                return "Sprechtaste";
                        }
                        break;
                    case LST_ZU_FZG:
                        switch (status) {
                            case 0x00:
                                return "Statusabfrage";
                            case 0x01:
                                return "Sammelruf an alle (A)";
                            case 0x02:
                                return "einrücken/abbrechen (E)";
                            case 0x03:
                                return "für Einsatzübername melden (C)";
                            case 0x04:
                                return "über Telefon melden (F)";
                            case 0x05:
                                return "Wache anfahren (H)";
                            case 0x06:
                                return "Sprechaufforderung (J)";
                            case 0x07:
                                return "Lagemeldung geben (L)";
                            case 0x08:
                                return "Fernwirktelegramm I (P)";
                            case 0x09:
                                return "Fernwirktelegramm II (U)";
                            case 0x0a:
                                return "dig. Alarmierung (c)";
                            case 0x0b:
                                return "dig. Alarmierung (d)";
                            case 0x0c:
                                return "dig. Alarmierung (h)";
                            case 0x0d:
                                return "dig. Alarmierung (o)";
                            case 0x0e:
                                return "Sttaus 14";
                            case 0x0f:
                                return "autom. Quittung";
                        }
                }
        }
        return "Status " + status;
    }
    
    public String getFahrzeugIdStr() {
        return String.format("%01x%01x%02x%04x", dienstkennung.ordinal(), laenderkennung.ordinal(), ortskennung, fahrzeugkennung);
    }
    
    public int getFahrzeugId() {
        return dienstkennung.ordinal() << 28 | laenderkennung.ordinal() << 24 | ortskennung << 16 | fahrzeugkennung;
    }

    public int getOrtsId() {
        return dienstkennung.ordinal() << 20 | laenderkennung.ordinal() << 16 | ortskennung << 8;
    }

    public String getOrtsIdStr() {
        return String.format("%01x%01x%02x00", dienstkennung.ordinal(), laenderkennung.ordinal(), ortskennung);
    }
    
}
