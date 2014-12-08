/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public enum Laenderkennung {
    SA("Sachsen"), BUND("Bund"), BW("Baden-Württemberg"), BY_I("Bayern I"), B("Berlin"), BR("Bremen"), HH("Hamburg"), HE("Hessen"), NS("Niedersachsen"), NRW("Nordrhein-Westfalen"), RP("Rheinland-Pfalz"), SH("Schleswig-Holstein"), SL("Saarland"), BY_II("Bayern II"), LK_E("MV oder SAH"), LK_F("BRB oder THÜ");
    //        MV("Mecklenburg-Vorpommern"), // Ort 00..49
    //        SAH("Sachsen-Anhalt"), // Ort 50..99
    //        BRB("Brandenburg"), // Ort 00..49
    //        THÜ("Thüringen"); // Ort 50..99
    public final String label;

    private Laenderkennung(String label) {
        this.label = label;
    }

    public static Laenderkennung valueOf(byte value) {
        return Laenderkennung.values()[value];
    }
    
}
