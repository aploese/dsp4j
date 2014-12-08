/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public interface FmsContainerListener {
    
    void success(FmsData fmsData);
    boolean error(FmsData fmsData);
    boolean crcError(FmsData fmsData);
    boolean txtLengthParityError(FmsData fmsData);
    boolean txtCharParityError(FmsData fmsData, String txt);
    boolean txtCrcError(FmsData fmsData, String txt);
    boolean txtError(FmsData fmsData, String txt);
    
}
