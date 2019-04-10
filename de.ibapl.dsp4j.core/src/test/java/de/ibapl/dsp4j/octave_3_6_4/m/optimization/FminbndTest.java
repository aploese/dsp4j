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
package de.ibapl.dsp4j.octave_3_6_4.m.optimization;

import java.util.EnumMap;
import java.util.Map;
import de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author aploese
 */
public class FminbndTest {
    
    public FminbndTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCos() {
        Map<Opti, Object> opt0 = new EnumMap(Opti.class);
    
    opt0.put(Opti.TOL_X, 0.0);
    Fminbnd f = new Fminbnd();
    f.fminbnd(new FunctionWrapper() {

            @Override
            public double fun(double x) {
                return Math.cos(x);
            }
        }, Math.PI/2, 3*Math.PI/2, opt0);
    assertEquals(Math.PI, f.getX(), 10*Math.sqrt(OctaveBuildIn.EPS));
    }

    @Test
    @Ignore
    //TODO
    public void testSin() {
        Map<Opti, Object> opt0 = new EnumMap(Opti.class);
    
    opt0.put(Opti.TOL_X, 0.0);
    Fminbnd f = new Fminbnd();
    f.fminbnd(new FunctionWrapper() {

            @Override
            public double fun(double x) {
                return Math.pow(x, 2) + Math.sin(2*Math.PI*x);
            }
        }, Math.PI/2, 3*Math.PI/2, opt0);
    assertEquals(0.4, f.getX(), 10*Math.sqrt(OctaveBuildIn.EPS));
//%!assert (fminbnd (@(x) (x - 1e-3)^4, -1, 1, opt0), 1e-3, 10e-3*sqrt(eps))
//%!assert (fminbnd (@(x) abs(x-1e7), 0, 1e10, opt0), 1e7, 10e7*sqrt(eps))
//%!assert (fminbnd (@(x) , 0.4, 1, opt0), fzero (@(x) 2*x + 2*pi*cos(2*pi*x), [0.4, 1], opt0), sqrt(eps))
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}