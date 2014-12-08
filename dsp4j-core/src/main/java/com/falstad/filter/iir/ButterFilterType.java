package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public abstract class ButterFilterType extends PoleFilterType {

    @Override
    public Complex getSPole(int i, double wc) {
        double theta = Math.PI / 2 + (2 * i + 1) * Math.PI / (2 * n);
        Complex result = new Complex();
        result.setMagPhase(Math.tan(wc * .5), theta);
        return result;
    }
}
