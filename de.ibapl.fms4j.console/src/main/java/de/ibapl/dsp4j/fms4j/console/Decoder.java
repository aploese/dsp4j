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
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import de.ibapl.dsp4j.datatypes._short.ShortSampledSource;
import de.ibapl.dsp4j.datatypes._short.ShortTargetDataLineWrapper;
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
		}

		@Override
		public void success(FmsData data) {
			pw.printf("FMS[%d]: @%s: ", fmsIndex++, getTimeStr());
			printFmsData(data);
			pw.flush();
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
			pw.flush();
			return false;
		}

		@Override
		public boolean txtCharParityError(FmsData fmsData, String txt) {
			pw.printf("Text Char Parity Error FMS: @%s: ", getTimeStr());
			printFmsData(fmsData);
			pw.printf("Text: %s\n", txt);
			pw.flush();
			return false;
		}

		@Override
		public boolean txtCrcError(FmsData fmsData, String txt) {
			pw.printf("Text Crc Error FMS: @%s: ", getTimeStr());
			printFmsData(fmsData);
			pw.printf("Text: %s\n", txt);
			pw.flush();
			return false;
		}

		@Override
		public boolean txtError(FmsData fmsData, String txt) {
			pw.printf("Text Error FMS: @%s: ", getTimeStr());
			printFmsData(fmsData);
			pw.println(txt);
			pw.flush();
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
			 * System.out.print(new Date() + " | ZVEI ERR: @" + sc.getTimeStr()+ " "); for
			 * (int i = 0; i < data.length; i++) { System.out.print(data[i]); if (i <
			 * data.length - 1) { System.out.print(", "); } } System.out.println(" ");
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
	private ShortFileSink fs;
	private String inputFilename;
	private final FmsFileDataStore ds;
	private final PrintWriter pw;
	private final File backupDir;
	private int fmsIndex;
	private int zveiIndex;
	private int backupSamples;

	private void processSampleBackup(final short y) throws IOException {
		switch (fmSquech.setX(y)) {
		case MUTED:
			break;
		case TRIGGERING:
			fs.setWavOut(new File(backupDir,
					"FMS-" + (inputFilename != null ? inputFilename : "lineIn_" + fmSquech.getSampleRate()) + "_"
							+ getTimeStr().replace(" ", "_") + ".wav"));
			fs.setShort(0, y);
			fs.nextSample();
			fmsContainer.setX(y);
			zveiContainer.setX(y);
			backupSamples++;
			break;
		case TRIGGERED:
			fs.setShort(0, y);
			fs.nextSample();
			fmsContainer.setX(y);
			zveiContainer.setX(y);
			backupSamples++;
			break;
		case MUTING:
			if (backupSamples > (int)fmSquech.getSampleRate() / 10 ) {
				//more than 100ms save
				fs.close();
			} else {
				fs.delete();
			}
			backupSamples = 0;
			fmsContainer.reset();
			zveiContainer.reset();
			break;
		default:
			throw new IllegalStateException("Can't handle state: " + fmSquech.getState());
		}
	}

	private void processSampleWoBackup(double y) {
		switch (fmSquech.setX(y)) {
		case MUTED:
			break;
		case TRIGGERING:
			fmsContainer.setX(y);
			zveiContainer.setX(y);
			break;
		case TRIGGERED:
			fmsContainer.setX(y);
			zveiContainer.setX(y);
			break;
		case MUTING:
			fs.close();
			fmsContainer.reset();
			zveiContainer.reset();
			break;
		default:
			throw new IllegalStateException("Can't handle state: " + fmSquech.getState());
		}
	}

	private void setSampleRate(float sampleRate) {
		if (fs != null) {
			fs.setSampleRate(sampleRate);
		}
		fmSquech.setSampleRate(sampleRate);
		fmsContainer.setSampleRate(sampleRate);
		zveiContainer.setSampleRate(sampleRate);
	}

	public void decodeFile(File f, int channel) throws IOException, UnsupportedAudioFileException {
		inputFilename = f.getName();
		inputFilename = inputFilename.substring(0, inputFilename.indexOf(".wav"));
		pw.println(new Date() + " | decode file: " + f.getAbsolutePath());
		pw.flush();
		final ShortSampledSource aiss = new ShortSampledSource(f, 1024);
		
		setSampleRate(aiss.getSampleRate());
		if (backupDir != null) {
			while (aiss.nextSample()) {
				final short sample = aiss.getShort(channel);
				processSampleBackup(sample);
			}
		} else {
			while (aiss.nextSample()) {
				final short sample = aiss.getShort(channel);
				processSampleWoBackup(sample);
			}
		}
		pw.println(new Date() + " | decoded file: " + f.getAbsolutePath());
		pw.flush();
	}

	public void decodeDefaultAudioIn(double samplerate, int channels, int channel)
			throws IOException, LineUnavailableException {

		final ShortTargetDataLineWrapper ds = new ShortTargetDataLineWrapper(channels, samplerate, (int)samplerate);

		pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());
		pw.flush();

		setSampleRate(ds.getSampleRate());
		if (backupDir != null) {
			while (ds.nextSample()) {
				processSampleBackup(ds.getShort(channel));
			}
		} else {
			while (ds.nextSample()) {
				processSampleWoBackup(ds.getShort(channel));
			}
		}
		pw.println(new Date() + " | audio decoded");
	}

	public void decodeAudioIn(String mixerName, int channels, int channel, double samplerate)
			throws IOException, LineUnavailableException {
		Mixer.Info m = null;
		for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
			if (mi.getName().equals(mixerName)) {
				m = mi;
			}
		}

		if (m == null) {
			throw new RuntimeException("Can't find mixer: " + mixerName);
		}

		final ShortTargetDataLineWrapper ds = new ShortTargetDataLineWrapper(m, channels, samplerate, 4096);

		pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());
		pw.flush();

		setSampleRate(ds.getSampleRate());
		if (backupDir != null) {
			while (ds.nextSample()) {
				processSampleBackup(ds.getShort(channel));
			}
		} else {
			while (ds.nextSample()) {
				processSampleWoBackup(ds.getShort(channels));
			}
		}
		pw.println(new Date() + " | audio decoded");
		pw.flush();
	}

	public void printPowerFile(File f, int channel)
			throws IOException, UnsupportedAudioFileException  {
		inputFilename = f.getName();
		inputFilename = inputFilename.substring(0, inputFilename.indexOf(".wav"));
		pw.println(new Date() + " | decode file: " + f.getAbsolutePath());
		pw.flush();
		
		final ShortSampledSource aiss = new ShortSampledSource(f, 1024);
		final ShortFileSink sfs = new ShortFileSink(new File(f.getAbsolutePath() + ".wav") , 2, aiss.getSampleRate(), 4096);
		
		setSampleRate(aiss.getSampleRate());
		while (aiss.nextSample()) {
			final short sample0 = aiss.getShort(0);
			fmSquech.setX(sample0);
			sfs.setShort(0, sample0);
			sfs.setShort(1, (short)Math.round(10 * (fmSquech.getPower() - fmSquech.getThreshold())));
			sfs.nextSample();
		}
		sfs.flush();
		sfs.close();

		pw.println(new Date() + " | decoded to file: " + sfs.getWavOut().getAbsolutePath());
		pw.flush();
	}

	public void printPowerDefaultAudioIn(int channels, double samplerate)
			throws IOException, LineUnavailableException {
		printPowerForAudio(new ShortTargetDataLineWrapper(channels, samplerate, 4096));
	}

	public void printPowerAudioIn(String mixerName, int channels, double samplerate)
			throws IOException, LineUnavailableException {
		Mixer.Info m = null;
		for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
			if (mi.getName().equals(mixerName)) {
				m = mi;
			}
		}

		if (m == null) {
			throw new RuntimeException("Can't find mixer: " + mixerName);
		}
		printPowerForAudio(new ShortTargetDataLineWrapper(m, channels, samplerate, 4096));
	}

	private void printPowerForAudio(final ShortTargetDataLineWrapper ds) throws IOException {
		pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

		final FmSquelch fmSquech0 = new FmSquelch(fmSquech.getThreshold(), fmSquech.getFlp(), fmSquech.getFhp());
		final FmSquelch fmSquech1 = new FmSquelch(fmSquech.getThreshold(), fmSquech.getFlp(), fmSquech.getFhp());
		fmSquech0.setSampleRate(ds.getSampleRate());
		fmSquech1.setSampleRate(ds.getSampleRate());
		while (ds.nextSample()) {
			final short sample0 = ds.getShort(0);
			final short sample1 = ds.getShort(1);
			fmSquech0.setX(sample0);
			fmSquech1.setX(sample1);
			System.out.printf("Power for Squelch [0]:% 10f [1]:% 10f | Value [0]% 8d [1]% 8d\n", fmSquech0.getPower(), fmSquech1.getPower(), sample0, sample1);
			System.out.flush();
		}
		pw.println(new Date() + " | audio decoded");
		pw.flush();
	}

	public Decoder(File backupDir, File fms32Dir, File logFile, short squelchThreshold) {
		ds = new FmsFileDataStore();
		try {
			ds.read(fms32Dir);
		} catch (Exception e) {
		}
		fmSquech = new FmSquelch(squelchThreshold, 5, 3600);
		fmsConsolePrinter = new FmsConsolePrinter();
		fmsContainer = new FmsContainer(fmsConsolePrinter);
		zveiConsolePrinter = new ZveiConsolePrinter();
		zveiContainer = new ZveiContainer(zveiConsolePrinter);

		// fancy stuff
		this.backupDir = backupDir;
		if (backupDir != null) {
			fs = new ShortFileSink(new File(backupDir, "fmsconsole.wav"), 1, 11025.0, 4096);
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
