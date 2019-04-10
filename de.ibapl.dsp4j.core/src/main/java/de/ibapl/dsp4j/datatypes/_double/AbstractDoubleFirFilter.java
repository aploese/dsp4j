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
package de.ibapl.dsp4j.datatypes._double;

import java.util.Arrays;
import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;

/**
 *
 * @author aploese
 */
public abstract class AbstractDoubleFirFilter extends AbstractSampleProcessingBlock  {

    private double[] b;
    private double[] si;
    private double y;

    protected void setCoeff(double[] b) {
        this.b = b;
        si = new double[b.length - 1];
    }

    @In
    public double setX(double x) {
         y = b[0] * x + si[0];
        for (int i = 0; i < si.length - 1; i++) {
            si[i] = b[i + 1] * x + si[i + 1];
        }
        si[si.length - 1] = b[b.length - 1] * x;

        return y;
    }
    
    @Out
    public double getY() {
        return y;
    }

    public double[] getB() {
        return Arrays.copyOf(b, b.length);
    }

    public double[] getSi() {
        return Arrays.copyOf(si, si.length);
    }

    @Override
    public void reset() {
       for (int i = 0; i < si.length; i++) {
           si[i] = 0;
       }
       super.reset();
    }

}
