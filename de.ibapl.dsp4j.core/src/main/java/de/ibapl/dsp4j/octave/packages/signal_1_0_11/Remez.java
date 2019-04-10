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
package de.ibapl.dsp4j.octave.packages.signal_1_0_11;

import java.util.Arrays;
import static de.ibapl.dsp4j.DspConst.TWO_PI;

/**************************************************************************
 * Parks-McClellan algorithm for FIR filter design (C version)
 *-------------------------------------------------
 *  Copyright (c) 1995,1998  Jake Janovetz <janovetz@uiuc.edu>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Library General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public
 *  License along with this library; if not, see <http://www.gnu.org/licenses/>.
 *
 *
 *  Sep 1999 - Paul Kienzle (pkienzle@cs.indiana.edu)
 *      Modified for use in octave as a replacement for the matlab function
 *      remez.mex.  In particular, magnitude responses are required for all
 *      band edges rather than one per band, griddensity is a parameter,
 *      and errors are returned rather than printed directly.
 *  Mar 2000 - Kai Habel (kahacjde@linux.zrz.tu-berlin.de)
 *      Change: ColumnVector x=arg(i).vector_value();
 *      to: ColumnVector x(arg(i).vector_value());
 *  There appear to be some problems with the routine Search. See comments
 *  therein [search for PAK:].  I haven't looked closely at the rest
 *  of the code---it may also have some problems.
 *************************************************************************/
/*
#include <octave/oct.h>
#include <cmath>

#ifndef OCTAVE_LOCAL_BUFFER
#include <vector>
#define OCTAVE_LOCAL_BUFFER(T, buf, size) \
std::vector<T> buf ## _vector (size); \
T *buf = &(buf ## _vector[0])
#endif
 */
public class Remez {

    final static int MAXITERATIONS = 40;

    public static enum Symmety {

        NEGATIVE,
        POSITIVE;
    }

    public static enum FType {

        BAND_PASS(Symmety.POSITIVE),
        HILBERT(Symmety.NEGATIVE),
        DIFFERENTIATOR(Symmety.NEGATIVE);
        final Symmety symmetry;

        private FType(Symmety symmetry) {
            this.symmetry = symmetry;
        }
    }
    public final static int DEFAULT_GRID_DENSITY = 16;

    public static double[] defaultWeight(final double[] bands) {
        double[] result = new double[bands.length / 2];
        Arrays.fill(result, 1.0);
        return result;
    }

    /* == Octave interface starts here ====================================== */
    /*
    DEFUN_DLD (remez, args, ,
    "b = remez(n, f, a [, w] [, ftype] [, griddensity])\n\
    Parks-McClellan optimal FIR filter design.\n\
    n gives the number of taps in the returned filter\n\
    f gives frequency at the band edges [ b1 e1 b2 e2 b3 e3 ...]\n\
    a gives amplitude at the band edges [ a(b1) a(e1) a(b2) a(e2) ...]\n\
    w gives weighting applied to each band\n\
    ftype is 'bandpass', 'hilbert' or 'differentiator'\n\
    griddensity determines how accurately the filter will be\n\
    constructed. The minimum value is 16, but higher numbers are\n\
    slower to compute.\n\
    \n\
    Frequency is in the range (0, 1), with 1 being the nyquist frequency")
     */
    public Remez(int order, double[] f, double[] a, FType ftype) {
        this(order, f, a, defaultWeight(f), ftype, DEFAULT_GRID_DENSITY);
    }

    public Remez(int order, double[] f, double[] a, double[] w) {
        this(order, f, a, w, FType.BAND_PASS, DEFAULT_GRID_DENSITY);
    }

    public Remez(int order, double[] f, double[] a) {
        this(order, f, a, defaultWeight(f), FType.BAND_PASS, DEFAULT_GRID_DENSITY);
    }

    //remez256, [0, 0.2497498779296875, 0.2502498779296875, 1], [1,1,0,0])

