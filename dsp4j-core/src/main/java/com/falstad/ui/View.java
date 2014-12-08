package com.falstad.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class View extends Rectangle {

    View() {
        super();
    }

    View(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    void drawLabel(Graphics g, String str) {
        g.setColor(Color.white);
        DFilterFrame.centerString(g, str, y - 5, width);
    }

    public int getRight() {
        return x + width - 1;
    }

    public int getBottom() {
        return y + height - 1;
    }
}
