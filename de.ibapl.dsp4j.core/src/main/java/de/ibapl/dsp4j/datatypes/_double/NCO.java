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

import de.ibapl.dsp4j.In;
import de.ibapl.dsp4j.Out;
import de.ibapl.dsp4j.SampledBlock;

/**
 *
 * @author aploese
 */
public interface NCO extends SampledBlock {

    @In
    void setX(double error);

    /**
     * @return the phi0
     */
    double getPhi();

    /**
     * @param phi0 the phi0 to set
     */
    void setPhi(double phi);

    @Out
    double getCosY();

    /**
     * @param alpha the alpha to set
     * beta will be calculated
     */
    void setAlpha(double alpha);

    /**
     * @return the deltaPhi
     */
    double getDeltaPhi();

    /**
     * @return the alpha
     */
    double getAlpha();

    /**
     * @return the sinY
     */
    double getSinY();

    /**
     * @return the f0
     */
    double getF0();

    /**
     * @param f0 the f0 to set
     */
    void setF0(double f0);

}