    public Remez(int order, double[] f, double[] response, double[] weight, FType ftype, int griddensity) {
        this.numtaps = order + 1;
        this.bands = new double[f.length];
        for (int i = 0; i < bands.length; i++) {
            bands[i] = f[i] / 2;
        }
        this.response = response;
        this.weight = weight;
        this.ftype = ftype;
        this.griddensity = griddensity;

        if (numtaps < 4) {
            throw new RuntimeException("remez: number of taps must be an integer greater than 3");
        }

        numbands = bands.length / 2;

        if (bands.length / 2 < 1 || bands.length % 2 == 1) {
            throw new RuntimeException("remez: must have an even number of band edges");
        }

        for (int i = 1; i < bands.length; i++) {
            if (bands[i] < bands[i - 1]) {
                throw new RuntimeException("band edges must be nondecreasing");
            }
        }

        if (bands[0] < 0 || bands[1] > 1) {
            throw new RuntimeException("band edges must be in the range [0,1]");
        }

        if (response.length != bands.length) {
            throw new RuntimeException("remez: must have one response magnitude for each band edge");
        }

        if (weight.length != numbands) {
            throw new RuntimeException("remez: need one weight for each band [=length(band)/2]");
        }

        if (griddensity < 16) {
            throw new RuntimeException("remez: griddensity is too low; must be greater than 16");
        }

        h = new double[numtaps];
        remez();

    }
    private double[] h;
    private int numtaps;
    private int numbands;
    private double[] bands;
    private double[] response;
    private double[] weight;
    private FType ftype;
    private int griddensity;

    public double[] getB() {
        return h;
    }

    /*******************
     * CreateDenseGrid
     *=================
     * Creates the dense grid of frequencies from the specified bands.
     * Also creates the Desired Frequency Response function (D[]) and
     * the Weight function (W[]) on that dense grid
     *
     *
     * INPUT:
     * ------
     * int      r        - 1/2 the number of filter coefficients
     * int      numtaps  - Number of taps in the resulting filter
     * int      numband  - Number of bands in user specification
     * double   bands[]  - User-specified band edges [2*numband]
     * double   des[]    - Desired response per band [2*numband]
     * double   weight[] - Weight per band [numband]
     * int      symmetry - Symmetry of filter - used for grid check
     * int      griddensity
     *
     * OUTPUT:
     * -------
     * int    gridsize   - Number of elements in the dense frequency grid
     * double Grid[]     - Frequencies (0 to 0.5) on the dense grid [gridsize]
     * double D[]        - Desired response on the dense grid [gridsize]
     * double W[]        - Weight function on the dense grid [gridsize]
     *******************/
    void CreateDenseGrid(final int r, final double Grid[], final double D[], final double W[]) {
        final double delf = 0.5 / (griddensity * r);

        /*
         * For differentiator, hilbert,
         *   symmetry is odd and Grid[0] = max(delf, bands[0])
         */
        double grid0 = (ftype.symmetry == Symmety.NEGATIVE) && (delf > bands[0]) ? delf : bands[0];

        int j = 0;
        for (int band = 0; band < numbands; band++) {
            double lowf = (band == 0 ? grid0 : bands[2 * band]);
            double highf = bands[2 * band + 1];
            int k = (int) Math.round((highf - lowf) / delf);
            for (int i = 0; i < k; i++) {
                D[j] = response[2 * band] + i * (response[2 * band + 1] - response[2 * band]) / (k - 1);
                W[j] = weight[band];
                Grid[j] = lowf;
                lowf += delf;
                j++;
            }
            if (j > 1) {
                Grid[j - 1] = highf;
            } else {
                throw new RuntimeException("Some Error im remenz CreateDenseGrid");
            }
        }

        /*
         * Similar to above, if odd symmetry, last grid point can't be .5
         *  - but, if there are even taps, leave the last grid point at .5
         */
        if ((ftype.symmetry == Symmety.NEGATIVE)
                && (Grid[Grid.length - 1] > (0.5 - delf))
                && (numtaps % 2) != 0) {
            Grid[Grid.length - 1] = 0.5 - delf;
        }
    }

    /********************
     * InitialGuess
     *==============
     * Places Extremal Frequencies evenly throughout the dense grid.
     *
     *
     * INPUT:
     * ------
     * int r        - 1/2 the number of filter coefficients
     * int gridsize - Number of elements in the dense frequency grid
     *
     * OUTPUT:
     * -------
     * int Ext[]    - Extremal indexes to dense frequency grid [r+1]
     ********************/
    private int[] InitialGuess(final int r, final int gridsize) {
        int[] Ext = new int[r + 1];
        for (int i = 0; i <= r; i++) {
            Ext[i] = i * (gridsize - 1) / r;
        }
        return Ext;
    }

