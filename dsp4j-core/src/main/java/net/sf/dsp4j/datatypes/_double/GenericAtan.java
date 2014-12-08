/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double;

/**
 *
 * @author aploese
 */
public class GenericAtan implements FunctionAtan {

    @Override
    public double aTan(double re, double im) {
        return Math.atan2(im, re);
    }
    
}
