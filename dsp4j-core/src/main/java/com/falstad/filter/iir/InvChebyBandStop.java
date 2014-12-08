package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public class InvChebyBandStop extends InvChebyBandPass {

    @Override
    public Complex getPole(int i) {
        return getBandStopPole(i);
    }

    @Override
    public Complex getZero(int i) {
        Complex result = getChebyZero(i / 2, Math.PI * .5);
        bandStopXform(i, result);
        return result;
    }
}