    /***********************
     * CalcParms
     *===========
     *
     *
     * INPUT:
     * ------
     * int    r      - 1/2 the number of filter coefficients
     * int    Ext[]  - Extremal indexes to dense frequency grid [r+1]
     * double Grid[] - Frequencies (0 to 0.5) on the dense grid [gridsize]
     * double D[]    - Desired response on the dense grid [gridsize]
     * double W[]    - Weight function on the dense grid [gridsize]
     *
     * OUTPUT:
     * -------
     * double ad[]   - 'b' in Oppenheim & Schafer [r+1]
     * double x[]    - [r+1]
     * double y[]    - 'C' in Oppenheim & Schafer [r+1]
     ***********************/
    void CalcParms(final int r, final int Ext[], final double Grid[], final double D[], final double W[],
            final double ad[], final double x[], final double y[]) {
        double xi, delta, denom;

        /*
         * Find x[]
         */
        for (int i = 0; i <= r; i++) {
            x[i] = Math.cos(TWO_PI * Grid[Ext[i]]);
        }

        /*
         * Calculate ad[]  - Oppenheim & Schafer eq 7.132
         */
        int ld = (r - 1) / 15 + 1;         /* Skips around to avoid round errors */
        for (int i = 0; i <= r; i++) {
            denom = 1.0;
            xi = x[i];
            for (int j = 0; j < ld; j++) {
                for (int k = j; k <= r; k += ld) {
                    if (k != i) {
                        denom *= 2.0 * (xi - x[k]);
                    }
                }
            }
            if (Math.abs(denom) < 0.00001) {
                denom = 0.00001;
            }
            ad[i] = 1.0 / denom;
        }

        /*
         * Calculate delta  - Oppenheim & Schafer eq 7.131
         */
        double numer = denom = 0;
        double sign = 1;
        for (int i = 0; i <= r; i++) {
            numer += ad[i] * D[Ext[i]];
            denom += sign * ad[i] / W[Ext[i]];
            sign = -sign;
        }
        delta = numer / denom;
        sign = 1;

        /*
         * Calculate y[]  - Oppenheim & Schafer eq 7.133b
         */
        for (int i = 0; i <= r; i++) {
            y[i] = D[Ext[i]] - sign * delta / W[Ext[i]];
            sign = -sign;
        }
    }

    /*********************
     * ComputeA
     *==========
     * Using values calculated in CalcParms, ComputeA calculates the
     * actual filter response at a given frequency (freq).  Uses
     * eq 7.133a from Oppenheim & Schafer.
     *
     *
     * INPUT:
     * ------
     * double freq - Frequency (0 to 0.5) at which to calculate A
     * int    r    - 1/2 the number of filter coefficients
     * double ad[] - 'b' in Oppenheim & Schafer [r+1]
     * double x[]  - [r+1]
     * double y[]  - 'C' in Oppenheim & Schafer [r+1]
     *
     * OUTPUT:
     * -------
     * Returns double value of A[freq]
     *********************/
    double ComputeA(final double freq, final int r, final double ad[], final double x[], final double y[]) {

        double denom = 0;
        double numer = 0;
        double xc = Math.cos(TWO_PI * freq);
        for (int i = 0; i <= r; i++) {
            double c = xc - x[i];
            if (Math.abs(c) < 1.0e-7) {
                numer = y[i];
                denom = 1;
                break;
            }
            c = ad[i] / c;
            denom += c;
            numer += c * y[i];
        }
        return numer / denom;
    }

    /************************
     * CalcError
     *===========
     * Calculates the Error function from the desired frequency response
     * on the dense grid (D[]), the weight function on the dense grid (W[]),
     * and the present response calculation (A[])
     *
     *
     * INPUT:
     * ------
     * int    r      - 1/2 the number of filter coefficients
     * double ad[]   - [r+1]
     * double x[]    - [r+1]
     * double y[]    - [r+1]
     * int gridsize  - Number of elements in the dense frequency grid
     * double Grid[] - Frequencies on the dense grid [gridsize]
     * double D[]    - Desired response on the dense grid [gridsize]
     * double W[]    - Weight function on the desnse grid [gridsize]
     *
     * OUTPUT:
     * -------
     * double E[]    - Error function on dense grid [gridsize]
     ************************/
    void CalcError(final int r, final double ad[], final double x[], final double y[],
            final double Grid[],
            final double D[], final double W[], final double E[]) {

        for (int i = 0; i < Grid.length; i++) {
            final double A = ComputeA(Grid[i], r, ad, x, y);
            E[i] = W[i] * (D[i] - A);
        }
    }

