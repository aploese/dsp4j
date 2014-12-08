package net.sf.dsp4j.datatypes._int;

import net.sf.dsp4j.VisualResultCheckTest;
import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.datatypes._double.iirfilter.GenericDirectDoubleIirFilter;
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
public class IntIirFilterTest extends VisualResultCheckTest {

    public IntIirFilterTest() {
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
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    @Ignore
    public void testFilter() throws Exception {
        System.out.println("setX");

        IntIirFilterGenerator genInt = new IntIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        DirectIntIirFilter filterInt = genInt.getLP_ButterFc(1, 1000, DirectIntIirFilter.class);
        DirectDoubleIirFilter filterDouble = genDouble.getLP_ButterFc(1, 1000, DirectDoubleIirFilter.class);

        filterDouble.setX(1024);
        filterInt.setX(1024);
        assertEquals("y", filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
        filterDouble.setX(1024);
        filterInt.setX(1024);
        assertEquals(filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);


    }

    @Test
    @Ignore
    public void testButterLP1stOrder() throws Exception {
        System.out.println("setX");

        createFile("test", 8000, 3);
        IntIirFilterGenerator genInt = new IntIirFilterGenerator(8000);
        DoubleIirFilterGenerator genDouble = new DoubleIirFilterGenerator(8000);

        DirectIntIirFilter filterInt = genInt.getLP_ButterFc(1, 100, DirectIntIirFilter.class);
        DirectDoubleIirFilter filterDouble = genDouble.getLP_ButterFc(1, 100, DirectDoubleIirFilter.class);
        
        for (int i = 0; i < 8000; i++) {
            filterDouble.setX(Short.MAX_VALUE);
            filterInt.setX(Short.MAX_VALUE);
//            filterInt.setX(Q.Q_15.mul - 1);
//            assertEquals("@Index: " + i, filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
            if (isShowResult()) {
                sfs.setX((short) (1024),
                        (short) (filterDouble.getY()),
                        (short) (filterInt.getY()));
            }
        }
        for (int i = 0; i < 8000; i++) {
            filterDouble.setX(0);
            filterInt.setX(0);
//            assertEquals("@Index: " + i, filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
            if (isShowResult()) {
                sfs.setX((short) 0,
                        (short) (filterDouble.getY()),
                        (short) (filterInt.getY()));
            }
        }
        for (int i = 0; i < 8000; i++) {
            filterDouble.setX(Short.MIN_VALUE);
            filterInt.setX(Short.MIN_VALUE);
//            assertEquals("@Index: " + i, filterDouble.getY(), filterInt.getY(), Double.MIN_VALUE);
            if (isShowResult()) {
                sfs.setX(Short.MIN_VALUE,
                        (short) (filterDouble.getY()),
                        (short) (filterInt.getY()));
            }
        }
    }
}
