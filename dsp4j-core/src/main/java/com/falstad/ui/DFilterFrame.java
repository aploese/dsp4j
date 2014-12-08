package com.falstad.ui;

import com.falstad.filter.Complex;
import com.falstad.filter.FFT;
import com.falstad.filter.DirectFilter;
import com.falstad.filter.Filter;
import com.falstad.filter.FilterType;
import com.falstad.filter.NoFilter;
import com.falstad.filter.fir.CustomFIRFilter;
import com.falstad.filter.fir.GaussianFilter;
import com.falstad.filter.fir.InverseCombFilter;
import com.falstad.filter.fir.MovingAverageFilter;
import com.falstad.filter.fir.RandomFilter;
import com.falstad.filter.fir.SincBandPassFilter;
import com.falstad.filter.fir.SincBandStopFilter;
import com.falstad.filter.fir.SincHighPassFilter;
import com.falstad.filter.fir.SincLowPassFilter;
import com.falstad.filter.fir.TriangleFilter;
import com.falstad.filter.iir.AllPassFilter;
import com.falstad.filter.iir.ButterBandPass;
import com.falstad.filter.iir.ButterBandStop;
import com.falstad.filter.iir.ButterHighPass;
import com.falstad.filter.iir.ButterLowPass;
import com.falstad.filter.iir.ChebyBandPass;
import com.falstad.filter.iir.ChebyBandStop;
import com.falstad.filter.iir.ChebyFilterType;
import com.falstad.filter.iir.ChebyHighPass;
import com.falstad.filter.iir.ChebyLowPass;
import com.falstad.filter.iir.CombFilter;
import com.falstad.filter.iir.CustomIIRFilter;
import com.falstad.filter.iir.DelayFilter;
import com.falstad.filter.iir.EllipticBandPass;
import com.falstad.filter.iir.EllipticBandStop;
import com.falstad.filter.iir.EllipticHighPass;
import com.falstad.filter.iir.EllipticLowPass;
import com.falstad.filter.iir.InvChebyBandPass;
import com.falstad.filter.iir.InvChebyBandStop;
import com.falstad.filter.iir.InvChebyHighPass;
import com.falstad.filter.iir.InvChebyLowPass;
import com.falstad.filter.iir.NotchFilter;
import com.falstad.filter.iir.PluckedStringFilter;
import com.falstad.filter.iir.ResonatorFilter;
import com.falstad.filter.iir.ResonatorZeroFilter;
import com.falstad.source.ImpulseWaveform;
import com.falstad.source.Mp3Waveform;
import com.falstad.source.NoiseWaveform;
import com.falstad.source.PeriodicNoiseWaveform;
import com.falstad.source.SawtoothWaveform;
import com.falstad.source.SineWaveform;
import com.falstad.source.SquareWaveform;
import com.falstad.source.SweepWaveform;
import com.falstad.source.TriangleWaveform;
import com.falstad.source.Waveform;
import java.awt.Checkbox;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.MemoryImageSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class DFilterFrame extends Frame
        implements ComponentListener, ActionListener, AdjustmentListener,
        MouseMotionListener, MouseListener, ItemListener {

    Dimension winSize;
    Image dbimage;
    ResponseView respView;
    PhaseView phaseView;
    View impulseView, stepView, spectrumView, waveformView, poleInfoView, polesView;
    Random random;
    int maxSampleCount = 70; // was 50
    int sampleCountR, sampleCountTh;
    int modeCountR, modeCountTh;
    int maxDispRModes = 5, maxDispThModes = 5;
    public static int WINDOW_KAISER = 4;
    private NumberFormat showFormat;

    public static void main(String[] args) throws Exception {
        final DFilterFrame f = new DFilterFrame(null);
        f.setSize(800, 600);
        f.init();
        f.setVisible(true);
        f.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                f.setVisible(false);
                f.dispose();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                f.setVisible(false);
                f.dispose();
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    public String getAppletInfo() {
        return "DFilter Series by Paul Falstad";
    }
    Checkbox soundCheck;
    Checkbox displayCheck;
    Checkbox shiftSpectrumCheck;
    //Checkbox woofCheck;
    CheckboxMenuItem freqCheckItem;
    CheckboxMenuItem phaseCheckItem;
    CheckboxMenuItem spectrumCheckItem;
    CheckboxMenuItem impulseCheckItem;
    CheckboxMenuItem stepCheckItem;
    CheckboxMenuItem waveformCheckItem;
    CheckboxMenuItem logFreqCheckItem;
    CheckboxMenuItem logAmpCheckItem;
    CheckboxMenuItem allWaveformCheckItem;
    CheckboxMenuItem ferrisCheckItem;
    MenuItem exitItem;
    Choice filterChooser;
    int selection;
    final int SELECT_RESPONSE = 1;
    final int SELECT_SPECTRUM = 2;
    final int SELECT_POLES = 3;
    int filterSelection;
    Choice inputChooser;
    Choice windowChooser;
    Choice rateChooser;
    Scrollbar[] auxBars;
    Label[] auxLabels;
    Label inputLabel;
    Scrollbar inputBar;
    Label shiftFreqLabel;
    Scrollbar shiftFreqBar;
    Label kaiserLabel;
    Scrollbar kaiserBar;
    boolean editingFunc;
    boolean dragStop;
    double inputW;
    double step;
    double waveGain = 1. / 65536;
    double outputGain = 1;
    int sampleRate;
    int xpoints[] = new int[4];
    int ypoints[] = new int[4];
    int dragX, dragY;
    int dragStartX, dragStartY;
    int mouseX, mouseY;
    int selectedPole, selectedZero;
    int lastPoleCount = 2, lastZeroCount = 2;
    boolean dragSet, dragClear;
    boolean dragging;
    boolean unstable;
    MemoryImageSource imageSource;
    Image memimage;
    int pixels[];
    double t;
    int pause;
    PlayThread playThread;
    Filter curFilter;
    FilterType filterType;
    double spectrumBuf[];
    FFT spectrumFFT;
    Waveform wformInfo;
    PhaseColor phaseColors[];
    static final int phaseColorCount = 50 * 8;
    boolean filterChanged;

    int getrand(int x) {
        int q = random.nextInt();
        if (q < 0) {
            q = -q;
        }
        return q % x;
    }
    DFilterCanvas cv;
    DFilterApplet applet;

    DFilterFrame(DFilterApplet a) {
        super("Digital Filters Applet v1.2");
        applet = a;
    }
    boolean java2 = false;
    String mp3List[];
    String mp3Error;

    public void init() {
        showFormat = DecimalFormat.getInstance();
        showFormat.setMaximumFractionDigits(2);

        mp3List = new String[20];
        if (applet != null) {
            try {
                String param = applet.getParameter("PAUSE");
                if (param != null) {
                    pause = Integer.parseInt(param);
                }
                int i;
                for (i = 0; i < mp3List.length; i++) {
                    param = applet.getParameter("mp3File" + (i + 1));
                    if (param == null) {
                        break;
                    }
                    mp3List[i] = param;
                }
            } catch (Exception e) {
            }
        }
        String jv = System.getProperty("java.class.version");
        double jvf = new Double(jv).doubleValue();
        if (jvf >= 48) {
            java2 = true;
        }

        int j;
        int pc8 = phaseColorCount / 8;
        phaseColors = new PhaseColor[phaseColorCount];
        int i;
        for (i = 0; i != 8; i++) {
            for (j = 0; j != pc8; j++) {
                double ang = Math.atan(j / (double) pc8);
                phaseColors[i * pc8 + j] = genPhaseColor(i, ang);
            }
        }

        customPoles = new Complex[20];
        customZeros = new Complex[20];
        for (i = 0; i != customPoles.length; i++) {
            customPoles[i] = new Complex();
        }
        for (i = 0; i != customZeros.length; i++) {
            customZeros[i] = new Complex();
        }

        setLayout(new DFilterLayout());
        cv = new DFilterCanvas(this);
        cv.addComponentListener(this);
        cv.addMouseMotionListener(this);
        cv.addMouseListener(this);
        add(cv);

        MenuBar mb = new MenuBar();
        Menu m = new Menu("File");
        mb.add(m);
        m.add(exitItem = getMenuItem("Exit"));
        m = new Menu("View");
        mb.add(m);
        m.add(freqCheckItem = getCheckItem("Frequency Response", true));
        m.add(phaseCheckItem = getCheckItem("Phase Response", false));
        m.add(spectrumCheckItem = getCheckItem("Spectrum", true));
        m.add(waveformCheckItem = getCheckItem("Waveform", java2));
        m.add(impulseCheckItem = getCheckItem("Impulse Response", true));
        m.add(stepCheckItem = getCheckItem("Step Response", false));
        m.addSeparator();
        m.add(logFreqCheckItem = getCheckItem("Log Frequency Scale", false));
        m.add(allWaveformCheckItem = getCheckItem("Show Entire Waveform", false));
        m.add(ferrisCheckItem = getCheckItem("Ferris Plot", false));
        // this doesn't fully work when turned off
        logAmpCheckItem = getCheckItem("Log Amplitude Scale", true);

        setMenuBar(mb);

        soundCheck = new Checkbox("Sound On");
        if (java2) {
            soundCheck.setState(true);
        } else {
            soundCheck.disable();
        }
        soundCheck.addItemListener(this);
        add(soundCheck);

        displayCheck = new Checkbox("Stop Display");
        displayCheck.addItemListener(this);
        add(displayCheck);

        shiftSpectrumCheck = new Checkbox("Shift Spectrum");
        shiftSpectrumCheck.addItemListener(this);
        add(shiftSpectrumCheck);

        /*woofCheck = new Checkbox("Woof");
        woofCheck.addItemListener(this);
        add(woofCheck);*/

        add(inputChooser = new Choice());
        inputChooser.add("Input = Noise");
        inputChooser.add("Input = Sine Wave");
        inputChooser.add("Input = Sawtooth");
        inputChooser.add("Input = Triangle Wave");
        inputChooser.add("Input = Square Wave");
        inputChooser.add("Input = Periodic Noise");
        inputChooser.add("Input = Sweep");
        inputChooser.add("Input = Impulses");
        for (i = 0; mp3List[i] != null; i++) {
            inputChooser.add("Input = " + mp3List[i]);
        }
        inputChooser.addItemListener(this);

        add(filterChooser = new Choice());
        filterChooser.add("Filter = FIR Low-pass");
        filterChooser.add("Filter = FIR High-pass");
        filterChooser.add("Filter = FIR Band-pass");
        filterChooser.add("Filter = FIR Band-stop");
        filterChooser.add("Filter = Custom FIR");
        filterChooser.add("Filter = None");
        filterChooser.add("Filter = Butterworth Low-pass");
        filterChooser.add("Filter = Butterworth High-pass");
        filterChooser.add("Filter = Butterworth Band-pass");
        filterChooser.add("Filter = Butterworth Band-stop");
        filterChooser.add("Filter = Chebyshev Low-pass");
        filterChooser.add("Filter = Chebyshev High-pass");
        filterChooser.add("Filter = Chebyshev Band-pass");
        filterChooser.add("Filter = Chebyshev Band-stop");
        filterChooser.add("Filter = Inv Cheby Low-pass");
        filterChooser.add("Filter = Inv Cheby High-pass");
        filterChooser.add("Filter = Inv Cheby Band-pass");
        filterChooser.add("Filter = Inv Cheby Band-stop");
        filterChooser.add("Filter = Elliptic Low-pass");
        filterChooser.add("Filter = Elliptic High-pass");
        filterChooser.add("Filter = Elliptic Band-pass");
        filterChooser.add("Filter = Elliptic Band-stop");
        filterChooser.add("Filter = Comb (+)");
        filterChooser.add("Filter = Comb (-)");
        filterChooser.add("Filter = Delay");
        filterChooser.add("Filter = Plucked String");
        filterChooser.add("Filter = Inverse Comb");
        filterChooser.add("Filter = Reson");
        filterChooser.add("Filter = Reson w/ Zeros");
        filterChooser.add("Filter = Notch");
        filterChooser.add("Filter = Moving Average");
        filterChooser.add("Filter = Triangle");
        filterChooser.add("Filter = Allpass");
        filterChooser.add("Filter = Gaussian");
        filterChooser.add("Filter = Random");
        filterChooser.add("Filter = Custom IIR");
        filterChooser.addItemListener(this);
        filterSelection = -1;

        add(windowChooser = new Choice());
        windowChooser.add("Window = Rectangular");
        windowChooser.add("Window = Hamming");
        windowChooser.add("Window = Hann");
        windowChooser.add("Window = Blackman");
        windowChooser.add("Window = Kaiser");
        windowChooser.add("Window = Bartlett");
        windowChooser.add("Window = Welch");
        windowChooser.addItemListener(this);
        windowChooser.select(1);

        add(rateChooser = new Choice());
        rateChooser.add("Sampling Rate = 8000");
        rateChooser.add("Sampling Rate = 11025");
        rateChooser.add("Sampling Rate = 16000");
        rateChooser.add("Sampling Rate = 22050");
        rateChooser.add("Sampling Rate = 32000");
        rateChooser.add("Sampling Rate = 44100");
        rateChooser.select(3);
        sampleRate = 22050;
        rateChooser.addItemListener(this);

        auxLabels = new Label[5];
        auxBars = new Scrollbar[5];
        for (i = 0; i != 5; i++) {
            add(auxLabels[i] = new Label("", Label.CENTER));
            add(auxBars[i] = new Scrollbar(Scrollbar.HORIZONTAL, 25, 1, 1, 999));
            auxBars[i].addAdjustmentListener(this);
        }

        add(inputLabel = new Label("Input Frequency", Label.CENTER));
        add(inputBar = new Scrollbar(Scrollbar.HORIZONTAL, 40, 1, 1, 999));
        inputBar.addAdjustmentListener(this);

        add(shiftFreqLabel = new Label("Shift Frequency", Label.CENTER));
        add(shiftFreqBar = new Scrollbar(Scrollbar.HORIZONTAL, 10, 1, 0, 1001));
        shiftFreqBar.addAdjustmentListener(this);
        shiftFreqLabel.hide();
        shiftFreqBar.hide();

        add(kaiserLabel = new Label("Kaiser Parameter", Label.CENTER));
        add(kaiserBar = new Scrollbar(Scrollbar.HORIZONTAL, 500, 1, 1, 999));
        kaiserBar.addAdjustmentListener(this);

        random = new Random();
        setInputLabel();
        reinit();
        cv.setBackground(Color.black);
        cv.setForeground(Color.lightGray);

        resize(640, 640);
        handleResize();
        Dimension x = getSize();
        Dimension screen = getToolkit().getScreenSize();
        setLocation((screen.width - x.width) / 2,
                (screen.height - x.height) / 2);
        show();
    }

    void reinit() {
        setupFilter();
        setInputW();
    }

    MenuItem getMenuItem(String s) {
        MenuItem mi = new MenuItem(s);
        mi.addActionListener(this);
        return mi;
    }

    CheckboxMenuItem getCheckItem(String s, boolean b) {
        CheckboxMenuItem mi = new CheckboxMenuItem(s);
        mi.setState(b);
        mi.addItemListener(this);
        return mi;
    }

    PhaseColor genPhaseColor(int sec, double ang) {
        // convert to 0 .. 2*pi angle
        ang += sec * Math.PI / 4;
        // convert to 0 .. 6
        ang *= 3 / Math.PI;
        int hsec = (int) ang;
        double a2 = ang % 1;
        double a3 = 1. - a2;
        PhaseColor c = null;
        switch (hsec) {
            case 6:
            case 0:
                c = new PhaseColor(1, a2, 0);
                break;
            case 1:
                c = new PhaseColor(a3, 1, 0);
                break;
            case 2:
                c = new PhaseColor(0, 1, a2);
                break;
            case 3:
                c = new PhaseColor(0, a3, 1);
                break;
            case 4:
                c = new PhaseColor(a2, 0, 1);
                break;
            case 5:
                c = new PhaseColor(1, 0, a3);
                break;
        }
        return c;
    }

    class PhaseColor {

        public double r, g, b;

        PhaseColor(double rr, double gg, double bb) {
            r = rr;
            g = gg;
            b = bb;
        }
    }

    void handleResize() {
        Dimension d = winSize = cv.getSize();
        if (winSize.width == 0) {
            return;
        }
        int ct = 1;
        respView = null;
        phaseView = null;
        impulseView = null;
        spectrumView = null;
                stepView = null;
                waveformView = null;
        if (freqCheckItem.getState()) {
            ct++;
        }
        if (phaseCheckItem.getState()) {
            ct++;
        }
        if (spectrumCheckItem.getState()) {
            ct++;
        }
        if (waveformCheckItem.getState()) {
            ct++;
        }
        if (impulseCheckItem.getState()) {
            ct++;
        }
        if (stepCheckItem.getState()) {
            ct++;
        }

        int dh3 = d.height / ct;
        dbimage = createImage(d.width, d.height);
        int bd = 15;

        int i = 0;
        if (freqCheckItem.getState()) {
            respView = new ResponseView();
            setViewDimension(respView, i++, ct);
        }
        if (phaseCheckItem.getState()) {
            phaseView = new PhaseView();
            setViewDimension(phaseView, i++, ct);
        }
        if (spectrumCheckItem.getState()) {
            spectrumView = new View();
            setViewDimension(spectrumView, i++, ct);
        }
        if (waveformCheckItem.getState()) {
            waveformView = new View();
            setViewDimension(waveformView, i++, ct);
        }
        if (impulseCheckItem.getState()) {
            impulseView = new View();
            setViewDimension(impulseView, i++, ct);
        }
        if (stepCheckItem.getState()) {
            stepView = new View();
            setViewDimension(stepView, i++, ct);
        }
             poleInfoView= new View();
            setViewDimension(poleInfoView, i++, ct);
        if (poleInfoView.height > 200) {
            poleInfoView.height = 200;
        }
        polesView = new View(poleInfoView.x, poleInfoView.y,
                poleInfoView.height, poleInfoView.height);
        getPoleBuffer();
    }

    private void setViewDimension(View v, int i, int ct) {
        final int bd = 5;
        final int tpad = 15;
        v.setLocation(bd, bd + i * winSize.height / ct + tpad);
        v.setSize(winSize.width - bd * 2, winSize.height / ct - bd * 2 - tpad);
    }

    void getPoleBuffer() {
        int i;
        pixels = null;
        if (java2) {
            try {
                /* simulate the following code using reflection:
                dbimage = new BufferedImage(d.width, d.height,
                BufferedImage.TYPE_INT_RGB);
                DataBuffer db = (DataBuffer)(((BufferedImage)memimage).
                getRaster().getDataBuffer());
                DataBufferInt dbi = (DataBufferInt) db;
                pixels = dbi.getData();
                 */
                Class biclass = Class.forName("java.awt.image.BufferedImage");
                Class dbiclass = Class.forName("java.awt.image.DataBufferInt");
                Class rasclass = Class.forName("java.awt.image.Raster");
                Constructor cstr = biclass.getConstructor(
                        new Class[]{int.class, int.class, int.class});
                memimage = (Image) cstr.newInstance(new Object[]{
                            new Integer(polesView.width), new Integer(polesView.height),
                            new Integer(1)}); // BufferedImage.TYPE_INT_RGB)});
                Method m = biclass.getMethod("getRaster", null);
                Object ras = m.invoke(memimage, null);
                Object db = rasclass.getMethod("getDataBuffer", null).
                        invoke(ras, null);
                pixels = (int[]) dbiclass.getMethod("getData", null).invoke(db, null);
            } catch (Exception ee) {
                // ee.printStackTrace();
                System.out.println("BufferedImage failed");
            }
        }
        if (pixels == null) {
            pixels = new int[polesView.width * polesView.height];
            for (i = 0; i != polesView.width * polesView.height; i++) {
                pixels[i] = 0xFF000000;
            }
            imageSource = new MemoryImageSource(polesView.width, polesView.height,
                    pixels, 0, polesView.width);
            imageSource.setAnimated(true);
            imageSource.setFullBufferUpdates(true);
            memimage = cv.createImage(imageSource);
        }
    }

    public static void centerString(Graphics g, String s, int y, int winSizeWidth) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(s, (winSizeWidth - fm.stringWidth(s)) / 2, y);
    }

    @Override
    public void paint(Graphics g) {
        cv.repaint();
    }
    long lastTime;
    double minlog, logrange;

    public void updateDFilter(Graphics realg) {
        Graphics g = dbimage.getGraphics();
        if (winSize == null || winSize.width == 0 || dbimage == null) {
            return;
        }

        if (curFilter == null) {
            Filter f = filterType.genFilter(auxBars, auxLabels, windowChooser.getSelectedIndex(), kaiserBar);
            curFilter = f;
            if (playThread != null) {
                playThread.setFilter(f);
            }
            filterChanged = true;
            unstable = false;
        }

        if (playThread == null && !unstable && soundCheck.getState()) {
            playThread = new PlayThread();
            playThread.start();
        }

        if (displayCheck.getState()) {
            return;
        }

        g.setColor(cv.getBackground());
        g.fillRect(0, 0, winSize.width, winSize.height);
        g.setColor(cv.getForeground());

        double minf = 40. / sampleRate;
        minlog = Math.log(minf);
        logrange = Math.log(.5) - minlog;

        if (respView != null) {
            respView.updateChart(g, logrange, logFreqCheckItem.getState(), minlog, filterType);
        }
        if (phaseView != null) {
            phaseView.updateChart(g, logrange, logFreqCheckItem.getState(), minlog, filterType);
        }

        int polect = filterType.getPoleCount();
        int zeroct = filterType.getZeroCount();
        int infoX = 10;
        int ph = 0, pw = 0, cx = 0, cy = 0;
        if (poleInfoView != null && (polect > 0 || zeroct > 0 || ferrisCheckItem.getState())) {
            ph = polesView.height / 2;
            pw = ph;
            cx = polesView.x + pw;
            cy = polesView.y + ph;
            infoX = cx + pw + 10;

            if (!ferrisCheckItem.getState()) {
                g.setColor(Color.white);
                FontMetrics fm = g.getFontMetrics();
                String s = "Poles/Zeros";
                g.drawString(s, cx - fm.stringWidth(s) / 2, polesView.y - 5);
                g.drawOval(cx - pw, cy - ph, pw * 2, ph * 2);
                g.drawLine(cx, cy - ph, cx, cy + ph);
                g.drawLine(cx - ph, cy, cx + ph, cy);
                for (int i = 0; i < polect; i++) {
                    Complex c1 = filterType.getPole(i);
                    g.setColor(i == selectedPole ? Color.yellow : Color.white);
                    int c1x = cx + (int) (pw * c1.getRe());
                    int c1y = cy - (int) (ph * c1.getIm());
                    g.drawLine(c1x - 3, c1y - 3, c1x + 3, c1y + 3);
                    g.drawLine(c1x - 3, c1y + 3, c1x + 3, c1y - 3);
                }
                for (int i = 0; i < zeroct; i++) {
                    Complex c1 = filterType.getZero(i);
                    g.setColor(i == selectedZero ? Color.yellow : Color.white);
                    int c1x = cx + (int) (pw * c1.getRe());
                    int c1y = cy - (int) (ph * c1.getIm());
                    g.drawOval(c1x - 3, c1y - 3, 6, 6);
                }
                if (filterChanged) {
                    setCustomPolesZeros();
                }
            } else {
                if (filterChanged) {
                    int ri, ii;
                    Complex c1 = new Complex();
                    for (ri = 0; ri != polesView.width; ri++) {
                        for (ii = 0; ii != polesView.height; ii++) {
                            c1.set((ri - pw) / (double) pw,
                                    (ii - pw) / (double) pw);
                            if (c1.getRe() == 0 && c1.getIm() == 0) {
                                c1.set(1e-30);
                            }
                            curFilter.evalTransfer(c1);
                            double cv = 0, wv = 0;
                            double m = Math.sqrt(c1.getMagnitude());
                            if (m < 1) {
                                cv = m;
                                wv = 1 - cv;
                            } else if (m < 2) {
                                cv = 2 - m;
                            }
                            cv *= 255;
                            wv *= 255;
                            double p = c1.getPhase();
                            if (p < 0) {
                                p += 2 * Math.PI;
                            }
                            if (p >= 2 * Math.PI) {
                                p -= 2 * Math.PI;
                            }
                            PhaseColor pc = phaseColors[(int) (p * phaseColorCount / (2 * Math.PI))];
                            pixels[ri + ii * polesView.width] = 0xFF000000
                                    + 0x10000 * (int) (pc.r * cv + wv)
                                    + 0x00100 * (int) (pc.g * cv + wv)
                                    + 0x00001 * (int) (pc.b * cv + wv);
                        }
                    }
                }
                if (imageSource != null) {
                    imageSource.newPixels();
                }
                g.drawImage(memimage, polesView.x, polesView.y, null);
            }
        }
        if (poleInfoView != null) {
            g.setColor(Color.white);
            String info[] = new String[10];
            filterType.getInfo(info, auxBars, auxLabels);
            int infoIdx;
            for (infoIdx = 0; infoIdx < 10; infoIdx++) {
                if (info[infoIdx] == null) {
                    break;
                }
            }
            if (wformInfo.needsFrequency()) {
                info[infoIdx++] = "Input Freq = " + (int) (inputW * sampleRate / (2 * Math.PI));
            }
            info[infoIdx++] = "Output adjust = "
                    + showFormat.format(-10 * Math.log(outputGain) / Math.log(.1)) + " dB";
            for (infoIdx = 0; infoIdx != 10; infoIdx++) {
                if (info[infoIdx] == null) {
                    break;
                }
                g.drawString(info[infoIdx], infoX, poleInfoView.y + 5 + 20 * infoIdx);
            }
            if ((respView != null && respView.contains(mouseX, mouseY))
                    || (spectrumView != null && spectrumView.contains(mouseX, mouseY))) {
                double f = getFreqFromX(mouseX, respView);
                if (f >= 0) {
                    double fw = 2 * Math.PI * f;
                    f *= sampleRate;
                    g.setColor(Color.yellow);
                    String s = "Selected Freq = " + (int) f;
                    if (respView.contains(mouseX, mouseY)) {
                        final Complex cc = filterType.getResponse(fw);
                        double bw = cc.magSquared();
                        bw = Math.log(bw * bw) / (2 * ChebyFilterType.LOG_10);
                        s += ", Response = " + showFormat.format(10 * bw) + " dB";
                    }
                    g.drawString(s, infoX, poleInfoView.y + 5 + 20 * infoIdx);
                    if (ph > 0) {
                        int x = cx + (int) (pw * Math.cos(fw));
                        int y = cy - (int) (pw * Math.sin(fw));
                        if (ferrisCheckItem.getState()) {
                            g.setColor(Color.black);
                            g.fillOval(x - 3, y - 3, 7, 7);
                        }
                        g.setColor(Color.yellow);
                        g.fillOval(x - 2, y - 2, 5, 5);
                    }
                }
            }
        }

        if (impulseView != null) {
            impulseView.drawLabel(g, "Impulse Response");
            g.setColor(Color.darkGray);
            g.fillRect(impulseView.x, impulseView.y, impulseView.width, impulseView.height);
            g.setColor(Color.black);
            g.drawLine(impulseView.x, impulseView.y + impulseView.height / 2,
                    impulseView.x + impulseView.width - 1,
                    impulseView.y + impulseView.height / 2);
            g.setColor(Color.white);
            int offset = curFilter.getImpulseOffset();
            double impBuf[] = curFilter.getImpulseResponse(offset);
            int len = curFilter.getImpulseLen(offset, impBuf);
            int ox = -1, oy = -1;
            double mult = .5 / max(impBuf);
            int flen = (len < 50) ? 50 : len;
            if (len < flen && flen < impBuf.length - offset) {
                len = flen;
            }
            //System.out.println("cf " + offset + " " + len + " " + impBuf.length);
            for (int i = 0; i != len; i++) {
                int k = offset + i;
                double q = impBuf[k] * mult;
                int y = impulseView.y + (int) (impulseView.height * (.5 - q));
                int x = impulseView.x + impulseView.width * i / flen;
                if (len < 100) {
                    g.drawLine(x, impulseView.y + impulseView.height / 2, x, y);
                    g.fillOval(x - 2, y - 2, 5, 5);
                } else {
                    if (ox != -1) {
                        g.drawLine(ox, oy, x, y);
                    }
                    ox = x;
                    oy = y;
                }
            }
        }

        if (stepView != null) {
            stepView.drawLabel(g, "Step Response");
            g.setColor(Color.darkGray);
            g.fillRect(stepView.x, stepView.y, stepView.width, stepView.height);
            g.setColor(Color.black);
            g.drawLine(stepView.x, stepView.y + stepView.height / 2,
                    stepView.x + stepView.width - 1,
                    stepView.y + stepView.height / 2);
            g.setColor(Color.white);
            int offset = curFilter.getStepOffset();
            double impBuf[] = curFilter.getStepResponse(offset);
            int len = curFilter.getStepLen(offset, impBuf);
            int ox = -1, oy = -1;
            double mult = .5 / max(impBuf);
            int flen = (len < 50) ? 50 : len;
            if (len < flen && flen < impBuf.length - offset) {
                len = flen;
            }
            //System.out.println("cf " + offset + " " + len + " " + impBuf.length);
            for (int i = 0; i != len; i++) {
                int k = offset + i;
                double q = impBuf[k] * mult;
                int y = stepView.y + (int) (stepView.height * (.5 - q));
                int x = stepView.x + stepView.width * i / flen;
                if (len < 100) {
                    g.drawLine(x, stepView.y + stepView.height / 2, x, y);
                    g.fillOval(x - 2, y - 2, 5, 5);
                } else {
                    if (ox != -1) {
                        g.drawLine(ox, oy, x, y);
                    }
                    ox = x;
                    oy = y;
                }
            }
        }

        if (playThread != null) {
            int splen = playThread.spectrumLen;
            if (spectrumBuf == null || spectrumBuf.length != splen * 2) {
                spectrumBuf = new double[splen * 2];
            }
            int off = playThread.spectrumOffset;
            int i2;
            int mask = playThread.BUFF_MASK;
            for (int i = i2 = 0; i != splen; i++, i2 += 2) {
                int o = mask & (off + i);
                spectrumBuf[i2] = playThread.filterOutBuff[o];
                spectrumBuf[i2 + 1] = 0;
            }
        } else {
            spectrumBuf = null;
        }

        if (waveformView != null && spectrumBuf != null) {
            waveformView.drawLabel(g, "Waveform");
            g.setColor(Color.darkGray);
            g.fillRect(waveformView.x, waveformView.y,
                    waveformView.width, waveformView.height);
            g.setColor(Color.black);
            g.drawLine(waveformView.x, waveformView.y + waveformView.height / 2,
                    waveformView.x + waveformView.width - 1,
                    waveformView.y + waveformView.height / 2);
            g.setColor(Color.white);
            int ox = -1, oy = -1;

            if (waveGain < .1) {
                waveGain = .1;
            }
            double max = 0;
            for (int i = 0; i != spectrumBuf.length; i += 2) {
                if (spectrumBuf[i] > max) {
                    max = spectrumBuf[i];
                }
                if (spectrumBuf[i] < -max) {
                    max = -spectrumBuf[i];
                }
            }
            if (waveGain > 1 / max) {
                waveGain = 1 / max;
            } else if (waveGain * 1.05 < 1 / max) {
                waveGain *= 1.05;
            }
            double mult = .5 * waveGain;
            int nb = waveformView.width;
            if (nb > spectrumBuf.length || allWaveformCheckItem.getState()) {
                nb = spectrumBuf.length;
            }
            for (int i = 0; i < nb; i += 2) {
                double bf = .5 - spectrumBuf[i] * mult;
                int ya = (int) (waveformView.height * bf);
                if (ya > waveformView.height) {
                    ox = -1;
                    continue;
                }
                int y = waveformView.y + ya;
                int x = waveformView.x + i * waveformView.width / nb;
                if (ox != -1) {
                    g.drawLine(ox, oy, x, y);
                }
                ox = x;
                oy = y;
            }
        }

        if (spectrumView != null && spectrumBuf != null) {
            spectrumView.drawLabel(g, "Spectrum");
            g.setColor(Color.darkGray);
            g.fillRect(spectrumView.x, spectrumView.y,
                    spectrumView.width, spectrumView.height);
            g.setColor(Color.black);
            double ym = .138;
            for (int i = 0;; i++) {
                double q = ym * i;
                if (q > 1) {
                    break;
                }
                int y = spectrumView.y + (int) (q * spectrumView.height);
                g.drawLine(spectrumView.x, y, spectrumView.x + spectrumView.width, y);
            }
            for (int i = 1;; i++) {
                double ll = logrange - i * Math.log(2);
                int x = 0;
                if (logFreqCheckItem.getState()) {
                    x = (int) (ll * spectrumView.width / logrange);
                } else {
                    x = spectrumView.width / (1 << i);
                }
                if (x <= 0) {
                    break;
                }
                x += spectrumView.x;
                g.drawLine(x, spectrumView.y, x, spectrumView.getBottom());
            }

            g.setColor(Color.white);
            int isub = spectrumBuf.length / 2;
            double cosmult = 2 * Math.PI / (spectrumBuf.length - 2);
            for (int i = 0; i != spectrumBuf.length; i += 2) {
                double ht = .54 - .46 * Math.cos(i * cosmult);
                spectrumBuf[i] *= ht;
            }
            if (spectrumFFT == null || spectrumFFT.getSize() != spectrumBuf.length / 2) {
                spectrumFFT = new FFT(spectrumBuf.length / 2);
            }
            spectrumFFT.fft(spectrumBuf);
            double logmult = spectrumView.width / Math.log(spectrumBuf.length / 2 + 1);

            int ox = -1, oy = -1;
            double bufmult = 1. / (spectrumBuf.length / 2);
            if (logAmpCheckItem.getState()) {
                bufmult /= 65536;
            } else {
                bufmult /= 768;
            }
            bufmult *= bufmult;

            double specArray[] = new double[spectrumView.width];
            if (logFreqCheckItem.getState()) {
                // freq = i*rate/(spectrumBuf.length)
                // min frequency = 40 Hz
                for (int i = 0; i != spectrumBuf.length / 2; i += 2) {
                    double f = i / (double) spectrumBuf.length;
                    int ix = (int) (specArray.length * (Math.log(f) - minlog) / logrange);
                    if (ix < 0) {
                        continue;
                    }
                    specArray[ix] += spectrumBuf[i] * spectrumBuf[i]
                            + spectrumBuf[i + 1] * spectrumBuf[i + 1];
                }
            } else {
                for (int i = 0; i != spectrumBuf.length / 2; i += 2) {
                    int ix = specArray.length * i * 2 / spectrumBuf.length;
                    specArray[ix] += spectrumBuf[i] * spectrumBuf[i]
                            + spectrumBuf[i + 1] * spectrumBuf[i + 1];
                }
            }

            int maxi = specArray.length;
            for (int i = 0; i != spectrumView.width; i++) {
                double bf = specArray[i] * bufmult;
                if (logAmpCheckItem.getState()) {
                    bf = -ym * Math.log(bf) / ChebyFilterType.LOG_10;
                } else {
                    bf = 1 - bf;
                }

                int ya = (int) (spectrumView.height * bf);
                if (ya > spectrumView.height) {
                    continue;
                }
                int y = spectrumView.y + ya;
                int x = spectrumView.x + i * spectrumView.width / maxi;
                g.drawLine(x, y, x, spectrumView.y + spectrumView.height - 1);
            }
        }
        if (spectrumView != null && !java2) {
            g.setColor(Color.white);
            centerString(g, "Need java 2 for sound",
                    spectrumView.y + spectrumView.height / 2, winSize.width);
        }

        if (unstable) {
            g.setColor(Color.red);
            centerString(g, "Filter is unstable", winSize.height / 2, winSize.width);
        }
        if (mp3Error != null) {
            g.setColor(Color.red);
            centerString(g, mp3Error, winSize.height / 2 + 20, winSize.width);
        }

        if (respView != null && respView.contains(mouseX, mouseY)) {
            g.setColor(Color.yellow);
            g.drawLine(mouseX, respView.y,
                    mouseX, respView.y + respView.height - 1);
        }
        if (spectrumView != null && spectrumView.contains(mouseX, mouseY)) {
            g.setColor(Color.yellow);
            g.drawLine(mouseX, spectrumView.y,
                    mouseX, spectrumView.y + spectrumView.height - 1);
        }
        filterChanged = false;

        realg.drawImage(dbimage, 0, 0, this);
    }

    void setCutoff(double f) {
    }

    void setCustomPolesZeros() {
        int n =0;
        for (int i = 0; i < filterType.getPoleCount(); i++) {
            Complex c1 = filterType.getPole(i);
            if (c1.getIm() >= 0) {
                customPoles[n++].set(c1);
                customPoles[n++].set(c1.getRe(), -c1.getIm());
                if (n == customPoles.length) {
                    break;
                }
            }
        }
        lastPoleCount = n;
        n = 0;
        for (int i = 0; i < filterType.getZeroCount(); i++) {
            Complex c1 = filterType.getZero(i);
            if (c1.getIm() >= 0) {
                customZeros[n++].set(c1);
                customZeros[n++].set(c1.getRe(), -c1.getIm());
                if (n == customZeros.length) {
                    break;
                }
            }
        }
        lastZeroCount = n;
    }

    double max(double buf[]) {
        int i;
        double max = 0;
        for (i = 0; i != buf.length; i++) {
            double qa = Math.abs(buf[i]);
            if (qa > max) {
                max = qa;
            }
        }
        return max;
    }

    // get freq (from 0 to .5) given an x coordinate
    double getFreqFromX(int x, View v) {
        double f = .5 * (x - v.x) / (double) v.width;
        if (f <= 0 || f >= .5) {
            return -1;
        }
        if (logFreqCheckItem.getState()) {
            return Math.exp(minlog + 2 * f * logrange);
        }
        return f;
    }

    void setupFilter() {
        int filt = filterChooser.getSelectedIndex();
        switch (filt) {
            case 0:
                filterType = new SincLowPassFilter();
                break;
            case 1:
                filterType = new SincHighPassFilter();
                break;
            case 2:
                filterType = new SincBandPassFilter();
                break;
            case 3:
                filterType = new SincBandStopFilter();
                break;
            case 4:
                filterType = new CustomFIRFilter();
                break;
            case 5:
                filterType = new NoFilter();
                break;
            case 6:
                filterType = new ButterLowPass();
                break;
            case 7:
                filterType = new ButterHighPass();
                break;
            case 8:
                filterType = new ButterBandPass();
                break;
            case 9:
                filterType = new ButterBandStop();
                break;
            case 10:
                filterType = new ChebyLowPass();
                break;
            case 11:
                filterType = new ChebyHighPass();
                break;
            case 12:
                filterType = new ChebyBandPass();
                break;
            case 13:
                filterType = new ChebyBandStop();
                break;
            case 14:
                filterType = new InvChebyLowPass();
                break;
            case 15:
                filterType = new InvChebyHighPass();
                break;
            case 16:
                filterType = new InvChebyBandPass();
                break;
            case 17:
                filterType = new InvChebyBandStop();
                break;
            case 18:
                filterType = new EllipticLowPass();
                break;
            case 19:
                filterType = new EllipticHighPass();
                break;
            case 20:
                filterType = new EllipticBandPass();
                break;
            case 21:
                filterType = new EllipticBandStop();
                break;
            case 22:
                filterType = new CombFilter(1);
                break;
            case 23:
                filterType = new CombFilter(-1);
                break;
            case 24:
                filterType = new DelayFilter();
                break;
            case 25:
                filterType = new PluckedStringFilter();
                break;
            case 26:
                filterType = new InverseCombFilter();
                break;
            case 27:
                filterType = new ResonatorFilter();
                break;
            case 28:
                filterType = new ResonatorZeroFilter();
                break;
            case 29:
                filterType = new NotchFilter();
                break;
            case 30:
                filterType = new MovingAverageFilter();
                break;
            case 31:
                filterType = new TriangleFilter();
                break;
            case 32:
                filterType = new AllPassFilter();
                break;
            case 33:
                filterType = new GaussianFilter();
                break;
            case 34:
                filterType = new RandomFilter();
                break;
            case 35:
                filterType = new CustomIIRFilter();
                break;
        }
        if (filterSelection != filt) {
            filterSelection = filt;
            int i;
            for (i = 0; i != auxBars.length; i++) {
                auxBars[i].setMaximum(999);
            }
            int ax = filterType.select(auxBars, auxLabels);
            for (i = 0; i != ax; i++) {
                auxLabels[i].show();
                auxBars[i].show();
            }
            for (i = ax; i != auxBars.length; i++) {
                auxLabels[i].hide();
                auxBars[i].hide();
            }
            if (filterType.needsWindow()) {
                windowChooser.show();
                setWindow();
            } else {
                windowChooser.hide();
                setWindow();
            }
            validate();
        }
        filterType.setup(auxBars, auxLabels);
        curFilter = null;
    }

    void setInputLabel() {
        wformInfo = getWaveformSrc();
        String inText = wformInfo.getInputText();
        if (inText == null) {
            inputLabel.hide();
            inputBar.hide();
        } else {
            inputLabel.setText(inText);
            inputLabel.show();
            inputBar.show();
        }
        validate();
    }

    private Waveform getWaveformSrc() {
        switch (inputChooser.getSelectedIndex()) {
            case 0:
                return new NoiseWaveform();
            case 1:
                return new SineWaveform();
            case 2:
                return new SawtoothWaveform();
            case 3:
                return new TriangleWaveform();
            case 4:
                return new SquareWaveform();
            case 5:
                return new PeriodicNoiseWaveform();
            case 6:
                return new SweepWaveform();
            case 7:
                return new ImpulseWaveform();
            default:
                return new Mp3Waveform(inputChooser.getSelectedIndex() - 8);
        }
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
        cv.repaint(pause);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        handleResize();
        cv.repaint(pause);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitItem) {
            if (applet != null) {
                applet.destroyFrame();
            }
            return;
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        setupFilter();
        System.out.print(((Scrollbar) e.getSource()).getValue() + "\n");
        if ((e.getSource()) == inputBar) {
            setInputW();
        }
        cv.repaint(pause);
    }

    void setInputW() {
        inputW = Math.PI * inputBar.getValue() / 1000.;
    }

    @Override
    public boolean handleEvent(Event ev) {
        if (ev.id == Event.WINDOW_DESTROY) {
            if (playThread != null) {
                playThread.requestShutdown();
            }
            if (applet != null) {
                applet.destroyFrame();
            }
            return true;
        }
        return super.handleEvent(ev);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        edit(e);
        cv.repaint(pause);
    }

    public void mouseMoved(MouseEvent e) {
        dragX = mouseX = e.getX();
        dragY = mouseY = e.getY();
        cv.repaint(pause);
        if (respView != null && respView.contains(e.getX(), e.getY())) {
            selection = SELECT_RESPONSE;
        }
        if (spectrumView != null && spectrumView.contains(e.getX(), e.getY())) {
            selection = SELECT_SPECTRUM;
        }
        if (polesView != null && polesView.contains(e.getX(), e.getY())
                && !ferrisCheckItem.getState()) {
            selection = SELECT_POLES;
            selectPoleZero(e.getX(), e.getY());
        }
    }

    void selectPoleZero(int x, int y) {
        selectedPole = selectedZero = -1;
        int ph = polesView.height / 2;
        int pw = ph;
        int cx = polesView.x + pw;
        int cy = polesView.y + ph;
        int bestdist = 10000;
        for (int i = 0; i < filterType.getPoleCount(); i++) {
            Complex c1 = filterType.getPole(i);
            int c1x = cx + (int) (pw * c1.getRe());
            int c1y = cy - (int) (ph * c1.getIm());
            int dist = distanceSq(c1x, c1y, x, y);
            if (dist <= bestdist) {
                bestdist = dist;
                selectedPole = i;
                selectedZero = -1;
            }
        }
        for (int i = 0; i < filterType.getZeroCount(); i++) {
            Complex c1 = filterType.getZero(i);
            int c1x = cx + (int) (pw * c1.getRe());
            int c1y = cy - (int) (ph * c1.getIm());
            int dist = distanceSq(c1x, c1y, x, y);
            if (dist < bestdist) {
                bestdist = dist;
                selectedPole = -1;
                selectedZero = i;
            }
        }
    }

    int distanceSq(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
        edit(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    void edit(MouseEvent e) {
        if (selection == SELECT_RESPONSE) {
            if (filterType instanceof CustomFIRFilter) {
                editCustomFIRFilter(e);
                return;
            }
            double f = getFreqFromX(e.getX(), respView);
            if (f < 0) {
                return;
            }
            filterType.setCutoff(f, auxBars, auxLabels);
            setupFilter();
        }
        if (selection == SELECT_SPECTRUM) {
            if (!wformInfo.needsFrequency()) {
                return;
            }
            double f = getFreqFromX(e.getX(), spectrumView);
            if (f < 0) {
                return;
            }
            inputW = 2 * Math.PI * f;
            inputBar.setValue((int) (2000 * f));
        }
        if (selection == SELECT_POLES && filterType instanceof CustomIIRFilter) {
            editCustomIIRFilter(e);
            return;
        }
    }

    void editCustomFIRFilter(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (dragX == x) {
            editCustomFIRFilterPoint(x, y);
            dragY = y;
        } else {
            // need to draw a line from old x,y to new x,y and
            // call editFuncPoint for each point on that line.  yuck.
            int x1 = (x < dragX) ? x : dragX;
            int y1 = (x < dragX) ? y : dragY;
            int x2 = (x > dragX) ? x : dragX;
            int y2 = (x > dragX) ? y : dragY;
            dragX = x;
            dragY = y;
            for (x = x1; x <= x2; x++) {
                y = y1 + (y2 - y1) * (x - x1) / (x2 - x1);
                editCustomFIRFilterPoint(x, y);
            }
        }
        setupFilter();
    }

    void editCustomFIRFilterPoint(int x, int y) {
        double xx1 = getFreqFromX(x, respView) * 2;
        double xx2 = getFreqFromX(x + 1, respView) * 2;
        y -= respView.y;
        double ym = .069;
        double yy = Math.exp(-y * Math.log(10) / (ym * 4 * respView.height));
        if (yy >= 1) {
            yy = 1;
        }
        ((CustomFIRFilter) filterType).edit(xx1, xx2, yy);
    }

    void editCustomIIRFilter(MouseEvent e) {
        if (ferrisCheckItem.getState()) {
            return;
        }
        int x = e.getX();
        int y = e.getY();
        int ph = polesView.height / 2;
        int pw = ph;
        int cx = polesView.x + pw;
        int cy = polesView.y + ph;
        Complex c1 = new Complex();
        c1.set((x - cx) / (double) pw, (y - cy) / (double) ph);
        ((CustomIIRFilter) filterType).editPoleZero(selectedPole, c1);
        setCustomPolesZeros();
        setupFilter();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        filterChanged = true;
        if (e.getSource() == displayCheck) {
            cv.repaint(pause);
            return;
        }
        if (e.getSource() == inputChooser) {
            if (playThread != null) {
                playThread.requestShutdown();
            }
            setInputLabel();
        }
        if ((e.getSource()) == rateChooser) {
            if (playThread != null) {
                playThread.requestShutdown();
            }
            inputW *= sampleRate;
            switch (rateChooser.getSelectedIndex()) {
                case 0:
                    sampleRate = 8000;
                    break;
                case 1:
                    sampleRate = 11025;
                    break;
                case 2:
                    sampleRate = 16000;
                    break;
                case 3:
                    sampleRate = 22050;
                    break;
                case 4:
                    sampleRate = 32000;
                    break;
                case 5:
                    sampleRate = 44100;
                    break;
            }
            inputW /= sampleRate;
        }
        if ((e.getSource()) == shiftSpectrumCheck) {
            if (shiftSpectrumCheck.getState()) {
                shiftFreqLabel.show();
                shiftFreqBar.show();
            } else {
                shiftFreqLabel.hide();
                shiftFreqBar.hide();
            }
            validate();
        }
        if ((e.getSource()) == windowChooser) {
            setWindow();
        }
        if (e.getSource() instanceof CheckboxMenuItem) {
            handleResize();
        } else {
            setupFilter();
        }
        cv.repaint(pause);
    }

    void setWindow() {
        if (windowChooser.getSelectedIndex() == WINDOW_KAISER
                && filterType.needsWindow()) {
            kaiserLabel.show();
            kaiserBar.show();
        } else {
            kaiserLabel.hide();
            kaiserBar.hide();
        }
        validate();
    }

    void setSampleRate(int r) {
        int x = 0;
        switch (r) {
            case 8000:
                x = 0;
                break;
            case 11025:
                x = 1;
                break;
            case 16000:
                x = 2;
                break;
            case 22050:
                x = 3;
                break;
            case 32000:
                x = 4;
                break;
            case 44100:
                x = 5;
                break;
        }
        rateChooser.select(x);
        sampleRate = r;
    }

    class PlayThread extends Thread {

        SourceDataLine line;
        Waveform wform;
        boolean shutdownRequested;
        boolean stereo;
        Filter filt, newFilter;
        double[] filterInBuf;
        double[] filterOutBuff;
        final int BUFF_SIZE = 32768;
        final int BUFF_MASK = BUFF_SIZE -1;
        int spectrumOffset, spectrumLen;

        PlayThread() {
            shutdownRequested = false;
        }

        void requestShutdown() {
            shutdownRequested = true;
        }

        void setFilter(Filter f) {
            newFilter = f;
        }

        void openLine() {
            try {
                stereo = (wform.getChannels() == 2);
                AudioFormat playFormat =
                        new AudioFormat(sampleRate, 16, 2, true, false);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                        playFormat);

                if (!AudioSystem.isLineSupported(info)) {
                    throw new LineUnavailableException(
                            "sorry, the sound format cannot be played");
                }
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(playFormat, Waveform.getPower2(sampleRate / 4));
                line.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int inbp, outbp;
        int spectCt;

        @Override
        public void run() {
            try {
                doRun();
            } catch (Exception e) {
                e.printStackTrace();
            }
            playThread = null;
        }

        void doRun() {
            rateChooser.enable();
            wform = getWaveformSrc();
            mp3Error = null;
            unstable = false;
            if (!wform.start(sampleRate)) {
                cv.repaint();
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                }
                return;
            }

            filterInBuf = new double[BUFF_SIZE];
            filterOutBuff = new double[BUFF_SIZE];
            openLine();
            inbp = outbp = spectCt = 0;
            int ss = (stereo) ? 2 : 1;
            outputGain = 1;
            newFilter = filt = curFilter;
            spectrumLen = Waveform.getPower2(sampleRate / 12);
            int gainCounter = 0;
            boolean maxGain = true;
            boolean useConvolve = false;

            ob = new byte[16384];
            int shiftCtr = 0;
            while (!shutdownRequested && soundCheck.getState()
                    && (applet != null ? applet.ogf != null : true)) {
                //System.out.println("nf " + newFilter + " " +(inbp-outbp));
                if (newFilter != null) {
                    gainCounter = 0;
                    maxGain = true;
                    if (wform instanceof SweepWaveform
                            || wform instanceof SineWaveform) {
                        maxGain = false;
                    }
                    outputGain = 1;
                    // we avoid doing this unless necessary because it sounds bad
                    if (filt == null || filt.getLength() != newFilter.getLength()) {
                        convBufPtr = inbp = outbp = spectCt = 0;
                    }
                    filt = newFilter;
                    newFilter = null;
                    impulseBuf = null;
                    useConvolve = filt.useConvolve();
                }
                int length = wform.getData(logFreqCheckItem.getState(), inputBar.getValue(), inputW);
                if (length == 0) {
                    break;
                }
                short ib[] = wform.buffer;

                int i = inbp;
                for (int i2 = 0; i2 < length; i2 += ss) {
                    filterInBuf[i] = ib[i2];
                    i = (i + 1) & BUFF_MASK;
                }
                i = inbp;
                if (stereo) {
                    for (int i2 = 0; i2 < length; i2 += 2) {
//                        fbufRi[i] = ib[i2 + 1];
                        i = (i + 1) & BUFF_MASK;
                    }
                } else {
                    for (int i2 = 0; i2 < length; i2++) {
  //                      fbufRi[i] = fbufLi[i];
                        i = (i + 1) & BUFF_MASK;
                    }
                }
                if (shiftSpectrumCheck.getState()) {
                    double shiftFreq = shiftFreqBar.getValue() * Math.PI / 1000.;
                    if (shiftFreq > Math.PI) {
                        shiftFreq = Math.PI;
                    }
                    i = inbp;
                    for (int i2 = 0; i2 < length; i2 += ss) {
                        double q = Math.cos(shiftFreq * shiftCtr++);
                        filterInBuf[i] *= q;
//                        fbufRi[i] *= q;
                        i = (i + 1) & BUFF_MASK;
                    }
                }

                int sampleCount = length / ss;
                if (useConvolve) {
                    doConvolveFilter(sampleCount, maxGain);
                } else {
                    doFilter(sampleCount);
                    if (unstable) {
                        break;
                    }
                    int outlen = sampleCount * 4;
                    doOutput(outlen, maxGain);
                }

                if (unstable) {
                    break;
                }

                if (spectCt >= spectrumLen) {
                    spectrumOffset = (outbp - spectrumLen) & BUFF_MASK;
                    spectCt -= spectrumLen;
                    cv.repaint();
                }
                gainCounter += sampleCount;
                if (maxGain && gainCounter >= sampleRate) {
                    gainCounter = 0;
                    maxGain = false;
                    //System.out.println("gain ctr up " + outputGain);
                }
            }
            if (shutdownRequested || unstable || !soundCheck.getState()) {
                line.flush();
            } else {
                line.drain();
            }
            cv.repaint();
        }

        void doFilter(int sampleCount) {
            filt.run(filterInBuf, filterOutBuff, inbp, BUFF_MASK, sampleCount);
//            filt.run(fbufRi, fbufRo, inbp, BUFF_MASK, sampleCount, stateR);
            inbp = (inbp + sampleCount) & BUFF_MASK;
            double q = filterOutBuff[(inbp - 1) & BUFF_MASK];
            if (Double.isNaN(q) || Double.isInfinite(q)) {
                unstable = true;
            }
        }
        double impulseBuf[], convolveBuf[];
        int convBufPtr;
        FFT convFFT;

        void doConvolveFilter(int sampleCount, boolean maxGain) {
            int fi2 = inbp, i20;
            double filtA[] = ((DirectFilter) filt).copyA();
            int cblen = Waveform.getPower2(512 + filtA.length * 2);
            if (convolveBuf == null || convolveBuf.length != cblen) {
                convolveBuf = new double[cblen];
            }
            if (impulseBuf == null) {
                // take FFT of the impulse response
                impulseBuf = new double[cblen];
                for (int i = 0; i != filtA.length; i++) {
                    impulseBuf[i * 2] = filtA[i];
                }
                convFFT = new FFT(convolveBuf.length / 2);
                convFFT.fft(impulseBuf);
            }
            int cbptr = convBufPtr;
            // result = impulseLen+inputLen-1 samples long; result length
            // is fixed, so use it to get inputLen
            int cbptrmax = convolveBuf.length + 2 - 2 * filtA.length;
            //System.out.println("reading " + sampleCount);
            for (int i = 0; i < sampleCount; i++, fi2++) {
                i20 = fi2 & BUFF_MASK;
                convolveBuf[cbptr] = filterInBuf[i20];
//                convolveBuf[cbptr + 1] = fbufRi[i20];
                convolveBuf[cbptr + 1] = filterInBuf[i20];
                cbptr += 2;
                if (cbptr == cbptrmax) {
                    // buffer is full, do the transform
                    convFFT.fft(convolveBuf);
                    double mult = 2. / cblen;
                    // multiply transforms to get convolution
                    for (int j = 0; j != cblen; j += 2) {
                        double a = convolveBuf[j] * impulseBuf[j]
                                - convolveBuf[j + 1] * impulseBuf[j + 1];
                        double b = convolveBuf[j] * impulseBuf[j + 1]
                                + convolveBuf[j + 1] * impulseBuf[j];
                        convolveBuf[j] = a * mult;
                        convolveBuf[j + 1] = b * mult;
                    }
                    // inverse transform to get signal
                    convFFT.fft(convolveBuf);
                    int fj2 = outbp, j20;
                    int overlap = cblen - cbptrmax;
                    // generate output that overlaps with old data
                    int j;
                    for (j = 0; j < overlap; j += 2, fj2++) {
                        j20 = fj2 & BUFF_MASK;
                        filterOutBuff[j20] += convolveBuf[j];
//                        fbufRo[j20] += convolveBuf[j + 1];
                    }
                    // generate new output
                    for (; j != cblen; j += 2, fj2++) {
                        j20 = fj2 & BUFF_MASK;
                        filterOutBuff[j20] = convolveBuf[j];
//                        fbufRo[j20] = convolveBuf[j + 1];
                    }
                    cbptr = 0;
                    // output the sound
                    doOutput(cbptrmax * 2, maxGain);
                    //System.out.println("outputting " + cbptrmax);
                    // clear transform buffer
                    for (j = 0; j < cblen; j++) {
                        convolveBuf[j] = 0;
                    }
                }
            }
            inbp = fi2 & BUFF_MASK;
            convBufPtr = cbptr;
        }
        byte ob[];

        void doOutput(int outlen, boolean maxGain) {
            if (ob.length < outlen) {
                ob = new byte[outlen];
            }
            int qi;
            int i, i2;
            while (true) {
                int max = 0;
                i = outbp;
                for (i2 = 0; i2 < outlen; i2 += 4) {
                    qi = (int) (filterOutBuff[i] * outputGain);
                    if (qi > max) {
                        max = qi;
                    }
                    if (qi < -max) {
                        max = -qi;
                    }
                    ob[i2 + 1] = (byte) (qi >> 8);
                    ob[i2] = (byte) qi;
                    i = (i + 1) & BUFF_MASK;
                }
                i = outbp;
                for (i2 = 2; i2 < outlen; i2 += 4) {
//                    qi = (int) (fbufRo[i] * outputGain);
                    qi = (int) (filterOutBuff[i] * outputGain);
                    if (qi > max) {
                        max = qi;
                    }
                    if (qi < -max) {
                        max = -qi;
                    }
                    ob[i2 + 1] = (byte) (qi >> 8);
                    ob[i2] = (byte) qi;
                    i = (i + 1) & BUFF_MASK;
                }
                // if we're getting overflow, adjust the gain
                if (max > 32767) {
                    //System.out.println("max = " + max);
                    outputGain *= 30000. / max;
                    if (outputGain < 1e-8 || Double.isInfinite(outputGain)) {
                        unstable = true;
                        break;
                    }
                    continue;
                } else if (maxGain && max < 24000) {
                    if (max == 0) {
                        if (outputGain == 1) {
                            break;
                        }
                        outputGain = 1;
                    } else {
                        outputGain *= 30000. / max;
                    }
                    continue;
                }
                break;
            }
            if (unstable) {
                return;
            }
            int oldoutbp = outbp;
            outbp = i;

            line.write(ob, 0, outlen);
            spectCt += outlen / 4;
        }
    }
    Complex customPoles[], customZeros[];
}