    /************************
     * Search
     *========
     * Searches for the maxima/minima of the error curve.  If more than
     * r+1 extrema are found, it uses the following heuristic (thanks
     * Chris Hanson):
     * 1) Adjacent non-alternating extrema deleted first.
     * 2) If there are more than one excess extrema, delete the
     *    one with the smallest error.  This will create a non-alternation
     *    condition that is fixed by 1).
     * 3) If there is exactly one excess extremum, delete the smaller
     *    of the first/last extremum
     *
     *
     * INPUT:
     * ------
     * int    r        - 1/2 the number of filter coefficients
     * int    Ext[]    - Indexes to Grid[] of extremal frequencies [r+1]
     * int    gridsize - Number of elements in the dense frequency grid
     * double E[]      - Array of error values.  [gridsize]
     * OUTPUT:
     * -------
     * int    Ext[]    - New indexes to extremal frequencies [r+1]
     ************************/
    private void Search(final int r, final int Ext[], final double E[]) {

        /*
         * Allocate enough space for found extremals.
         */
        int[] foundExt = new int[2 * r];
        int k = 0;

        /*
         * Check for extremum at 0.
         */
        if (((E[0] > 0.0) && (E[0] > E[1])) || ((E[0] < 0.0) && (E[0] < E[1]))) {
            foundExt[k++] = 0;
        }

        /*
         * Check for extrema inside dense grid
         */
        for (int i = 1; i < E.length - 1; i++) {
            if (((E[i] >= E[i - 1]) && (E[i] > E[i + 1]) && (E[i] > 0.0))
                    || ((E[i] <= E[i - 1]) && (E[i] < E[i + 1]) && (E[i] < 0.0))) {
                // PAK: we sometimes get too many extremal frequencies
                if (k >= 2 * r) {
                    throw new RuntimeException("remez: too many extremals--cannot continue");
                }
                foundExt[k++] = i;
            }
        }

        /*
         * Check for extremum at 0.5
         */
        int j = E.length - 1;
        if (((E[j] > 0.0) && (E[j] > E[j - 1]))
                || ((E[j] < 0.0) && (E[j] < E[j - 1]))) {
            if (k >= 2 * r) {
                throw new RuntimeException("remez: too many extremals--cannot continue");
            }
            foundExt[k++] = j;
        }

        // PAK: we sometimes get not enough extremal frequencies
        if (k < r + 1) {
            throw new RuntimeException("remez: insufficient extremals--cannot continue");
        }


        /*
         * Remove extra extremals
         */
        int extra = k - (r + 1);
        assert (extra >= 0);

        while (extra > 0) {
            boolean up;
            if (E[foundExt[0]] > 0.0) {
                up = true;                /* first one is a maxima */
            } else {
                up = false;                /* first one is a minima */
            }

            int l = 0;
            boolean alt = true;
            for (j = 1; j < k; j++) {
                if (Math.abs(E[foundExt[j]]) < Math.abs(E[foundExt[l]])) {
                    l = j;               /* new smallest error. */
                }
                if ((up) && (E[foundExt[j]] < 0.0)) {
                    up = false;             /* switch to a minima */
                } else if ((!up) && (E[foundExt[j]] > 0.0)) {
                    up = true;             /* switch to a maxima */
                } else {
                    alt = false;
                    // PAK: break now and you will delete the smallest overall
                    // extremal.  If you want to delete the smallest of the
                    // pair of non-alternating extremals, then you must do:
                    //
                    // if (fabs(E[foundExt[j]]) < fabs(E[foundExt[j-1]])) l=j;
                    // else l=j-1;
                    break;              /* Ooops, found two non-alternating */
                }                      /* extrema.  Delete smallest of them */
            }  /* if the loop finishes, all extrema are alternating */

            /*
             * If there's only one extremal and all are alternating,
             * delete the smallest of the first/last extremals.
             */
            if ((alt) && (extra == 1)) {
                if (Math.abs(E[foundExt[k - 1]]) < Math.abs(E[foundExt[0]])) /* Delete last extremal */ {
                    l = k - 1;
                } // PAK: changed from l = foundExt[k-1];
                else /* Delete first extremal */ {
                    l = 0;
                }
                // PAK: changed from l = foundExt[0];
            }

            for (j = l; j < k - 1; j++) /* Loop that does the deletion */ {
                foundExt[j] = foundExt[j + 1];
                assert (foundExt[j] < E.length);
            }
            k--;
            extra--;
        }

        for (int i = 0; i <= r; i++) {
            assert (foundExt[i] < E.length);
            Ext[i] = foundExt[i];       /* Copy found extremals to Ext[] */
        }

    }

