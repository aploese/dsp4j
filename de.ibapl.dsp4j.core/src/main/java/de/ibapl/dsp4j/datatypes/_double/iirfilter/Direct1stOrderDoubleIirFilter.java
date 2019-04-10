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
package de.ibapl.dsp4j.datatypes._double.iirfilter;

import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public final class Direct1stOrderDoubleIirFilter implements DirectDoubleIirFilter {

    private final double a1;
    private final double b0;
    private final double b1;
    private double si0;
    private double y;

    public Direct1stOrderDoubleIirFilter(double[] a, double[] b) {
        if (a[0] != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        this.a1 = a[1];
        this.b0 = b[0];
        this.b1 = b[1];
    }

    @Override
    @In
    public final void setX(final double x) {
        y = b0 * x + si0;
        si0 = b1 * x - a1 * y;
    }

    @Override
    @Out
    public final double getY() {
        return y;
    }

    @Override
    public double[] getA() {
        return new double[]{1, a1};
    }

    @Override
    public double[] getB() {
        return new double[]{b0, b1};
    }

    @Override
    public double[] getSi() {
        return new double[]{si0};
    }

    @Override
    public void reset() {
        si0 = 0;
    }

}