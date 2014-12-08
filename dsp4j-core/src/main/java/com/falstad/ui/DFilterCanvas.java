package com.falstad.ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

public class DFilterCanvas extends Canvas {
    DFilterFrame pg;
    DFilterCanvas(DFilterFrame p) {
	pg = p;
    }
    @Override
    public Dimension getPreferredSize() {
	return new Dimension(300,400);
    }
    @Override
    public void update(Graphics g) {
	pg.updateDFilter(g);
    }
    @Override
    public void paint(Graphics g) {
	pg.updateDFilter(g);
    }
}
