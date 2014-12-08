package net.sf.dsp4j.octave.packages.specfun_1_1_0;


import java.util.Arrays;
import net.sf.dsp4j.octave_3_2_4.OctaveBuildIn;

/*## Copyright (C) 2001 David Billinghurst
 ##
 ## This program is free software; you can redistribute it and/or modify
 ## it under the terms of the GNU General Public License as published by
 ## the Free Software Foundation; either version 3 of the License, or
 ## (at your option) any later version.
 ##
 ## This program is distributed in the hope that it will be useful,
 ## but WITHOUT ANY WARRANTY; without even the implied warranty of
 ## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 ## GNU General Public License for more details.
 ##
 ## You should have received a copy of the GNU General Public License
 ## along with this program; If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * ## -*- texinfo -*- ##
 *
 * @deftypefn {Function File} {[
 * @var{k},
 * @var{e}] =} ellipke (
 * @var{m}[,
 * @var{tol}]) ## Compute complete elliptic integral of first K(
 * @var{m}) and second E(
 * @var{m}). ## ##
 * @var{m} is either real array or scalar with 0 <= m <= 1 ## ## @var{tol} will
 * be ignored (@sc{Matlab} uses this to allow faster, less ## accurate
 * approximation) ## ## Ref: Abramowitz, Milton and Stegun, Irene A. Handbook of
 * Mathematical ## Functions, Dover, 1965, Chapter 17. ## @seealso{ellipj} ##
 * @end deftypefn
 *
 * ## Author: David Billinghurst <David.Billinghurst@riotinto.com> ## Created:
 * 31 January 2001 ## 2001-02-01 Paul Kienzle ## * vectorized ## * included
 * function name in error messages ## 2003-1-18 Jaakko Ruohio ## * extended for
 * m < 0
 */
public class Ellipke {

    private final double[] k;
    private final double[] e;

    public Ellipke(double[] m) {

        k = new double[m.length];
        e = new double[m.length];

        for (double d : m) {
            if (d > 1) {
                throw new IllegalArgumentException("ellipke must have m[X] <= 1");
            }
        }

        int Nmax = 16;
        int[] idx = OctaveBuildIn.find(m, new OctaveBuildIn.FindEval() {
            @Override
            public boolean eval(double d) {
                return d == 1;
            }
        });
        for (int i = 0; i < idx.length; i++) {
            k[idx[i]] = Double.POSITIVE_INFINITY;
            e[idx[i]] = 1.0;
        }

        idx = OctaveBuildIn.find(m, new OctaveBuildIn.FindEval() {
            @Override
            public boolean eval(double d) {
                return d == Double.NEGATIVE_INFINITY;
            }
        });
        for (int i = 0; i < idx.length; i++) {
            k[idx[i]] = 0.0;
            e[idx[i]] = Double.POSITIVE_INFINITY;
        }

        // Arithmetic-Geometric Mean (AGM) algorithm
        // ( Abramowitz and Stegun, Section 17.6 )
        idx = OctaveBuildIn.find(m, new OctaveBuildIn.FindEval() {
            @Override
            public boolean eval(double d) {
                return (d != 1.0) && (d != Double.NEGATIVE_INFINITY);
            }
        });
        double[] a = new double[0];
        double[] sum = new double[0];
        double[] mult_k = new double[0];
        double[] mult_e = new double[0];
        int[] idx_neg = new int[0];

        if (idx.length > 0) {
            idx_neg = OctaveBuildIn.find(m, new OctaveBuildIn.FindEval() {
                @Override
                public boolean eval(double d) {
                    return (d < 0.0) && (d != Double.NEGATIVE_INFINITY);
                }
            });
            mult_k = new double[m.length];
            mult_e = new double[m.length];
            for (int j = 0; j < idx_neg.length; j++) {
                mult_k[idx_neg[j]] = 1 / Math.sqrt(1 - m[idx_neg[j]]);
                mult_e[idx_neg[j]] = Math.sqrt(1 - m[idx_neg[j]]);
                m[idx_neg[j]] = -m[idx_neg[j]] / (1 - m[idx_neg[j]]);
            }
            a = new double[idx.length];
            Arrays.fill(a, 1);
            double[] b = new double[idx.length];
            double[] c = new double[idx.length];
            for (int j = 0; j < idx.length; j++) {
                b[j] = Math.sqrt(1.0 - m[idx[j]]);
                c[j] = Math.sqrt(m[idx[j]]);
            }
            double f = 0.5;
            sum = new double[idx.length];
            for (int j = 0; j < c.length; j++) {
                sum[j] = f * c[j] * c[j];
            }

            double[] t = new double[a.length];
            for (int n = 0; n <= Nmax; n++) {
    
                for (int j = 0; j < a.length; j++) {
                    t[j] = (a[j] + b[j]) / 2;
                    c[j] = (a[j] - b[j]) / 2;
                    b[j] = Math.sqrt(a[j] * b[j]);
                }
                double[] oldA = a;
                a = t;
                t = oldA;
                f = f * 2;

                for (int j = 0; j < c.length; j++) {
                    sum[j] = sum[j] + f * c[j] * c[j];
                }

                boolean done = true;
                for (int j = 0; j < c.length; j++) {
                    done &= (c[j] / a[j] < OctaveBuildIn.EPS);
                }
                if (done) {
                    break;
                }

                if (n >= Nmax) {
                    throw new RuntimeException("ellipke: not enough workspace");
                }
            }
        }
        for (int j = 0; j < idx.length; j++) {
            k[idx[j]] = 0.5 * Math.PI / a[j];
            e[idx[j]] = 0.5 * Math.PI * (1.0 - sum[j]) / a[j];
        }
        for (int j = 0; j < idx_neg.length; j++) {
            k[idx_neg[j]] = mult_k[idx_neg[j]] * k[idx_neg[j]];
            e[idx_neg[j]] = mult_e[idx_neg[j]] * e[idx_neg[j]];
        }
    }

    /**
     * @return the k
     */
    public double[] getK() {
        return k;
    }

    public double getK(int index) {
        return k[index];
    }
    /**
     * @return the e
     */
    public double[] getE() {
        return e;
    }
    public double getE(int index) {
        return e[index];
    }
}