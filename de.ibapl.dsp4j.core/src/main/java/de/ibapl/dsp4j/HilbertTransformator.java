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
package de.ibapl.dsp4j;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public class HilbertTransformator extends AbstractSampleProcessingBlock {

       double[] b;
        double[] si;
        int mid;
        int length;
        Complex y;

        public HilbertTransformator(int length) {
            mid = length / 2;
            this.length = mid * 2;
            b = new double[length];
            si = new double[length];
            for (int i = 0; i < this.length; i++) {
                if (i == mid) {
                    b[i] = 0;
                } else {

                    b[i] = (1 - Math.cos((i - mid) * Math.PI)) / ((i - mid) * Math.PI);
                }

            }
        }

        @In
        public Complex setX(double x) {
            si[0] = x;
            double resultIm = 0;
            for (int i = length - 1; i >= 1; i--) {
                resultIm += b[i] * si[i];
                si[i] = si[i - 1];
            }
            resultIm += b[0] * si[0];
            y = new Complex(si[mid], resultIm);
                    return y;
        }
        
        @Out
        public Complex getY() {
            return y;
        }

}