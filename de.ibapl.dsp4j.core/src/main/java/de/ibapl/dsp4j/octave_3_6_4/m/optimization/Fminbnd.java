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

import java.util.Collections;
import java.util.Map;
import de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn;

/*
 ## Copyright (C) 2008-2012 VZLU Prague, a.s.
 ##
 ## This file is part of Octave.
 ##
 ## Octave is free software; you can redistribute it and/or modify it
 ## under the terms of the GNU General Public License as published by
 ## the Free Software Foundation; either version 3 of the License, or (at
 ## your option) any later version.
 ##
 ## Octave is distributed in the hope that it will be useful, but
 ## WITHOUT ANY WARRANTY; without even the implied warranty of
 ## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 ## General Public License for more details.
 ##
 ## You should have received a copy of the GNU General Public License
 ## along with Octave; see the file COPYING.  If not, see
 ## <http://www.gnu.org/licenses/>.
 ##
 ## Author: Jaroslav Hajek <highegg@gmail.com>
 */
/**
 * ## -*- texinfo -*- ##
 *
 * @deftypefn {Function File} {[
 * @var{x},
 * @var{fval},
 * @var{info},
 * @var{output}] =} fminbnd (
 * @var{fun},
 * @var{a},
 * @var{b},
 * @var{options}) ## Find a minimum point of a univariate function.
 * @var{fun} should be a ## function ## handle or name.
 * @var{a},
 * @var{b} specify a starting interval.
 * @var{options} ## is a ## structure specifying additional options. Currently, @code{fminbnd}
 * ## recognizes these options: @code{"FunValCheck"}, @code{"OutputFcn"},
 * ## @code{"TolX"}, @code{"MaxIter"}, @code{"MaxFunEvals"}.
 * ## For description of these options, see @ref{doc-optimset,,optimset}.
 * ##
 * ## On exit, the function returns @var{x}, the approximate minimum point
 * ## and @var{fval}, the function value thereof.
 * ## @var{info} is an exit flag that can have these values:
 * ##
 * ## @itemize
 * ## @item 1
 * ## The algorithm converged to a solution.
 * ##
 * ## @item 0
 * ## Maximum number of iterations or function evaluations has been exhausted.
 * ##
 * ## @item -1
 * ## The algorithm has been terminated from user output function.
 * ## @end itemize
 * ## @seealso{optimset, fzero, fminunc}
 * ## @end deftypefn
 *
 * ## This is patterned after opt/fmin.f from Netlib, which in turn is taken from
 * ## Richard Brent: Algorithms For Minimization Without Derivatives, Prentice-Hall (1973)
 *
 * ## PKG_ADD: ## Discard result to avoid polluting workspace with ans at startup.
 * ## PKG_ADD: [~] = __all_opts__ ("fminbnd");
 */
public class Fminbnd {

    private double x;
    private double fval;
    private double info;
    private int outputIterations;
    private int outputFuncCount;
    private double outputBracketA;
    private double outputBracketB;

    public Fminbnd() {
        
    }
    
    public double fminbnd(FunctionWrapper fun, double xmin, double xmax) {
        return fminbnd(fun, xmin, xmax, Collections.EMPTY_MAP);
    }
    
