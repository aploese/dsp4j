package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public class EllipticBandStop extends EllipticBandPass {

    @Override
    public Complex getPole(int i) {
        return getBandStopPole(i);
    }

    @Override
    public Complex getZero(int i) {
        Complex result = getEllipticZero(i / 2, Math.PI * .5);
        bandStopXform(i, result);
        return result;
    }
}
