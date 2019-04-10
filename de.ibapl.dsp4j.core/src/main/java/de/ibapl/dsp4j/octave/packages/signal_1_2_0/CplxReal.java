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
package de.ibapl.dsp4j.octave.packages.signal_1_2_0;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public class CplxReal {

    private List<Complex> conjComplxPair;
    private List<Complex> realValues;
    
    public static boolean checkIsConjugate(Complex c1, Complex c2, double delta) {
        return Math.abs(c1.getReal() - c2.getReal()) <= delta && Math.abs(c1.getImaginary() + c2.getImaginary()) <= delta;
    }

    public static boolean checkIsReal(Complex c, double delta) {
        return Math.abs(c.getImaginary()) <= delta;
    }
    
    public void cplxReal(double delta, Complex... in) {
        conjComplxPair = new ArrayList(in.length / 2);
        realValues = new ArrayList();

        List<Complex> listValues = new ArrayList(in.length / 2);

        for (Complex c : in) {
            if (checkIsReal(c, delta)) {
                addReal(c);
            } else {
                boolean found = false;
                for (Complex listValue : listValues) {
                    if (checkIsConjugate(listValue, c, delta)) {
                        listValues.remove(listValue);
                        if (c.getImaginary() > 0.0) {
                            addComplexConjugated(c);
                            found = true;
                            break;
                        } else {
                            addComplexConjugated(listValue);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    listValues.add(c);
                }
            }
        }
        if (!listValues.isEmpty()) {
            throw new RuntimeException("sibling(s) of complex pair(s) missing");
        }
    }

    private void addReal(Complex c) {
        for (int i = 0; i < realValues.size(); i++) {
            if (realValues.get(i).getReal() > c.getReal()) {
                realValues.add(i, c);
                return;
            }
        }
        realValues.add(c);
    }

    private void addComplexConjugated(Complex c) {
        for (int i = 0; i < conjComplxPair.size(); i++) {
            if (conjComplxPair.get(i).getReal() > c.getReal()) {
                conjComplxPair.add(i, c);
                return;
            }
        }
        conjComplxPair.add(c);
    }

    /**
     * @return the conjComplxPair
     */
    public Complex[] getConjComplxPair() {
        return conjComplxPair.toArray(new Complex[conjComplxPair.size()]);
    }

    /**
     * @return the realValues
     */
    public Complex[] getRealValues() {
        return realValues.toArray(new Complex[realValues.size()]);
    }
}
