package net.sf.dsp4j.fms4j.zvei;

import net.sf.dsp4j.datatypes._double.SinWaveSource;

/**
 *
 * @author aploese
 */
public class ZveiModulator extends SinWaveSource {

    public ZveiModulator(double precisition) {
        super(precisition);
    }

    public void addData(ZveiFreqTable ... z) {
        for (ZveiFreqTable z1 : z) {
            add(new SinWaveByDuration(z1.f, ZveiFreqTable.DEFAULT_TON_LENGTH_IN_S));
        }
    }

}

