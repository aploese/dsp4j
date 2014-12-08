package net.sf.dsp4j.datatypes._double;

import java.util.Arrays;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.Out;

/**
 *
 * @author aploese
 */
public abstract class AbstractDoubleFirFilter extends AbstractSampleProcessingBlock  {

    private double[] b;
    private double[] si;
    private double y;

    protected void setCoeff(double[] b) {
        this.b = b;
        si = new double[b.length - 1];
    }

    @In
    public double setX(double x) {
         y = b[0] * x + si[0];
        for (int i = 0; i < si.length - 1; i++) {
            si[i] = b[i + 1] * x + si[i + 1];
        }
        si[si.length - 1] = b[b.length - 1] * x;

        return y;
    }
    
    @Out
    public double getY() {
        return y;
    }

    public double[] getB() {
        return Arrays.copyOf(b, b.length);
    }

    public double[] getSi() {
        return Arrays.copyOf(si, si.length);
    }

    @Override
    public void reset() {
       for (int i = 0; i < si.length; i++) {
           si[i] = 0;
       }
       super.reset();
    }

}
