/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aploese
 */
public class Clock {
    
    private double samplerate;
    private List<ClockChangeListener> clockChangeListener = new ArrayList();
    
}
