/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.fms4j.console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import de.ibapl.dsp4j.datatypes._double.FmSquelch;
import de.ibapl.dsp4j.datatypes._short.MonoShortFileSink;
import de.ibapl.dsp4j.datatypes._short.MonoShortFileSource;
import de.ibapl.dsp4j.datatypes._short.MonoShortTargetDataLineWrapper;
import de.ibapl.dsp4j.datatypes._short.ShortFileSource;
import de.ibapl.dsp4j.fms4j.fms.FmsContainer;
import de.ibapl.dsp4j.fms4j.fms.FmsContainerListener;
import de.ibapl.dsp4j.fms4j.fms.FmsData;
import de.ibapl.dsp4j.fms4j.fms.FmsFileDataStore;
import de.ibapl.dsp4j.fms4j.zvei.ZveiContainer;
import de.ibapl.dsp4j.fms4j.zvei.ZveiFolgeContainerListener;
import de.ibapl.dsp4j.fms4j.zvei.ZveiFreqTable;

/**
 *
 * @author aploese
 */
public class Decoder {

    private class FmsConsolePrinter implements FmsContainerListener {

        private void printFmsData(FmsData data) {
            pw.print(data.getDienstkennung());
            pw.print(", ");
            pw.print(data.getLaenderkennung());
            pw.print(", ");
            pw.print(ds.getFmsOrtsmname(data));
            pw.print(", ");
            pw.print(ds.getFmsFahrzeugname(data));
            pw.print(", ");
            pw.print(data.getMsg());
            pw.print(", ");
            pw.print(data.getStatus());
            pw.print(", ");
            pw.print(data.getRichtungskennung());
            pw.print(", ");
            pw.print(data.getBaustufenkennung());
            pw.print(", ");
            pw.print(data.getTaktischeKurzinfirmation());
            if (data.getText() != null) {
                pw.print(", \"");
                pw.print(data.getText());
                pw.print("\"");
            } else {
                pw.print(", \"\"");
            }
            pw.println();
            pw.flush();
        }

        @Override
        public void success(FmsData data) {
            pw.printf("FMS[%d]: @%s: ", fmsIndex++, getTimeStr());
            printFmsData(data);
        }

        @Override
        public boolean error(FmsData fmsData) {
            return false;
        }

        @Override
        public boolean crcError(FmsData fmsData) {
//            pw.printf("Crc Error FMS: @%s: ", getTimeStr());
//            printFmsData(fmsData);
            return true;
        }

        @Override
        public boolean txtLengthParityError(FmsData fmsData) {
            pw.printf("Text Length Parity Error FMS: @%s: ", getTimeStr());
            printFmsData(fmsData);
            return false;
        }

        @Override
        public boolean txtCharParityError(FmsData fmsData, String txt) {
            pw.printf("Text Char Parity Error FMS: @%s: ", getTimeStr());
            printFmsData(fmsData);
            pw.printf("Text: %s\n", txt);
            return false;
        }

        @Override
        public boolean txtCrcError(FmsData fmsData, String txt) {
            pw.printf("Text Crc Error FMS: @%s: ", getTimeStr());
            printFmsData(fmsData);
            pw.printf("Text: %s\n", txt);
            return false;
        }

        @Override
        public boolean txtError(FmsData fmsData, String txt) {
            pw.printf("Text Error FMS: @%s: ", getTimeStr());
            printFmsData(fmsData);
            pw.println(txt);
            return true;
        }
    }

    private class ZveiConsolePrinter implements ZveiFolgeContainerListener {

        @Override
        public void success(ZveiFreqTable[] data) {
            pw.printf("ZVEI[%d]: @%s: ", zveiIndex++, getTimeStr());
            pw.append(ZveiFreqTable.toString(data));
            pw.print(", ");
            pw.print(ds.getZveiname(ZveiFreqTable.toId(data)));
            pw.println();
            pw.flush();
        }

