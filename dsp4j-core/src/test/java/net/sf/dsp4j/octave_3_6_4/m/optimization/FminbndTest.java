/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.octave_3_6_4.m.optimization;

import java.util.EnumMap;
import java.util.Map;
import net.sf.dsp4j.octave_3_2_4.OctaveBuildIn;
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