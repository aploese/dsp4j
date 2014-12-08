/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public enum TaktischeKurzinformation {
    TKI_I("TKI I"), 
    TKI_II("TKI II"), 
    TKI_III("TKI III"), 
    TKI_IV("TKI IV");

    public static TaktischeKurzinformation valueOf(int i) {
        return values()[i];
    }
    public final String label;

    private TaktischeKurzinformation(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
