/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.falstad.ui;

import com.falstad.filter.Complex;
import com.falstad.filter.FilterType;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author aploese
 */
class PhaseView extends View {

        void updateChart(Graphics g, double logrange, boolean isLog, double minlog, FilterType filterType) {
            drawLabel(g, "Phase Response");
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            for (int i = 0; i < 5; i++) {
                double q = i * .25;
                int y = this.y + (int) (q * height);
                g.drawLine(this.x, y, getRight(), y);
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
            int ox = -1, oy = -1;
            for (int i = 0; i != width; i++) {
                double w = 0;
                if (!isLog) {
                    w = Math.PI * i / (width);
                } else {
                    double f = Math.exp(minlog + i * logrange / width);
                    w = 2 * Math.PI * f;
                }
                final Complex cc =  filterType.getResponse(w);
                double val = .5 + cc.getPhase() / (2 * Math.PI);
                int y = this.y + (int) (height * val);
                int x = i + this.x;
                if (ox != -1) {
                    g.drawLine(ox, oy, x, y);
                } else if (x > this.x) {
                    g.drawLine(x, getBottom(), x, y);
                }
                ox = x;
                oy = y;
            }

    }

}