        @Override
        public void fail(ZveiFreqTable[] data) {
            /*
             System.out.print(new Date() + " | ZVEI ERR: @" + sc.getTimeStr()+ " ");
             for (int i = 0; i < data.length; i++) {
             System.out.print(data[i]);
             if (i <  data.length - 1) {
             System.out.print(", ");
             }
             }
             System.out.println(" ");
             */
        }
    }

    public static String getTimeStr() {
        return String.format("%1$TF %1$TT.%1$TL", System.currentTimeMillis());
    }

    private final FmSquelch fmSquech;
    private final FmsContainer fmsContainer;
    private final ZveiContainer zveiContainer;
    private final FmsConsolePrinter fmsConsolePrinter;
    private final ZveiConsolePrinter zveiConsolePrinter;
    private MonoShortFileSink fs;
    private String inputFilename;
    private final FmsFileDataStore ds;
    private final PrintWriter pw;
    private final File backupDir;
    private boolean showBlocking = false;
    private int fmsIndex;
    private int zveiIndex;
    private boolean oldSquelch;

    private void processSampleBackup(double yd, short ys) {
        if (fmSquech.setX(yd)) {
            if (!oldSquelch) {
                fs.setWavOut(new File(backupDir, "FMS-" + (inputFilename != null ? inputFilename : "lineIn_" + fmSquech.getSampleRate()) + "_" + getTimeStr().replace(" ", "_") + ".wav"));
                if (showBlocking) {
                    System.out.println("PASSING: @" + new Date());
                }
                fmsContainer.reset();
                zveiContainer.reset();
                fs.setX(ys);
            } else {
                fs.setX(ys);
            }
            oldSquelch = true;
        } else {
            if (oldSquelch) {
                fs.setX(ys);
                fs.close();
                if (showBlocking) {
                    System.out.println("BLOCKING: @" + getTimeStr());
                }
            }
            oldSquelch = false;
            return;
        }
        fmsContainer.setX(yd);
        zveiContainer.setX(yd);
    }

    private void processSampleWoBackup(double y) {
        if (fmSquech.setX(y)) {
            if (!oldSquelch) {
                if (showBlocking) {
                    System.out.println("PASSING: @" + new Date());
                }
                fmsContainer.reset();
                zveiContainer.reset();
            }
            oldSquelch = true;
        } else {
            if (oldSquelch && showBlocking) {
                    System.out.println("BLOCKING: @" + getTimeStr());
            }
            oldSquelch = false;
            return;
        }
        fmsContainer.setX(y);
        zveiContainer.setX(y);
    }

    private void setSampleRate(float sampleRate) {
        if (fs != null) {
            fs.setSampleRate(sampleRate);
        }
        fmSquech.setSampleRate(sampleRate);
        fmsContainer.setSampleRate(sampleRate);
        zveiContainer.setSampleRate(sampleRate);
    }


//    final FixedPointType fixedPointType;
    public void decodeMonoFile(File file) throws IOException, UnsupportedAudioFileException {
        inputFilename = file.getName();
        inputFilename = inputFilename.substring(0, inputFilename.indexOf(".wav"));
        pw.println(new Date() + " | decode file: " + file.getAbsolutePath());
        MonoShortFileSource aiss = new MonoShortFileSource(file, 1024 * 16);
        setSampleRate(aiss.getSampleRate());
        if (backupDir != null) {
            while (aiss.clock()) {
                final short y = aiss.getY();
                processSampleBackup(y, y);
            }
        } else {
            while (aiss.clock()) {
                processSampleWoBackup(aiss.getY());
            }
        }
        pw.println(new Date() + " | decoded file: " + file.getAbsolutePath());
    }

