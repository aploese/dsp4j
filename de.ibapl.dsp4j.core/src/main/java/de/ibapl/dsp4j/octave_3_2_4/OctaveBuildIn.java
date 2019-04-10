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
package de.ibapl.dsp4j.octave_3_2_4;

import java.util.Arrays;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class OctaveBuildIn {

    public static final double EPS = eps(1.0);

    public static double[] real(Complex[] arr) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].getReal();
        }
        return result;
    }

    public static double[] imag(Complex[] arr) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].getImaginary();
        }
        return result;
    }

    public static Complex prod(Complex[] arr) {
        if (arr.length == 0) {
            return Complex.ONE;
        }

        Complex result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            result = result.multiply(arr[i]);
        }
        return result;
    }

    public static Complex[] neg(Complex[] arr) {
        Complex[] result = new Complex[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].negate();
        }
        return result;
    }

    @Deprecated
    public static double[] abs(Complex[] arr) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].abs();
        }
        return result;
    }

    @Deprecated
    public static double[] divide(double[] arr, double d) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] / d;
        }
        return result;
    }

    public static int[] find(RealVector v) {
        int[] result = new int[v.getDimension()];
        int j = 0;
        for (int i = 0; i < v.getDimension(); i++) {
            if (Double.compare(v.getEntry(i), 0.0) != 0) {
                result[j++] = i;
            }
        }
        return Arrays.copyOf(result, j);
    }

    public static int[] find(double[] d, FindEval eval) {
        int[] result = new int[d.length];
        int j = 0;
        for (int i = 0; i < d.length; i++) {
            if (eval.eval(d[i])) {
                result[j++] = i;
            }
        }
        return Arrays.copyOf(result, j);
    }

    public static RealMatrix diag(double[] v, int n) {
        final int size = v.length + Math.abs(n);

        RealMatrix result = new Array2DRowRealMatrix(size, size);
        int row = n >= 0 ? n : 0;
        int col = n < 0 ? -n : 0;
        for (int i = row; i < v.length; i++) {
            result.setEntry(row++, col++, v[i]);
        }
        return result;
    }

    public static class FrExp {

        private int exponent;
        private double mantissa;

        double frexp(double value) {
            long bits = Double.doubleToLongBits(value);
            
            // Test for NaN, infinity.
            if (Double.isNaN(value) || Double.isInfinite(value) || value +value == value) {
                exponent = 0;
                mantissa = value;
            } else {

                boolean neg = (bits < 0);
                exponent = (int) ((bits >> 52) & 0x7ffL) - 1023;
                long _mantissa = (bits & 0x0000fffffffffffffL) | (0x0010000000000000L);
                mantissa = (double)_mantissa * 2.220446049250313E-16; // * Math.pow(2, -52);
                
                if (mantissa >= 1.0) {
                    mantissa /= 2;
                    exponent++;
                }


                if (neg) {
                    mantissa = -mantissa;
                }

            }
            return mantissa;
        }

        /**
         * @return the exponent
         */
        public int getExponent() {
            return exponent;
        }

        /**
         * @return the mantissa
         */
        public double getMantissa() {
            return mantissa;
        }
    }

    public static double eps(double val) {
        FrExp frexp = new FrExp();
        frexp.frexp(val);
        if (Double.isNaN(val) || Double.isInfinite(val)) {
            return Double.NaN;
        }
        if (val == 0.0) {
            return Double.MIN_VALUE;
        }
        return Math.pow(2.0, frexp.getExponent() - 53);
    }

    public interface FindEval {

        boolean eval(double d);
    }
}
