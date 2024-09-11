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

import static de.ibapl.dsp4j.DspConst.HALF_PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author aploese
 */
public class CosTableDoubleTest {

    public CosTableDoubleTest() {
    }

    /**
     * Test of cosPi method, of class CosTableDouble.
     */
    @Test
    public void testCos() {
        System.out.println("cos");
        final double delta = 0.001;
        CosTableDouble instance = CosTableDouble.getTableFor(delta);
        assertEquals(1.0, instance.cos(0.0), delta);
        assertEquals(0.0, instance.cos(HALF_PI), delta);
        assertEquals(-1.0, instance.cos(Math.PI), delta);
        assertEquals(0.0, instance.cos(Math.PI * 3 / 2), delta);
        for (int i = -100; i < 100; i++) {
            assertEquals(Math.cos(0.1 * i), instance.cos(0.1 * i), delta, "i: " + i);
        }
    }

    /**
     * Test of sinPi method, of class CosTableDouble.
     */
    @Test
    public void testSin() {
        System.out.println("sin");
        final double delta = 0.001;
        CosTableDouble instance = CosTableDouble.getTableFor(delta);
        assertEquals(0.0, instance.sin(0.0), delta);
        assertEquals(1.0, instance.sin(Math.PI / 2), delta);
        assertEquals(0.0, instance.sin(Math.PI), delta);
        assertEquals(-1.0, instance.sin(Math.PI * 3 / 2), delta);
        for (int i = -100; i < 100; i++) {
            assertEquals(Math.sin(0.1 * i), instance.sin(0.1 * i), delta, "i: " + i);
        }
    }

}