    /*********************
     * FreqSample
     *============
     * Simple frequency sampling algorithm to determine the impulse
     * response h[] from A's found in ComputeA
     *
     *
     * INPUT:
     * ------
     * int      N        - Number of filter coefficients
     * double   A[]      - Sample points of desired response [N/2]
     * int      symmetry - Symmetry of desired filter
     *
     * OUTPUT:
     * -------
     * double h[] - Impulse Response of final filter [N]
     *********************/
    void FreqSample(final double A[]) {

        double M = (numtaps - 1.0) / 2.0;
        if (ftype.symmetry == Symmety.POSITIVE) {
            if (numtaps % 2 != 0) {
                for (int n = 0; n < numtaps; n++) {
                    double val = A[0];
                    double x = TWO_PI * (n - M) / numtaps;
                    for (int k = 1; k <= M; k++) {
                        val += 2.0 * A[k] * Math.cos(x * k);
                    }
                    h[n] = val / numtaps;
                }
            } else {
                for (int n = 0; n < numtaps; n++) {
                    double val = A[0];
                    double x = TWO_PI * (n - M) / numtaps;
                    for (int k = 1; k <= (numtaps / 2 - 1); k++) {
                        val += 2.0 * A[k] * Math.cos(x * k);
                    }
                    h[n] = val / numtaps;
                }
            }
        } else {
            if (numtaps % 2 != 0) {
                for (int n = 0; n < numtaps; n++) {
                    double val = 0;
                    double x = TWO_PI * (n - M) / numtaps;
                    for (int k = 1; k <= M; k++) {
                        val += 2.0 * A[k] * Math.sin(x * k);
                    }
                    h[n] = val / numtaps;
                }
            } else {
                for (int n = 0; n < numtaps; n++) {
                    double val = A[numtaps / 2] * Math.sin(Math.PI * (n - M));
                    double x = TWO_PI * (n - M) / numtaps;
                    for (int k = 1; k <= (numtaps / 2 - 1); k++) {
                        val += 2.0 * A[k] * Math.sin(x * k);
                    }
                    h[n] = val / numtaps;
                }
            }
        }
    }

    /*******************
     * isDone
     *========
     * Checks to see if the error function is small enough to consider
     * the result to have converged.
     *
     * INPUT:
     * ------
     * int    r     - 1/2 the number of filter coeffiecients
     * int    Ext[] - Indexes to extremal frequencies [r+1]
     * double E[]   - Error function on the dense grid [gridsize]
     *
     * OUTPUT:
     * -------
     * Returns 1 if the result converged
     * Returns 0 if the result has not converged
     ********************/
    boolean isDone(final int r, final int Ext[], final double E[]) {
        int i;
        double min, max, current;

        min = max = Math.abs(E[Ext[0]]);
        for (i = 1; i <= r; i++) {
            current = Math.abs(E[Ext[i]]);
            if (current < min) {
                min = current;
            }
            if (current > max) {
                max = current;
            }
        }
        return (((max - min) / max) < 0.0001);
    }

