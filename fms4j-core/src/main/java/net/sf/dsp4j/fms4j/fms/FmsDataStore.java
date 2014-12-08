/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public interface FmsDataStore {
    
    String getFmsOrtsmname(FmsData data);
    String getFmsFahrzeugname(FmsData data);
    String getZveiname(int id);
}
