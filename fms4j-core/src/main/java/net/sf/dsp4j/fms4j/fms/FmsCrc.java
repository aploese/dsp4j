/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public class FmsCrc {

    public final static int g = 0x00C5;
    private int crc;

    /**
     * @return the crc
     */
    public int getCrc() {
        return crc;
    }

    /**
     * @param crc the crc to set
     */
    public void setCrc(int crc) {
        this.crc = crc;
    }

    public boolean isValid() {
        return crc == 0;
    }

    public void addBit(boolean bitIsSet) {
        crc <<= 1;
        crc |= bitIsSet ? 1 : 0;
        if ((crc & 0x0080) == 0x0080) {
            crc ^= g;
        }
    }
    
    public void reset() {
        crc = 0;
    }


}