    public void decodeFile(String filename, int channel) throws IOException, UnsupportedAudioFileException {
        inputFilename = new File(filename).getName();
        inputFilename = inputFilename.substring(0, inputFilename.indexOf(".wav"));
        pw.println(new Date() + " | decode file: " + filename);
        ShortFileSource aiss = new ShortFileSource(new File(filename), 1024);
        setSampleRate(aiss.getSampleRate());
        if (backupDir != null) {
            while (aiss.clock()) {
                final short y = aiss.getY(channel);
                processSampleBackup(y, y);
            }
        } else {
            while (aiss.clock()) {
                processSampleWoBackup(aiss.getY(channel));
            }
        }
        pw.println(new Date() + " | decoded file: " + filename);
    }

    public void decodeDefaultAudioIn(double samplerate) throws IOException, LineUnavailableException {

        MonoShortTargetDataLineWrapper ds = new MonoShortTargetDataLineWrapper(samplerate);


        pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

        setSampleRate(ds.getSampleRate());
        if (backupDir != null) {
            while (ds.clock()) {
                final short y = ds.getY();
                processSampleBackup(y, y);
            }
        } else {
            while (ds.clock()) {
                processSampleWoBackup(ds.getY());
            }
        }
        pw.println(new Date() + " | audio decoded");
    }

    public void decodeAudioIn(String mixerName, double samplerate) throws IOException, LineUnavailableException {
        Mixer.Info m = null;
        for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
            if (mi.getName().equals(mixerName)) {
                m = mi;
            }
        }

        if (m == null) {
            throw new RuntimeException("Cant find mixer: " + mixerName);
        }

        MonoShortTargetDataLineWrapper ds = new MonoShortTargetDataLineWrapper(m, samplerate, (int)(samplerate / 10)); // 100ms will do


        pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

        setSampleRate(ds.getSampleRate());
        if (backupDir != null) {
            while (ds.clock()) {
                final short y = ds.getY();
                processSampleBackup(y, y);
            }
        } else {
            while (ds.clock()) {
                processSampleWoBackup(ds.getY());
            }
        }
        pw.println(new Date() + " | audio decoded");
    }

    public void printPowerDefaultAudioIn(double samplerate) throws IOException, LineUnavailableException {

        MonoShortTargetDataLineWrapper ds = new MonoShortTargetDataLineWrapper(samplerate);


        pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

        setSampleRate(ds.getSampleRate());
        while (ds.clock()) {
            fmSquech.setX(ds.getY());
            System.out.println("Power for Squelch: " + fmSquech.getPower());
        }
        pw.println(new Date() + " | audio decoded");
    }

    public void printPowerAudioIn(String mixerName, double samplerate) throws IOException, LineUnavailableException {
        Mixer.Info m = null;
        for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
            if (mi.getName().equals(mixerName)) {
                m = mi;
            }
        }

        if (m == null) {
            throw new RuntimeException("Cant find mixer: " + mixerName);
        }

        MonoShortTargetDataLineWrapper ds = new MonoShortTargetDataLineWrapper(m, samplerate);


        pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

        setSampleRate(ds.getSampleRate());
        while (ds.clock()) {
            fmSquech.setX(ds.getY());
            System.out.println("Power for Squelch: " + fmSquech.getPower());
        }
        pw.println(new Date() + " | audio decoded");
    }

    public Decoder(File backupDir, File fms32Dir, File logFile, short squelchThreshold) {
        ds = new FmsFileDataStore();
        try {
            ds.read(fms32Dir);
        } catch (Exception e) {
        }
        fmSquech = new FmSquelch(squelchThreshold, 10, 3600); 
        fmsConsolePrinter = new FmsConsolePrinter();
        fmsContainer = new FmsContainer(fmsConsolePrinter);
        zveiConsolePrinter = new ZveiConsolePrinter();
        zveiContainer = new ZveiContainer(zveiConsolePrinter);
 
        // fancy stuff
        this.backupDir = backupDir;
        if (backupDir != null) {
            fs = new MonoShortFileSink(new File(backupDir, "fmsconsole.wav"), 11025);
        }
        if (logFile != null) {
            try {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            pw = new PrintWriter(System.out);
        }
    }
}
