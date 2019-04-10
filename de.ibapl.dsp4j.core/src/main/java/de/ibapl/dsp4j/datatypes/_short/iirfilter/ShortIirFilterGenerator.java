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
package de.ibapl.dsp4j.datatypes._short.iirfilter;

import de.ibapl.dsp4j.datatypes.CascadedIirFilter;
import de.ibapl.dsp4j.datatypes.DirectIirFilter;
import de.ibapl.dsp4j.datatypes.IirFilterGenerator;
import de.ibapl.dsp4j.datatypes._double.iirfilter.GenericCascadedDoubleIirFilter;

/**
 *
 * @author aploese
 */
public class ShortIirFilterGenerator extends IirFilterGenerator {

    public ShortIirFilterGenerator(double sampleRate) {
        super(sampleRate);
    }

    @Override
    public <DF extends DirectIirFilter> DF createDirectFilter(double[] a, double[] b, Class<DF> clazz) {
        if (a.length == 2) {
                return (DF)new Direct1stOrderShortIirFilter(a, b);
        } else if (a.length == 3) {
                return (DF)new Direct2ndOrderShortIirFilter(a, b);
        } else {
                return (DF)new GenericDirectShortIirFilter(a, b);
            
        }
    }

    @Override
    public <CF extends CascadedIirFilter> CF createCascadedFilter(double[][] sos, double gain, Class<CF> clazz) {
        switch (sos.length) {
            case 1:
                return (CF) new Cascaded1ShortIirFilter(sos, gain);
            case 2:
                return (CF) new Cascaded2ShortIirFilter(sos, gain);
            case 3:
                return (CF) new Cascaded3ShortIirFilter(sos, gain);
            case 4:
                return (CF) new Cascaded4ShortIirFilter(sos, gain);
            default:
                return (CF) new GenericCascadedDoubleIirFilter(sos, gain);
        }
    }


}