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
import de.ibapl.dsp4j.Resettable;

/**
 *
 * @author aploese
 */
public abstract class AbstractCascadedDoubleIirFilter implements CascadedDoubleIirFilter {

    protected interface BiQuad extends Resettable {

        double doCalc(double x);
    }

    private final static class FirstOrderBiQuad implements BiQuad {

        private final double a1;
        private final double b0;
        private final double b1;
        private double si0;

        private FirstOrderBiQuad(double a1, double b0, double b1) {
            this.a1 = a1;
            this.b0 = b0;
            this.b1 = b1;
        }

        @Override
        public final double doCalc(double x) {
            final double y = b0 * x + si0;
            si0 = b1 * x - a1 * y;
            return y;
        }

        @Override
        public void reset() {
            si0 = 0;
        }
    }

    private final static class SecondOrderBiQuad implements BiQuad {

        private final double a1;
        private final double a2;
        private final double b0;
        private final double b1;
        private final double b2;
        private double si0;
        private double si1;

        private SecondOrderBiQuad(double a1, double a2, double b0, double b1, double b2) {
            this.a1 = a1;
            this.a2 = a2;
            this.b0 = b0;
            this.b1 = b1;
            this.b2 = b2;
        }

        @Override
        public final double doCalc(double x) {
            final double y = b0 * x + si0;
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
    protected double y;

    private BiQuad createBiQuad(double a0, double a1, double a2, double b0, double b1, double b2) {
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

    public AbstractCascadedDoubleIirFilter(double[][] sos, double gain) {
        biquads = new BiQuad[sos.length];
        for (int i = 0; i < biquads.length; i++) {
            biquads[i] = createBiQuad(sos[i][3], sos[i][4], sos[i][5], sos[i][0] * gain, sos[i][1] * gain, sos[i][2] * gain);
        }
    }

    @In
    @Override
    public abstract void setX(double sample);

    @Out
    @Override
    public final double getY() {
        return y;
    }

    @Override
    public void reset() {
        for (BiQuad b: biquads) {
            b.reset();
        }
    }
}
