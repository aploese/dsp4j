package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public class EllipticHighPass extends EllipticLowPass {

    @Override
    public Complex getPole(int i) {
        Complex reesult = getSPole(i, Math.PI - wc);
        bilinearXform(reesult);
        reesult.mult(-1);
        return reesult;
    }

    @Override
    public Complex getZero(int i) {
        Complex result = getEllipticZero(i, Math.PI - wc);
        result.mult(-1);
        return result;
    }
}
