package com.falstad.filter.fir;

import com.falstad.filter.FFT;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class CustomFIRFilter extends FIRFilterType {
    private double uresp[];

    public CustomFIRFilter() {
        if (uresp == null) {
            uresp = new double[1024];
        }
    }

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Order");
        auxBars[0].setValue(120);
        auxBars[0].setMaximum(1600);
        int i;
        for (i = 0; i != 512; i++) {
            uresp[i] = 1.;
        }
        return 1;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
    }

    public double getUserResponse(double w) {
        double q = uresp[(int) (w * uresp.length / Math.PI)];
        return q * q;
    }

    public void edit(double x, double x2, double y) {
        int xi1 = (int) (x * uresp.length);
        int xi2 = (int) (x2 * uresp.length);
        for (; xi1 < xi2; xi1++) {
            if (xi1 >= 0 && xi1 < uresp.length) {
                uresp[xi1] = y;
            }
        }
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        int n = auxBars[0].getValue();
        int nsz = uresp.length * 4;
        double fbuf[] = new double[nsz];
        int nsz2 = nsz / 2;
        int nsz4 = nsz2 / 2;
        for (int i = 0; i != nsz4; i++) {
            double ur = uresp[i] / nsz2;
            fbuf[i * 2] = ur;
            if (i > 0) {
                fbuf[nsz - i * 2] = ur;
            }
        }
        new FFT(nsz2).ifft(fbuf);

        DirectFilter f = new DirectFilter();
        f.createCoeffArrays(n, 0, n);
        for (int i = 0; i != n; i++) {
            int i2 = (i - n / 2) * 2;
            f.setA(i, fbuf[i2 & (nsz - 1)] * getWindow(i, n, winIdx, kaiserbar));
            f.setN(i, i);
        }
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        int n = auxBars[0].getValue();
        x[0] = "Order: " + n;
    }

    @Override
    public boolean needsWindow() {
        return true;
    }
}
