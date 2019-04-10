/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.fms4j.fms;

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
