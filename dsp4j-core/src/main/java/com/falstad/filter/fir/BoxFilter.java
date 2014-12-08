package com.falstad.filter.fir;

import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import java.awt.Label;
import java.awt.Scrollbar;

public class BoxFilter extends FIRFilterType {

    private double cw;
    private double r;
    private double norm;
    private int n;

    @Override
    public int select(Scrollbar[] auxBars, Label[] auxLabels) {
        auxLabels[0].setText("Fundamental Freq");
        auxBars[0].setValue(500);
        auxLabels[1].setText("Position");
        auxBars[1].setValue(300);
        auxLabels[2].setText("Length/Width");
        auxBars[2].setValue(100);
        auxLabels[3].setText("Order");
        auxBars[3].setMaximum(1600);
        auxBars[3].setValue(100);
        return 4;
    }

    @Override
    public void setup(Scrollbar[] auxBars, Label[] auxLabels) {
        super.setup(auxBars, auxLabels);
        cw = auxBars[0].getValue() * Math.PI / 1000.;
        if (cw < .147) {
            cw = .147;
        }
        r = auxBars[1].getValue() / 1000.;
        n = auxBars[3].getValue();
    }

    @Override
    public Filter genFilter(Scrollbar[] auxBars, Label[] auxLabels, int winIdx, Scrollbar kaiserbar) {
        DirectFilter f = new DirectFilter();
        int nn = 20;
        double ws[][] = new double[nn][nn];
        double mg[][] = new double[nn][nn];
        double px = r * Math.PI;
        double py = Math.PI / 2;
        double ly = auxBars[2].getValue() / 100.;
        for (int i = 0; i != nn; i++) {
            for (int j = 0; j != nn; j++) {
                ws[i][j] = cw * Math.sqrt(i * i + j * j / ly);
                mg[i][j] = Math.cos(i * px) * Math.cos(j * py);
            }
        }
        mg[0][0] = 0;
        f.createCoeffArrays(n, 0, 0);
        double sum = 0;
        double ecoef = -2.5 / n;
        for (int k = 0; k != n; k++) {
            double q = 0;
            for (int i = 0; i != nn; i++) {
                for (int j = 0; j != nn; j++) {
                    double ph = k * ws[i][j];
                    q += mg[i][j] * Math.cos(ph);
                }
            }
            f.setA(k, q * Math.exp(ecoef * k));
            sum += q;
        }
        // normalize
        for (int i = 0; i != n; i++) {
            f.setA(i, f.getA(i) / sum);
        }
        setResponse(f);
        return f;
    }

    @Override
    public void getInfo(String x[], Scrollbar[] auxBars, Label[] auxLabels) {
        x[0] = "Order: " + n;
    }
}
