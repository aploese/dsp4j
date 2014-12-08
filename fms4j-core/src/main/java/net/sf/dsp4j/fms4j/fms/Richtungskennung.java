/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public enum Richtungskennung {
    FZG_ZU_LST("Fzg => Lst"), 
    LST_ZU_FZG("Lst => Fzg");
    public final String label;

    private Richtungskennung(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
