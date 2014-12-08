/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave_3_6_4.m.optimization;

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
