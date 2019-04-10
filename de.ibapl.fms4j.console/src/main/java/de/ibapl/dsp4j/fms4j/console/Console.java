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

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aploese
 */
public class Console {

    private static final String CMDL_OPT_HELP = "help";
    private static final String CMDL_OPT_VERSION = "version";
    private static final String CMDL_OPT_PRINT_MIXER_INFO = "print-mixer-info";
    private static final String CMDL_OPT_PRINT_LINE_INFO = "print-line-info";
    private static final String CMDL_OPT_USE_MIXER = "use-mixer";
    private static final String CMDL_OPT_SAMPLERATE = "samplerate";
    private static final String CMDL_OPT_IN_FILE = "read-from-file";
    private static final String CMDL_OPT_IN_DIR = "read-from-dir";
    private static final String CMDL_OPT_FMS32_DIR = "fms32-dir";
    private static final String CMDL_OPT_BACKUP_WAV = "backup-wav";
    private static final String CMDL_OPT_LOG_FILE = "log to file";
    private static final String CMDL_OPT_SQUELCH_THRESHOLD = "squelch-threshold";
    private static final String CMDL_OPT_PRINT_POWER = "print-power";

    public static void main(String[] args) throws Exception {
        Console c = new Console();
        c.parseCmdLine(args);
    }

    static void printMixerInfo(String mixerName, boolean printLineInfo) throws LineUnavailableException {
        Mixer.Info[] mixer = AudioSystem.getMixerInfo();
        for (Mixer.Info i : mixer) {
            if (i.getName().equals(mixerName) || mixerName == null) {
                System.out.println("Mixer = {");
                System.out.println(String.format(" name=\"%s\"\n desc=\"%s\"\n vendor=\"%s\"\n version=\"%s\"", i.getName(), i.getDescription(), i.getVendor(), i.getVersion()));
                if (printLineInfo) {
                    Mixer m = AudioSystem.getMixer(i);

                    System.out.println("  Source Lines = {");
                    printLineInfo(m, true);
                    System.out.println("  }");
                    System.out.println("  Target Lines = {");
                    printLineInfo(m, false);
                    System.out.println("  }");
                }
                System.out.println("}");
            }
        }
    }

    private static void printLineInfo(Mixer m, boolean isSource) throws LineUnavailableException {
        Info[] li;
        if (isSource) {
            li = m.getSourceLineInfo();
        } else {
            li = m.getTargetLineInfo();
        }
        for (Line.Info i : li) {
            if (i instanceof DataLine.Info) {
                DataLine.Info dli = (DataLine.Info) i;
                System.out.println("   Data Line = {");
                System.out.println("    name = \"" + i.toString() + "\"");
                Line line;
                if (isSource) {
                    line = AudioSystem.getSourceDataLine(dli.getFormats()[0], m.getMixerInfo());
                } else {
                    line = AudioSystem.getTargetDataLine(dli.getFormats()[0], m.getMixerInfo());
                }
                printControls(line);

                System.out.println("    AudioFormats = {");
                for (AudioFormat af : dli.getFormats()) {
                    System.out.println("     " + af.toString());
                }
            } else if (i instanceof Port.Info) {
                Port.Info pi = (Port.Info) i;
                System.out.println("   Port = {");
                System.out.println("    name = \"" + i.toString() + "\"");
                Line line = m.getLine(pi);
                printControls(line);
            } else {
                System.out.println("Unknown Line" + i);
            }
            System.out.println("   }");

        }
    }

    private static void printControls(Line line) throws LineUnavailableException {
        line.open();
        System.out.println("    Controls = {");
        for (Control c : line.getControls()) {
            if (c instanceof CompoundControl) {
                for (Control c0 : ((CompoundControl) c).getMemberControls()) {
                    System.out.println("     " + c0.toString());
                }
            } else {
                System.out.println("     " + c.toString());
            }
        }
        line.close();
        System.out.println("    }");
    }

