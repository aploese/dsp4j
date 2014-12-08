package com.falstad.filter.iir;

import com.falstad.filter.Complex;

public class ResonatorZeroFilter extends ResonatorFilter {

    @Override
    public int getZeroCount() {
        return 2;
    }

    @Override
    public Complex getZero(int i) {
        return new Complex(i == 0 ? 1 : -1, 0);
    }
}
