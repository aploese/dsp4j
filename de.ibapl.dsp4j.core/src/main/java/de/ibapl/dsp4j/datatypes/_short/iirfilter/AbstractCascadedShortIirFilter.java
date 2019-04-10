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
import de.ibapl.dsp4j.Resettable;

/**
 *
 * @author aploese
 */
public abstract class AbstractCascadedShortIirFilter implements CascadedShortIirFilter {

    protected interface BiQuad extends Resettable {

        int doCalc(int x);
    }

    private static class FirstOrderBiQuad implements BiQuad {

        private final int a1;
        private final int b0;
        private final int b1;
        private int si0;

        private FirstOrderBiQuad(int a1, int b0, int b1) {
            this.a1 = a1;
            this.b0 = b0;
            this.b1 = b1;
        }

        @Override
        public int doCalc(int x) {
            final int y = (b0 * x + si0) >> Q_DOT_15_EXP;
            si0 = b1 * x - a1 * y;
            return y;
        }

        @Override
        public void reset() {
            si0 = 0;
        }
    }

    private static class SecondOrderBiQuad implements BiQuad {

        private final int a1;
        private final int a2;
        private final int b0;
        private final int b1;
        private final int b2;
        private int si0;
        private int si1;

        private SecondOrderBiQuad(int a1, int a2, int b0, int b1, int b2) {
            this.a1 = a1;
            this.a2 = a2;
            this.b0 = b0;
            this.b1 = b1;
            this.b2 = b2;
        }

        @Override
        public int doCalc(int x) {
            final int y = (b0 * x + si0) >> Q_DOT_15_EXP;
            si0 = b1 * x - a1 * y + si1;
            si1 = b2 * x - a2 * y;
            return y;
        }

        @Override
        public void reset() {
            si0 = 0;
            si1 = 0;
        }
    }

    protected final BiQuad[] biquads;
    protected int y;

    private BiQuad createBiQuad(int a0, int a1, int a2, int b0, int b1, int b2) {
        if (a0 != 1) {
            throw new IllegalArgumentException("a[0] must be 1");
        }
        if (b2 == 0 && a2 == 0) {
            // FirstOrder
            return new FirstOrderBiQuad(a1, b0, b1);
        } else {
            // SecondOrder
            return new SecondOrderBiQuad(a1, a2, b0, b1, b2);
        }
    }

    public AbstractCascadedShortIirFilter(double[][] sos, double gain) {
        biquads = new BiQuad[sos.length];
        for (int i = 0; i < biquads.length; i++) {
            biquads[i] = createBiQuad(
                    (int)Math.round(sos[i][3] * Q_DOT_15_VALUE), 
                    (int)Math.round(sos[i][4] * Q_DOT_15_VALUE), 
                    (int)Math.round(sos[i][5] * Q_DOT_15_VALUE), 
                    (int)Math.round(sos[i][0] * gain * Q_DOT_15_VALUE), 
                    (int)Math.round(sos[i][1] * gain * Q_DOT_15_VALUE), 
                    (int)Math.round(sos[i][2] * gain * Q_DOT_15_VALUE));
        }
    }

    @In
    @Override
    public abstract void setX(int sample);

    @Out
    @Override
    public int getY() {
        return y;
    }

    @Override
    public void reset() {
        for (BiQuad b: biquads) {
            b.reset();
        }
    }
}
