/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public enum Baustufenkennung {
    NUR_VOM_FZG("Übertragung nur vom Fahrzeug zur Leitstelle möglich"), 
    BEIDE_RICHTUNGEN("Übertragung in beide Richtungen möglich");
    public final String label;

    private Baustufenkennung(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
