/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public class ComplexUtil {

    static public void assertComplexEquals(double expectedRe, double expectedIm, Complex actual) {
        if (Double.compare(expectedRe, actual.getReal()) != 0
                || Double.compare(expectedIm, actual.getImaginary()) != 0) {
            throw new RuntimeException(String.format("Fail: expected %s %si but was: %s %si", String.valueOf(expectedRe), String.valueOf(expectedIm), String.valueOf(actual.getReal()), String.valueOf(actual.getImaginary())));
        }
    }


}
