package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public class ButterBandStop extends ButterBandPass {

    @Override
    public Complex getPole(int i) {
        return getBandStopPole(i);
    }

    @Override
    public Complex getZero(int i) {
        return getBandStopZero(i);
    }
}
