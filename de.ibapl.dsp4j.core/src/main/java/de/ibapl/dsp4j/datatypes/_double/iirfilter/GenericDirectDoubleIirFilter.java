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

import java.util.Arrays;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public final class GenericDirectDoubleIirFilter implements DirectDoubleIirFilter {

    private final double[] a;
    private final double[] b;
    private double[] si;
    private double y;

    public GenericDirectDoubleIirFilter(double[] a, double[] b) {
        if (a[0] != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        if (a.length == b.length) {
        this.a = a;
        this.b = b;
        } else if (a.length < b.length) {
            this.a = Arrays.copyOf(a, b.length);
            this.b = b;
        } else {
            this.a = a;
            this.b = Arrays.copyOf(b, a.length);
        }
        if (a.length > 1) {
            si = new double[a.length - 1];
        } else {
            si = new double[1];
        }
    }

    @In
    @Override
    public final void setX(final double x) {
        y = b[0] * x + si[0];
        for (int i = 0; i < si.length - 1; i++) {
            si[i] = (b[i + 1] * x - a[i + 1] * y) + si[i + 1];
        }
        si[si.length - 1] = b[b.length - 1] * x - a[a.length - 1] * y;
    }

    @Out
    @Override
    public final double getY() {
        return y;
    }

    @Override
    public double[] getA() {
        return Arrays.copyOf(a, a.length);
    }

    @Override
    public double[] getB() {
        return Arrays.copyOf(b, b.length);
    }

    @Override
    public double[] getSi() {
        return Arrays.copyOf(si, si.length);
    }

    @Override
    public void reset() {
       for (int i = 0; i < si.length; i++) {
           si[i] = 0;
       }
    }

}