    private Options buildCmdlOptions() {
        Options options = new Options();
        Option opt;
        OptionGroup optg;

        opt = new Option("h", CMDL_OPT_HELP, false, "print this help message");
        options.addOption(opt);

        opt = new Option("v", CMDL_OPT_VERSION, false, "print version");
        options.addOption(opt);

        optg = new OptionGroup();

        opt = new Option("pm", CMDL_OPT_PRINT_MIXER_INFO, false, "print mixer info and exit");
        optg.addOption(opt);

        opt = new Option("pl", CMDL_OPT_PRINT_LINE_INFO, true, "print line info and exit");
        opt.setArgName("mixer");
        opt.setType(String.class);
        optg.addOption(opt);

        opt = new Option("sr", CMDL_OPT_SAMPLERATE, true, "set samplerate and use input");
        opt.setArgName("samplerate");
        opt.setType(Double.class);
        optg.addOption(opt);

        opt = new Option("f", CMDL_OPT_IN_FILE, true, "process file");
        opt.setArgName("file");
        opt.setType(String.class);
        optg.addOption(opt);

        opt = new Option("d", CMDL_OPT_IN_DIR, true, "process dir");
        opt.setArgName("dir");
        opt.setType(String.class);
        optg.addOption(opt);

        options.addOptionGroup(optg);

        opt = new Option("m", CMDL_OPT_USE_MIXER, true, "use mixer");
        opt.setArgName("mixer");
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option("bw", CMDL_OPT_BACKUP_WAV, true, "backup wav dir");
        opt.setArgName("backup dir");
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option("fms", CMDL_OPT_FMS32_DIR, true, "frms32 dir");
        opt.setArgName("fms32 dir");
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option("l", CMDL_OPT_LOG_FILE, true, "frms32 dir");
        opt.setArgName("log file");
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option("sq", CMDL_OPT_SQUELCH_THRESHOLD, true, "squelch threshold");
        opt.setArgName("value");
        opt.setType(Short.class);
        options.addOption(opt);

        opt = new Option("pp", CMDL_OPT_PRINT_POWER, false, "print power (for adjusting squelch)");
        options.addOption(opt);

        return options;
    }

    private void printHelp(Options opts) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(300);
        formatter.printHelp("fmsconsole", opts);
    }

    public void parseCmdLine(String... args) throws ParseException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        Options opts = buildCmdlOptions();
        Properties appProps = new Properties();
//        try {
//            appProps.load(Console.class.getResourceAsStream("app.properties"));
//        } catch (IOException ex) {
//            throw new RuntimeException("Cant read prop file", ex);
//        }

        CommandLineParser cmdParser = new PosixParser();
        CommandLine cml = cmdParser.parse(opts, args);
        if (cml.hasOption(CMDL_OPT_HELP)) {
            printHelp(opts);
            return;
        }

        if (cml.hasOption(CMDL_OPT_PRINT_MIXER_INFO)) {
            printMixerInfo(null, false);
            return;
        }

        if (cml.hasOption(CMDL_OPT_PRINT_LINE_INFO)) {
            String m = cml.getOptionValue(CMDL_OPT_PRINT_LINE_INFO);
            printMixerInfo(m, true);
            return;
        }

        File fms32Dir = null;
        if (cml.hasOption(CMDL_OPT_FMS32_DIR)) {
            fms32Dir = new File(cml.getOptionValue(CMDL_OPT_FMS32_DIR));
        }

        File backupDir = null;
        if (cml.hasOption(CMDL_OPT_BACKUP_WAV)) {
            backupDir = new File(cml.getOptionValue(CMDL_OPT_BACKUP_WAV));
        }

        File logFile = null;
        if (cml.hasOption(CMDL_OPT_LOG_FILE)) {
            logFile = new File(cml.getOptionValue(CMDL_OPT_LOG_FILE));
        }

        short squelchTreshold = 1000;
        if (cml.hasOption(CMDL_OPT_SQUELCH_THRESHOLD)) {
            squelchTreshold = Short.parseShort(cml.getOptionValue(CMDL_OPT_SQUELCH_THRESHOLD));
        }
        Decoder d = new Decoder(backupDir, fms32Dir, logFile, squelchTreshold);

        if (cml.hasOption(CMDL_OPT_PRINT_POWER)) {
            if (cml.hasOption(CMDL_OPT_SAMPLERATE)) {
                double sr = Double.parseDouble(cml.getOptionValue(CMDL_OPT_SAMPLERATE));
                if (cml.hasOption(CMDL_OPT_USE_MIXER)) {
                    d.printPowerAudioIn(cml.getOptionValue(CMDL_OPT_USE_MIXER), sr);
                } else {
                    d.printPowerDefaultAudioIn(sr);
                }
                return;
            }
        }

        if (cml.hasOption(CMDL_OPT_SAMPLERATE)) {
            double sr = Double.parseDouble(cml.getOptionValue(CMDL_OPT_SAMPLERATE));
            if (cml.hasOption(CMDL_OPT_USE_MIXER)) {
                d.decodeAudioIn(cml.getOptionValue(CMDL_OPT_USE_MIXER), sr);
            } else {
                d.decodeDefaultAudioIn(sr);
            }
            return;
        }

        if (cml.hasOption(CMDL_OPT_IN_FILE)) {
            String f = cml.getOptionValue(CMDL_OPT_IN_FILE);
            d.decodeMonoFile(new File(f));
            return;
        }

        if (cml.hasOption(CMDL_OPT_IN_DIR)) {
            String f = cml.getOptionValue(CMDL_OPT_IN_DIR);
            File dir = new File(f);
            for (File in : dir.listFiles()) {
                if (in.getName().endsWith(".wav")) {
                    try {
                        d.decodeMonoFile(in);
                    } catch (Exception ex) {
                        System.err.println("file " + in.getAbsolutePath() + " E: " + ex);
                    }
                }
            }
            return;
        }
        printHelp(opts);
    }
}
