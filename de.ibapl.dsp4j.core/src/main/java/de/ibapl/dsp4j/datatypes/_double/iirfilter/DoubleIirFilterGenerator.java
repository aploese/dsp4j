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
package de.ibapl.dsp4j.datatypes._double.iirfilter;

import de.ibapl.dsp4j.datatypes.CascadedIirFilter;
import de.ibapl.dsp4j.datatypes.DirectIirFilter;
import de.ibapl.dsp4j.datatypes.IirFilterGenerator;

/**
 *
 * @author aploese
 */
public class DoubleIirFilterGenerator extends IirFilterGenerator {

    public DoubleIirFilterGenerator(double samplerate) {
        super(samplerate);
    }

    @Override
    public <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz) {
        if (a.length == 2) {
            return (DF) new Direct1stOrderDoubleIirFilter(a, b);
        } else if (a.length == 3) {
            return (DF) new Direct2ndOrderDoubleIirFilter(a, b);
        } else {
            return (DF) new GenericDirectDoubleIirFilter(a, b);
        }
    }

    @Override
    public <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz) {
        switch (sos.length) {
            case 1:
                return (CF) new Cascaded1DoubleIirFilter(sos, gain);
            case 2:
                return (CF) new Cascaded2DoubleIirFilter(sos, gain);
            case 3:
                return (CF) new Cascaded3DoubleIirFilter(sos, gain);
            case 4:
                return (CF) new Cascaded4DoubleIirFilter(sos, gain);
            default:
                return (CF) new GenericCascadedDoubleIirFilter(sos, gain);
        }
    }
}
