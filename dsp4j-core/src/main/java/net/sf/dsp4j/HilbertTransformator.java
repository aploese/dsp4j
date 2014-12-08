package net.sf.dsp4j;

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