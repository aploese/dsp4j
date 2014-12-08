/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.datatypes._double.iirfilter.GenericDirectDoubleIirFilter;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aploese
 */
public class GenericFilterTest {

    public GenericFilterTest() {
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
     * Test of setX method, of class AbstractDoubleFilter.
     */
    @Test
    public void testSetX() throws Exception {
        System.out.println("setX");
        GenericDirectDoubleIirFilter filter = new GenericDirectDoubleIirFilter(new double[]{1, 2, 3}, new double[]{4, 5, 6});
        filter.setX(1);
        assertEquals(4, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(1, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(1, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(10, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(-8, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(1, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(37, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(-62, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(28, filter.getY(), Double.MIN_VALUE);
        filter.setX(1);
        assertEquals(145, filter.getY(), Double.MIN_VALUE);

        assertArrayEquals(new double[]{-363, -429}, filter.getSi(), 0.01);
    }

}