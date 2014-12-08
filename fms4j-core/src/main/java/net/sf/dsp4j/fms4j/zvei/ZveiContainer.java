/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.zvei;

import com.falstad.filter.CascadeFilter;
import java.util.Arrays;
import net.sf.dsp4j.AbstractSampleProcessingBlock;
import net.sf.dsp4j.In;
import net.sf.dsp4j.datatypes._double.iirfilter.AbstractCascadedDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.datatypes._double.CostasLoop;
import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;

/**
 *
 * @author aploese
 */
public class ZveiContainer extends AbstractSampleProcessingBlock {

    private final ZveiDemodulator zveiDemodulator;
    private final ZveiFolgeDetector zveiFolgeDetector;
    private final ZveiFolgeContainerListener zveiFolgeContainerListener;
    private AbstractCascadedDoubleIirFilter inHpFilter;
    private AbstractCascadedDoubleIirFilter signalFilter;
    private int sampleCount;
    private int samplesSinceLast;
    private ZveiFreqTable currentZ = ZveiFreqTable.UNBEKANNT;
    private int zCount;
    private int minZCount;
    private int maxZCount;
    private int currentZCount;
    private int minCurrentZCount;
    private ZveiFreqTable[] result = new ZveiFreqTable[5];
    private int resultIndex;

    public ZveiContainer(ZveiFolgeContainerListener zveiFolgeContainerListener) {
        this.zveiFolgeContainerListener = zveiFolgeContainerListener;
        zveiDemodulator = new ZveiDemodulator();
        zveiFolgeDetector = new ZveiFolgeDetector(5, 7);
    }

    @In
    public ZveiFreqTable[] setX(double sample) {
        zCount++;
        currentZCount++;
        samplesSinceLast++;
        final boolean oldNeg = inHpFilter.getY() < 0;
        inHpFilter.setX(sample);
        if (oldNeg && inHpFilter.getY() >= 0) {
            sampleCount = samplesSinceLast;
            samplesSinceLast = 0;
        }
        signalFilter.setX(sampleCount);

        int frq = (int) (getSampleRate() / signalFilter.getY());
        ZveiFreqTable newZ = ZveiFreqTable.find(frq);
        if (newZ == currentZ) {
            if (currentZCount == minCurrentZCount) {
                if (newZ == ZveiFreqTable.UNBEKANNT) {
                    resultIndex = 0;
                    currentZCount = 0;
                    zCount = 0;
                } else {
                    if (resultIndex == 0) {
                        zCount = currentZCount;
                    }
                    result[resultIndex++] = newZ;
                }
            }
            if (resultIndex == 5) {
                if (zCount < minZCount) {
                    resultIndex = 0;
                    currentZCount = 0;
                    zCount = 0;
                } else {
                    final ZveiFreqTable[] zt = Arrays.copyOf(result, result.length);
                    zveiFolgeContainerListener.success(zt);
                    resultIndex = 0;
                    return result;
                }

            }
        } else {
            currentZCount = 0;
        }
        if (zCount > maxZCount) {
            resultIndex = 0;
            currentZCount = 0;
            zCount = 0;
        }
        currentZ = newZ;
        return null;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        DoubleIirFilterGenerator iirGenerator = new DoubleIirFilterGenerator(sampleRate);
        inHpFilter = iirGenerator.getBP_ButterFc(4, 50, 4000, AbstractCascadedDoubleIirFilter.class); // TODO Elliptic??
        signalFilter = iirGenerator.getLP_ButterFc(2, 70, AbstractCascadedDoubleIirFilter.class);
        zveiDemodulator.setSampleRate(sampleRate);
        minCurrentZCount = (int) (0.050 * sampleRate); // 50 ms min
        minZCount = (int) (0.310 * sampleRate) + 1; // 5 * 70md = 350 ms
        maxZCount = (int) (0.350 * sampleRate) + 1; // 5 * 70md = 350 ms
    }

    @Override
    public double getSampleRate() {
        return zveiDemodulator.getSampleRate();
    }

    @Override
    public void reset() {
        zveiDemodulator.reset();
    }

    /**
     * @return the signalFilter
     */
    public AbstractCascadedDoubleIirFilter getSignalFilter() {
        return signalFilter;
    }

    /**
     * @return the sampleCount
     */
    public int getSampleCount() {
        return sampleCount;
    }

    /**
     * @return the inHpFilter
     */
    public AbstractCascadedDoubleIirFilter getInHpFilter() {
        return inHpFilter;
    }
}
