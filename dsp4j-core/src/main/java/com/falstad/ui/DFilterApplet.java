package com.falstad.ui;

// DFilterApplet.java (C) 2005 by Paul Falstad, www.falstad.com
import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;

public class DFilterApplet extends Applet implements ComponentListener {


    DFilterFrame ogf;

    void destroyFrame() {
        if (ogf != null) {
            ogf.dispose();
        }
        ogf = null;
        repaint();
    }
    boolean started = false;

    public void init() {
        addComponentListener(this);
    }

    void showFrame() {
        if (ogf == null) {
            started = true;
            try {
                ogf = new DFilterFrame(this);
                ogf.init();
            } catch (Exception e) {
                e.printStackTrace();
                ogf = null;
                security = true;
                repaint();
            }
            repaint();
        }
    }
    boolean security = false;

    public void paint(Graphics g) {
        String s = "Applet is open in a separate window.";
        if (security) {
            s = "Security exception, use nosound version";
        } else if (!started) {
            s = "Applet is starting.";
        } else if (ogf == null) {
            s = "Applet is finished.";
        } else {
            ogf.show();
        }
        g.drawString(s, 10, 30);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
        showFrame();
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void destroy() {
        if (ogf != null) {
            ogf.dispose();
        }
        ogf = null;
        repaint();
    }

}