    /********************
     * remez
     *=======
     * Calculates the optimal (in the Chebyshev/minimax sense)
     * FIR filter impulse response given a set of band edges,
     * the desired reponse on those bands, and the weight given to
     * the error in those bands.
     *
     * INPUT:
     * ------
     * int     numtaps     - Number of filter coefficients
     * int     numband     - Number of bands in filter specification
     * double  bands[]     - User-specified band edges [2 * numband]
     * double  des[]       - User-specified band responses [numband]
     * double  weight[]    - User-specified error weights [numband]
     * int     type        - Type of filter
     *
     * OUTPUT:
     * -------
     * double h[]      - Impulse response of final filter [numtaps]
     * returns         - true on success, false on failure to converge
     ********************/
    private void remez() {

        int r = numtaps / 2;                  /* number of extrema */
        if ((numtaps % 2 != 0) && (ftype.symmetry == Symmety.POSITIVE)) {
            r++;
        }

        /*
         * Predict dense grid size in advance for memory allocation
         */
        int gridsize = 0;
        for (int i = 0; i < numbands; i++) {
            gridsize += (int) Math.round(2 * r * griddensity * (bands[2 * i + 1] - bands[2 * i]));
        }
        if (ftype.symmetry == Symmety.NEGATIVE) {
            gridsize--;
        }

        /*
         * Dynamically allocate memory for arrays with proper sizes
         */
        double[] Grid = new double[gridsize];
        double[] D = new double[gridsize];
        double[] W = new double[gridsize];
        double[] E = new double[gridsize];

        double[] taps = new double[r + 1];
        double[] x = new double[r + 1];
        double[] y = new double[r + 1];
        double[] ad = new double[r + 1];

        /*
         * Create dense frequency grid
         */
        CreateDenseGrid(r, Grid, D, W);
        int[] Ext = InitialGuess(r, gridsize);

        /*
         * For Differentiator: (fix grid)
         */
        if (ftype == FType.DIFFERENTIATOR) {
            for (int i = 0; i < gridsize; i++) {
                /* D[i] = D[i]*Grid[i]; */
                if (D[i] > 0.0001) {
                    W[i] = W[i] / Grid[i];
                }
            }
        }

        /*
         * For odd or Negative symmetry filters, alter the
         * D[] and W[] according to Parks McClellan
         */
        if (ftype.symmetry == Symmety.POSITIVE) {
            if (numtaps % 2 == 0) {
                for (int i = 0; i < gridsize; i++) {
                    final double c = Math.cos(Math.PI * Grid[i]);
                    D[i] /= c;
                    W[i] *= c;
                }
            }
        } else {
            if (numtaps % 2 != 0) {
                for (int i = 0; i < gridsize; i++) {
                    final double c = Math.sin(TWO_PI * Grid[i]);
                    D[i] /= c;
                    W[i] *= c;
                }
            } else {
                for (int i = 0; i < gridsize; i++) {
                    final double c = Math.sin(Math.PI * Grid[i]);
                    D[i] /= c;
                    W[i] *= c;
                }
            }
        }

        /*
         * Perform the Remez Exchange algorithm
         * APL FEHLER  for(int idx=0; idx <= r; i++ ????
         */
        for (int iter = 0; iter < MAXITERATIONS; iter++) {
            CalcParms(r, Ext, Grid, D, W, ad, x, y);
            CalcError(r, ad, x, y, Grid, D, W, E);
            Search(r, Ext, E);

            for (int idx = 0; idx <= r; idx++) {
                assert (Ext[idx] < gridsize);
            }

            if (isDone(r, Ext, E)) {
                break;
            }
        }

        CalcParms(r, Ext, Grid, D, W, ad, x, y);

        /*
         * Find the 'taps' of the filter for use with Frequency
         * Sampling.  If odd or Negative symmetry, fix the taps
         * according to Parks McClellan
         */
        for (int i = 0; i <= numtaps / 2; i++) {
            double c;
            if (ftype.symmetry == Symmety.POSITIVE) {
                if (numtaps % 2 != 0) {
                    c = 1;
                } else {
                    c = Math.cos(Math.PI * (double) i / numtaps);
                }
            } else {
                if (numtaps % 2 != 0) {
                    c = Math.sin(TWO_PI * (double) i / numtaps);
                } else {
                    c = Math.sin(Math.PI * (double) i / numtaps);
                }
            }
            taps[i] = ComputeA((double) i / numtaps, r, ad, x, y) * c;
        }

        /*
         * Frequency sampling design with calculated taps
         */
        FreqSample(taps);
    }
}
