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
	private boolean showBlocking = false;
	private int fmsIndex;
	private int zveiIndex;
	private int squelchTrailing;
	private int squelchTrailingCount;
	private int squelchThreshold;
	private int squelchThresholdCount;

	private void processSampleBackup(final short y) throws IOException {
		if (fmSquech.setX(y)) {
			if (squelchTrailing == 0) {
				fs.setWavOut(new File(backupDir,
						"FMS-" + (inputFilename != null ? inputFilename : "lineIn_" + fmSquech.getSampleRate()) + "_"
								+ getTimeStr().replace(" ", "_") + ".wav"));
				if (showBlocking) {
					System.out.println("PASSING: @" + new Date());
				}
				fmsContainer.reset();
				zveiContainer.reset();
				fs.setShort(0, y);
				fs.nextSample();
				squelchTrailing = squelchTrailingCount;
				squelchThreshold = squelchThresholdCount;
			} else {
				fs.setShort(0, y);
				if (squelchThreshold != 0) {
					squelchThreshold--;
				}
			}
		} else {
			if (squelchTrailing == 0) {
				return;
			}
			fs.setShort(0, y);
			fs.nextSample();
			if (showBlocking) {
				System.out.println("BLOCKING: @" + getTimeStr());
			}
			if (squelchThreshold > 0) {
				fs.delete();
				squelchTrailing = 0;
				return;
			} else if (squelchTrailing == 1) {
				fs.close();
				squelchTrailing = 0;
				return;
			}
		}
		squelchTrailing--;
		fmsContainer.setX(y);
		zveiContainer.setX(y);
	}

	private void processSampleWoBackup(double y) {
		if (fmSquech.setX(y)) {
			if (squelchTrailing == 0) {
				if (showBlocking) {
					System.out.println("PASSING: @" + new Date());
				}
				fmsContainer.reset();
				zveiContainer.reset();
				squelchTrailing = squelchTrailingCount;
			}
		} else {
			if (squelchTrailing > 0) {
				squelchTrailing--;
			}
			if (squelchTrailing == 0 && showBlocking) {
				System.out.println("BLOCKING: @" + getTimeStr());
				return;
			}
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
		squelchTrailingCount = (int) Math.ceil(sampleRate * 2); // 2s
		squelchThresholdCount = (int) Math.ceil(sampleRate / 10.0); // 100ms
	}

	public void decodeFile(File f, int channel) throws IOException, UnsupportedAudioFileException {
		inputFilename = f.getName();
		inputFilename = inputFilename.substring(0, inputFilename.indexOf(".wav"));
		pw.println(new Date() + " | decode file: " + f.getAbsolutePath());
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
	}

	public void decodeDefaultAudioIn(double samplerate, int channels, int channel)
			throws IOException, LineUnavailableException {

		final ShortTargetDataLineWrapper ds = new ShortTargetDataLineWrapper(channels, samplerate, (int)samplerate);

		pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

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
	}

	public void printPowerDefaultAudioIn(int channels, int channel, double samplerate)
			throws IOException, LineUnavailableException {

		final ShortTargetDataLineWrapper ds = new ShortTargetDataLineWrapper(channels, samplerate, 4096);

		pw.println(new Date() + " | decode audio: " + ds.getTargetDataLine());

		setSampleRate(ds.getSampleRate());
		while (ds.nextSample()) {
			fmSquech.setX(ds.getShort(channel));
			System.out.println("Power for Squelch: " + fmSquech.getPower());
		}
		pw.println(new Date() + " | audio decoded");
	}

	public void printPowerAudioIn(String mixerName, int channels, int channel, double samplerate)
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

		setSampleRate(ds.getSampleRate());
		while (ds.nextSample()) {
			fmSquech.setX(ds.getShort(channel));
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
