package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public class InvChebyHighPass extends InvChebyLowPass {

    @Override
    public Complex getPole(int i) {
        Complex result = getSPole(i, Math.PI - wc);
        bilinearXform(result);
        result.mult(-1);
        return result;
    }

    @Override
    public Complex getZero(int i) {
        Complex result = getChebyZero(i, Math.PI - wc);
        result.mult(-1);
        return result;
    }
}
