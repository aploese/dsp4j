/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) 2024, Arne Plöse and individual contributors as indicated
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
module de.ibapl.dsp4j.core {

    // for javax.sound.**
    requires java.desktop;
    requires commons.math3;
    requires java.logging;

    exports de.ibapl.dsp4j;
    exports de.ibapl.dsp4j.datatypes;
    exports de.ibapl.dsp4j.datatypes._byte;
    exports de.ibapl.dsp4j.datatypes._double;
    exports de.ibapl.dsp4j.datatypes._double.iirfilter;
    exports de.ibapl.dsp4j.datatypes._int;
    exports de.ibapl.dsp4j.datatypes._short;
    exports de.ibapl.dsp4j.datatypes._short.iirfilter;
    exports de.ibapl.dsp4j.octave;
    exports de.ibapl.dsp4j.octave.packages.signal_1_0_11;
    exports de.ibapl.dsp4j.octave.packages.signal_1_2_0;
    exports de.ibapl.dsp4j.octave.packages.specfun_1_1_0;
    exports de.ibapl.dsp4j.octave_3_2_4;
    exports de.ibapl.dsp4j.octave_3_2_4.m.general;
    exports de.ibapl.dsp4j.octave_3_2_4.m.polynomial;
    exports de.ibapl.dsp4j.octave_3_6_4.m.optimization;
}
