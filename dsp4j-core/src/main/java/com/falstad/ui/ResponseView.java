/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.falstad.ui;

import com.falstad.filter.Complex;
import com.falstad.filter.FilterType;
import com.falstad.filter.fir.CustomFIRFilter;
import com.falstad.filter.iir.ChebyFilterType;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author aploese
 */
public class ResponseView extends View {

    void updateChart(Graphics g, double logrange, boolean isLog, double minlog, FilterType filterType) {
            drawLabel(g, "Frequency Response");
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            /*i = respView.x + respView.width/2;
            g.drawLine(i, respView.y, i, respView.y+respView.height);*/
            double ym = .069;
            for (int i = 0;; i += 2) {
                double q = ym * i;
                if (q > 1) {
                    break;
                }
                int y = this.y + (int) (q * height);
                g.drawLine(x, y, getRight(), y);
            }
            for (int i = 1;; i++) {
                double ll = logrange - i * Math.log(2);
                int x = 0;
                if (isLog) {
                    x = (int) (ll * width / logrange);
                } else {
                    x = width / (1 << i);
                }
                if (x <= 0) {
                    break;
                }
                x += this.x;
                g.drawLine(x, y, x, getBottom());
            }
            g.setColor(Color.white);
            int ox = -1, oy = -1, ox2 = -1, oy2 = -1;
            for (int i = 0; i < width; i++) {
                double w = 0;
                if (!isLog) {
                    w = Math.PI * i / (width);
                } else {
                    double f = Math.exp(minlog + i * logrange / width);
                    w = 2 * Math.PI * f;
                }
                final Complex cc = filterType.getResponse(w);
                double bw = cc.magSquared();
                double val = -ym * Math.log(bw * bw) / ChebyFilterType.LOG_10;
                int x = i + this.x;
                if (val > 1) {
                    if (ox != -1) {
                        g.drawLine(ox, oy, ox, getBottom());
                    }
                    ox = -1;
                } else {
                    int y = this.y + (int) (height * val);
                    if (ox != -1) {
                        g.drawLine(ox, oy, x, y);
                    } else if (x > this.x) {
                        g.drawLine(x, getBottom(), x, y);
                    }
                    ox = x;
                    oy = y;
                }
                if (filterType instanceof CustomFIRFilter) {
                    g.setColor(Color.white);
                    CustomFIRFilter cf = (CustomFIRFilter) filterType;
                    bw = cf.getUserResponse(w);
                    val = -ym * Math.log(bw * bw) / ChebyFilterType.LOG_10;
                    if (val > 1) {
                        if (ox2 != -1) {
                            g.drawLine(ox2, oy2, ox2, getBottom());
                        }
                        ox2 = -1;
                    } else {
                        int y = this.y + (int) (height * val);
                        if (ox2 != -1) {
                            g.drawLine(ox2, oy2, x, y);
                        } else if (x > this.x) {
                            g.drawLine(x, getBottom(), x, y);
                        }
                        ox2 = x;
                        oy2 = y;
                    }
                    g.setColor(Color.red);
                }
            }
    }


}
