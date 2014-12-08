package net.sf.dsp4j.datatypes._double;

import java.util.HashMap;
import java.util.Map;
import static net.sf.dsp4j.DspConst.TWO_PI;

public final class CosTableDouble {

    private final static Map<Integer, CosTableDouble> map = new HashMap();
    private final double[] cosTbl;
    private final double[] sinTbl;
    private final double phiToIndex;


    public static CosTableDouble getTableFor(double precisition) {
        double ds = TWO_PI / precisition * 2; // sin(x) = x for small x, sin(x)/dx is 1 for x = 0, so biggest error is around x = 0 ...
        int size = 1;
        while (size < ds) {
            size <<= 1;
        }
        CosTableDouble result = map.get(Integer.valueOf(size));
        if (result == null) {
            result = new CosTableDouble(size);
            map.put(Integer.valueOf(size), result);
        }
        return result;
    }


    private CosTableDouble(int size) {
        cosTbl = new double[size];
        sinTbl = new double[size];
        phiToIndex = (size - 1) / TWO_PI;
        for (int i = 0; i < size; i++) {
            cosTbl[i] = Math.cos(TWO_PI * i / (cosTbl.length - 1)); // 0 <= phi <= PI/2
            sinTbl[i] = Math.sin(TWO_PI * i / (sinTbl.length - 1)); // 0 <= phi <= PI/2
        }
    }

    public int getPeriodLength() {
        return cosTbl.length;
    }

    public final double cos0To2Pi(double phi) {
        return cosTbl[(int)Math.round(phi * phiToIndex)];
    }

    public final double sin0To2Pi(double phi) {
        return sinTbl[(int)Math.round(phi * phiToIndex)];
    }

    public final double cos(double phi) {
        if (phi < 0) {
            phi = -phi;
        }
        return cosTbl[(int)Math.round(phi * phiToIndex) % cosTbl.length];
    }

    public final double sin(double phi) {
        if (phi < 0) {
            return -sinTbl[(int)Math.round(-phi * phiToIndex) % sinTbl.length];
        } else {
            return sinTbl[(int)Math.round(phi * phiToIndex) % sinTbl.length];
        }
    }

}