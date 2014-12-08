/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.zvei;

/**
 *
 * @author aploese
 */
public interface ZveiFolgeContainerListener {

    void success(ZveiFreqTable[] result);

    void fail(ZveiFreqTable[] result);
    
}
