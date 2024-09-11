/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) 2019-2024, Arne Plöse and individual contributors as indicated
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

import de.ibapl.dsp4j.VisualResultCheckTest;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author aploese
 */
public class CostasLoopTest extends VisualResultCheckTest {

    final private static Logger LOG = Logger.getLogger(CostasLoopTest.class.getCanonicalName());

    public CostasLoopTest() {
    }

    /**
     * Test of setSampleRate method, of class CostasLoop.
     */
    @Test
    public void testSetSampleRate() {
        System.out.println("setSampleRate");
        CostasLoop instance = new CostasLoop(new GenericNCO2ndOrder(1000, 1500, 2000), new GenericAtan(), 1200);
        instance.setSampleRate(44100);
        assertEquals(44100, instance.getSampleRate(), Double.MIN_VALUE);
    }

    private static short scale(double d) {
        return (short) (d * Short.MAX_VALUE);
    }

}
