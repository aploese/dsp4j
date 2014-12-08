/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public enum Dienstkennung {
    N_V("nicht vergeben"), 
    P_D_BL("Polizei der Bundesländer"), 
    BGS("Bundesgrenzschutz"), 
    BKA("Bundeskriminalamt"), 
    KATS("Katastrophenschutz"), 
    ZOLL("Zoll"), 
    FW("Feuerwehr"), 
    THW("Technisches Hilfswerk"), 
    ASB("Arbeiter-Samariter-Bund"), 
    DRK("Deutsches Rotes Kreuz"), 
    JUH("Johanniter-Unfall-Hilfe"), 
    MHD("Malteser Hilfsdienst"), 
    DLRG("Deutsche Lebensrettungs-Gesellschaft"), 
    S_R_D("sonstige Rettungsdienste"), 
    Z_V("Zivilschutz"), 
    F_W_T("Fernwirktelegramm");
    public final String label;

    private Dienstkennung(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Dienstkennung valueOf(int i) {
        return values()[i];
    }
    
}
