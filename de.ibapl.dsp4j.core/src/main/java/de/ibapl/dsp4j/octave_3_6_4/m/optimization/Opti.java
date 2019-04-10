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
package de.ibapl.dsp4j.octave_3_6_4.m.optimization;

import java.util.Map;

/**
 *
 * @author aploese
 */
public enum Opti {

    DISPLAY,
    FUN_VAL_CHECK,
    OUTPUT_FCN,
    TOL_X,
    MAX_ITER,
    MAX_FUN_EVAL;

    static Object optimget(Map<Opti, Object> options, Opti opti) {
        return options.get(opti);
    }
    static boolean optimget(Map<Opti, Object> options, Opti opti, boolean b) {
        Object result = options.get(opti);
        if (result != null) {
            return (Boolean) result;
        } else {
            return b;
        }
    }

    static double optimget(Map<Opti, Object> options, Opti opti, double d) {
        Object result = options.get(opti);
        if (result != null) {
            return (Double) result;
        } else {
            return d;
        }
    }

    static long optimget(Map<Opti, Object> options, Opti opti, long l) {
        Object result = options.get(opti);
        if (result != null) {
            return (Long) result;
        } else {
            return l;
        }
    }
}