    public double fminbnd(FunctionWrapper fun, double xmin, double xmax, Map<Opti, Object> options) {
//    , options = struct ()

// Get default options if requested.
//  if (nargin == 1 && ischar (fun) && strcmp (fun, 'defaults'))
//    x = optimset ("MaxIter", Inf, "MaxFunEvals", Inf, "TolX", 1e-8, \
//    "OutputFcn", [], "FunValCheck", "off");
//    return;
//  endif
//
//  if (nargin < 2 || nargin > 4)
//    print_usage ();
//  endif
//
//  if (ischar (fun))
//    fun = str2func (fun, "global");
//  endif

        // TODO
        boolean displev = Opti.optimget(options, Opti.DISPLAY, true); // was "notify"
        boolean funvalchk = Opti.optimget(options, Opti.FUN_VAL_CHECK, false);
        Object outfcn = Opti.optimget(options, Opti.OUTPUT_FCN);
        double tolx = Opti.optimget(options, Opti.TOL_X, 1e-8);
        long maxiter = Opti.optimget(options, Opti.MAX_ITER, Long.MAX_VALUE); // was Inf
        long maxfev = Opti.optimget(options, Opti.MAX_FUN_EVAL, Long.MAX_VALUE); //was  Inf;

        // The default exit flag if exceeded number of iterations.
        int info = 0;
        int niter = 0;
        int nfev = 0;
        double sqrteps = OctaveBuildIn.EPS;

        double c = 0.5 * (3 - Math.sqrt(5));
        double a = xmin;
        double b = xmax;
        double v = a + c * (b - a);
        x = v;
        double w = x;
        double e = 0;
        fval = fun.fun(x);
        double fv = fval;
        double fw = fval;
        nfev++;

        double d = 0;
        while (niter < maxiter && nfev < maxfev) {
            double xm = 0.5 * (a + b);
            // FIXME: the golden section search can actually get closer than sqrt(eps)...
            // sometimes. Sometimes not, it depends on the function. This is the strategy
            // from the Netlib code. Something yet smarter would be good.
            double tol = 2 * sqrteps * Math.abs(x) + tolx / 3;
            if (Math.abs(x - xm) <= (2 * tol - 0.5 * (b - a))) {
                info = 1;
                break;
            }
            boolean dogs;
            if (Math.abs(e) > tol) {
                dogs = false;
                // Try inverse parabolic step.
                double r = (x - w) * (fval - fv);
                double q = (x - v) * (fval - fw);
                double p = (x - v) * q - (x - w) * r;
                q = 2 * (q - r);
                p *= -Math.signum(q);
                q = Math.abs(q);
                r = e;
                e = d;

                if (Math.abs(p) < Math.abs(0.5 * q * r) && p > q * (a - x) && p < q * (b - x)) {
                    // The parabolic step is acceptable.
                    d = p / q;
                    double u = x + d;

                    // f must not be evaluated too close to ax or bx.
                    if (Math.min(u - a, b - u) < 2 * tol) {
                        d = tol * (Math.signum(xm - x) + (xm == x ? 1 : 0));
                    }
                } else {
                    dogs = true;
                }
            } else {
                dogs = true;
            }
            if (dogs) {
                // Default to golden section step.
                e = x >= xm ? a - x : b - x;
                d = c * e;
            }

            // f must not be evaluated too close to x.
            double u = x + Math.max(Math.abs(d), tol) * (Math.signum(d) + (d == 0 ? 1 : 0));

            double fu = fun.fun(u);
            if (funvalchk) {
                if (Double.isNaN(fu)) {
                    throw new RuntimeException("fminbnd:isnan fminbnd: NaN value encountered");
                }
            }

            nfev++;
            niter++;

            // update  a, b, v, w, and x

            if (fu <= fval) {
                if (u < x) {
                    b = x;
                } else {
                    a = x;
                }
                v = w;
                fv = fw;
                w = x;
                fw = fval;
                x = u;
                fval = fu;
            } else {
                // The following if-statement was originally executed even if fu == fval.
                if (u < x) {
                    a = u;
                } else {
                    b = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    fv = fw;
                    w = u;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }

            // If there's an output function, use it now.
            //TODO
//    if (outfcn) {
//      optv.funccount = nfev;
//      optv.fval = fval;
//      optv.iteration = niter;
//      if (outfcn (x, optv, "iter")) {
//        info = -1;
//        break;
//      }
//    }
        }

        outputIterations = niter;
        outputFuncCount = nfev;
        outputBracketA = a;
        outputBracketB = b;
        return x;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the fval
     */
    public double getFval() {
        return fval;
    }

    /**
     * @return the info
     */
    public double getInfo() {
        return info;
    }

    /**
     * @return the outputIterations
     */
    public int getOutputIterations() {
        return outputIterations;
    }

    /**
     * @return the outputFuncCount
     */
    public int getOutputFuncCount() {
        return outputFuncCount;
    }

    /**
     * @return the outputBracketA
     */
    public double getOutputBracketA() {
        return outputBracketA;
    }

    /**
     * @return the outputBracketB
     */
    public double getOutputBracketB() {
        return outputBracketB;
    }
}