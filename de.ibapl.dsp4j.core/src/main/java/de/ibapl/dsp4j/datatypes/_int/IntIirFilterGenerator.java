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
package de.ibapl.dsp4j.datatypes._int;

import de.ibapl.dsp4j.datatypes.CascadedIirFilter;
import de.ibapl.dsp4j.datatypes.DirectIirFilter;
import de.ibapl.dsp4j.datatypes.IirFilterGenerator;

/**
 *
 * @author aploese
 */
public class IntIirFilterGenerator extends IirFilterGenerator {

    public final static int Q_DOT_31 = 1 << 31;
    
    public IntIirFilterGenerator(double sampleRate) {
        super(sampleRate);
    }

    @Override
    public <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz) {
        int[] aInt = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            aInt[i] = (int)Math.round(a[i] * Q_DOT_31);
        }
        int[] bInt = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            bInt[i] = (int)Math.round(b[i] * Q_DOT_31);
        }
        return (DF) new GenericDirectIntIirFilter().setCoeff(aInt, bInt);
    }

    @Override
    public <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
