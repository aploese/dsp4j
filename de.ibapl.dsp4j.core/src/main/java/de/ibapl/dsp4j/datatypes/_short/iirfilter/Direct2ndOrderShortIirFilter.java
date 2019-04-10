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
package de.ibapl.dsp4j.datatypes._short.iirfilter;

import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public class Direct2ndOrderShortIirFilter implements DirectShortIirFilter {

    private final int a1;
    private final int a2;
    private final int b0;
    private final int b1;
    private final int b2;
    private int si0;
    private int si1;
    private int y;

    public Direct2ndOrderShortIirFilter(double [] a, double [] b) {
        if (a[0] != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        this.a1 = (int)Math.round(a[1] * Q_DOT_15_VALUE);
        this.a2 = (int)Math.round(a[2] * Q_DOT_15_VALUE);
        this.b0 = (int)Math.round(b[0] * Q_DOT_15_VALUE);
        this.b1 = (int)Math.round(b[1] * Q_DOT_15_VALUE);
        this.b2 = (int)Math.round(b[2] * Q_DOT_15_VALUE);
    }

    @Override
    @In
    public void setX(int x) {
        y = (b0 * x + si0) >> Q_DOT_15_EXP;
        si0 = b1 * x - a1 * y + si1;
        si1 = b2 * x - a2 * y;
    }

    @Override
    @Out
    public int getY() {
        return y;
    }

    @Override
    public int [] getA() {
        return new int[]{1, a1, a2};
    }

    @Override
    public int [] getB() {
        return new int[]{b0, b1, b2};
    }

    @Override
    public int [] getSi() {
        return new int[]{si0, si1};
    }

    @Override
    public void reset() {
        si0 = 0;
        si1 = 0;
    }

}