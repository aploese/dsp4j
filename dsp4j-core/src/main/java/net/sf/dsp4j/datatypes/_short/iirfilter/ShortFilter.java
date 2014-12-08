/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._short.iirfilter;

import net.sf.dsp4j.datatypes._short.ShortBlock;

/**
 *
 * @author aploese
 */
interface ShortFilter extends ShortBlock {
    
    public final static short Q_DOT_15_EXP = 15;
    public final static int Q_DOT_15_VALUE = 1 << 15;

}
