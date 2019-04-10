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
package de.ibapl.dsp4j.datatypes._int;

import java.util.Arrays;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class GenericDirectIntIirFilter implements DirectIntIirFilter<GenericDirectIntIirFilter> {

    private int[] a;
    private int[] b;
    private int[] si;
    private int y;
    public final static int Q_DOT_31 = 31;

    GenericDirectIntIirFilter() {
    }

    public GenericDirectIntIirFilter setCoeff(int[] a, int[] b) {
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
            si = new int[a.length - 1];
        } else {
            si = new int[1];
        }
        return this;
    }

    @In
    @Override
    public int setX(int x) {
        y = (int)(((long)b[0] * x) >> Q_DOT_31) + si[0];
        for (int i = 0; i < si.length - 1; i++) {
            si[i] = (int)(((long)b[i + 1] * x) >> Q_DOT_31) - (int)(((long)a[i + 1] * y) >> Q_DOT_31) + si[i + 1];
        }
        si[si.length - 1] = (int)(((long)b[b.length - 1] * x) >> Q_DOT_31) - (int)(((long)a[a.length - 1] * y) >> Q_DOT_31);
        return y;
    }

    @Out
    @Override
    public int getY() {
        return y;
    }

    @Override
    public int[] getA() {
        return Arrays.copyOf(a, a.length);
    }

    @Override
    public int[] getB() {
        return Arrays.copyOf(b, b.length);
    }

    @Override
    public int[] getSi() {
        return Arrays.copyOf(si, si.length);
    }

    @Override
    public void reset() {
       for (int i = 0; i < si.length; i++) {
           si[i] = 0;
       }
    }

}
