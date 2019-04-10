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

import de.ibapl.dsp4j.AbstractSampleProcessingBlock;
import static de.ibapl.dsp4j.DspConst.TWO_PI;
import de.ibapl.dsp4j.In;

/**
 *
 * @author aploese
 */
public class LPll extends AbstractSampleProcessingBlock {

    //State of the pll
    private double u2_n;
    private double phi2_n;
    private double ud_n_M_1;
    private double uf_n_M_1;
    
    
    //Config of the pll
    private double kd;
    private double k0;
    private double a1;
    private double b0;
    private double b1;
    private double w0;
    private double T;

    @In
    public double setX(double sample) {
        final double ud_n = kd * sample * u2_n;
        final double uf_n = -a1 * uf_n_M_1 + b0 * ud_n + b1 * ud_n_M_1;
        double phi2_n_P_1 = phi2_n + (w0 + k0 * uf_n) * T;
        if (phi2_n_P_1 > Math.PI) {
            phi2_n_P_1 -= TWO_PI;
        }
        double u2_n_P_1;
        if (phi2_n_P_1 > 0) {
            u2_n_P_1 = 1;
        } else {
            u2_n_P_1 = -1;
        }
        ud_n_M_1 = ud_n;
        uf_n_M_1 = uf_n;
        phi2_n = phi2_n_P_1;
        u2_n = u2_n_P_1;
        return uf_n;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        T = 1 / sampleRate;
        //TODO use samplerate ???
        super.setSampleRate(sampleRate);
    }

    @Override
    public void reset() {
        u2_n = 0;
        phi2_n = 0;
        ud_n_M_1 = 0;
        uf_n_M_1 = 0;
    }

}
