package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
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
public class ButterworthDoubleFilterTest {

    public ButterworthDoubleFilterTest() {
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

    @Test
    @Ignore
    public void test1stOrder() throws Exception {
        System.out.println("setX");
        DoubleIirFilterGenerator gen = new DoubleIirFilterGenerator(8000);
        //LP
        DirectDoubleIirFilter filter = gen.getLP_ButterFc(1, 500, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.16591068104035053, 0.16591068104035053}, filter.getB(), Double.MIN_VALUE);

//        filter.setFc(5, 500, false);
//        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, filter.getA(), Double.MIN_VALUE);
//        assertArrayEquals(new double[]{0.16591068104035053, 0.16591068104035053}, filter.getB(), Double.MIN_VALUE);

        //HP
        filter = gen.getHP_ButterFc(1, 500, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.0, -0.6681786379192989}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.8340893189596494, -0.8340893189596494}, filter.getB(), Double.MIN_VALUE);
        //BP
        filter = gen.getBP_ButterFc(1, 500, 3000, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.00000, -0.281304567672052, -0.19891236737965798}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.5994561836898289, 0.00000, -0.5994561836898289}, filter.getB(), Double.MIN_VALUE);
        //BS
        filter = gen.getBS_ButterFc(1, 500, 3000, DirectDoubleIirFilter.class);
        assertArrayEquals(new double[]{1.00000, -0.281304567672052, -0.19891236737965798}, filter.getA(), Double.MIN_VALUE);
        assertArrayEquals(new double[]{0.40054381631017105, -0.281304567672052, 0.40054381631017105}, filter.getB(), Double.MIN_VALUE);
    }

}
