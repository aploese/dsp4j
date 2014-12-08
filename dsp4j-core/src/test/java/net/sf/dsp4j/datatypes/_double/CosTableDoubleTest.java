package net.sf.dsp4j.datatypes._double;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static net.sf.dsp4j.DspConst.HALF_PI;
import static org.junit.Assert.*;


/**
 *
 * @author aploese
 */
public class CosTableDoubleTest {

    public CosTableDoubleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
            assertEquals("i: " + i, Math.cos(0.1 * i), instance.cos(0.1 * i), delta);
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
            assertEquals("i: " + i, Math.sin(0.1 * i), instance.sin(0.1 * i), delta);
        }
    }

}