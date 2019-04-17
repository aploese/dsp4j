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
package de.ibapl.dsp4j.fms4j.fms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import de.ibapl.dsp4j.AudioSink;
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import de.ibapl.dsp4j.datatypes._short.ShortSampledSource;

import org.junit.Assert;

/**
 *
 * @author aploese
 */
public class FmsTestListener implements FmsContainerListener {

	List<FmsData> data = new ArrayList<>();
	int[] startIndices = new int[0];
	int currentDataIndex;
	private File f;
	private ShortFileSink sfs;
	private boolean showResult;

	private boolean ignoreError;
	private boolean ignoreCrcError;
	private boolean ignoreTxtCharParityError;
	private boolean ignoreTxtLengthParityError;
	private boolean ignoreTxtCrcError;
	private boolean ignoreTxtError;

	public FmsTestListener(boolean showResult) {
		this.showResult = showResult;
	}

	@Override
	public void success(FmsData fmsData) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex), fmsData);
		currentDataIndex++;
	}

	@Override
	public boolean error(FmsData fmsData) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex),
				FmsData.ERROR_DUMMY);
		currentDataIndex++;
		return !ignoreError;
	}

	@Override
	public boolean crcError(FmsData fmsData) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex),
				FmsData.CRC_ERROR_DUMMY);
		currentDataIndex++;
		return !ignoreCrcError;
	}

	@Override
	public boolean txtCharParityError(FmsData fmsData, String txt) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\nTXT:%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString(), txt), data.get(currentDataIndex),
				FmsData.TXT_CHAR_PARITY_ERROR_DUMMY);
		currentDataIndex++;
		return !ignoreTxtCharParityError;
	}

	@Override
	public boolean txtLengthParityError(FmsData fmsData) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex),
				FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY);
		currentDataIndex++;
		return !ignoreTxtLengthParityError;
	}

	@Override
	public boolean txtCrcError(FmsData fmsData, String txt) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\nTXT:%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString(), txt), data.get(currentDataIndex),
				FmsData.TXT_CRC_ERROR_DUMMY);
		currentDataIndex++;
		return !ignoreTxtCrcError;
	}

	@Override
	public boolean txtError(FmsData fmsData, String txt) {
		Assert.assertEquals(String.format("Index %d\n%s\n%s\nTXT:%s\n", currentDataIndex,
				data.get(currentDataIndex).toCsvString(), fmsData.toCsvString(), txt), data.get(currentDataIndex),
				FmsData.TXT_ERROR_DUMMY);
		currentDataIndex++;
		return !ignoreTxtError;
	}

	void add(int startIndex, FmsData fmsData) {
		data.add(fmsData);
		startIndices = Arrays.copyOf(startIndices, startIndices.length + 1);
		startIndices[startIndices.length - 1] = startIndex;
	}

	public void doTest(String dirName, String resFileName) throws Exception {
		final short bit_1 = (short) (Short.MAX_VALUE * 0.75);
		final short bit_0 = (short) (Short.MIN_VALUE * 0.75);
		ShortSampledSource msfs = new ShortSampledSource(
				FmsTestListener.class.getResourceAsStream(String.format("/%s/%s", dirName, resFileName)), 1);
		FmsContainer fmsContainer = new FmsContainer(this);
		fmsContainer.setSampleRate(msfs.getSampleRate());
		if (showResult) {
			f = createFile(dirName, resFileName);
			sfs = new ShortFileSink(f, 8, msfs.getSampleRate(), 1024);
		}
		short realBit = 0;
		short lastRealBit = 0;
		int lastRealBitCount = 0;
		while (msfs.nextSample()) {
			final short rawSample = msfs.getShort(0);
			fmsContainer.setX(rawSample);
			if (showResult) {
				if (fmsContainer.getFmsBD().getState() == FmsBitDecoder.State.DECODE_BITS) {
					if (lastRealBitCount < fmsContainer.getFmsBD().bitCount) {
						realBit = (short) (fmsContainer.getFmsBD().bit ? Short.MAX_VALUE / 2 : Short.MIN_VALUE / 2);
						lastRealBitCount = fmsContainer.getFmsBD().bitCount;
					} else {
						realBit = fmsContainer.getFmsBD().bit ? bit_1 : bit_0;
						if (lastRealBit != realBit) {
							lastRealBitCount = 0;
						}
					}
					lastRealBit = fmsContainer.getFmsBD().bit ? bit_1 : bit_0;
				} else {
					lastRealBit = 0;
					lastRealBitCount = 0;
					realBit = 0;
					lastRealBitCount = 0;
				}

				if (showResult) {
					sfs.setShort(0, rawSample);
					sfs.setShort(1, (short) fmsContainer.getInFilter().getY());
					sfs.setShort(2, AudioSink.scale0_2PI_to_short(fmsContainer.getNco().getPhi()));
					sfs.setShort(3, (short) (fmsContainer.getCostasLoop().getPhiError() * Short.MAX_VALUE / Math.PI));
					sfs.setShort(4, (short) fmsContainer.getCostasLoop().getY());
					sfs.setShort(5, (short) (fmsContainer.getFmsBD().getState().ordinal() * Short.MAX_VALUE / FmsBitDecoder.State.values().length));
					sfs.setShort(6, (short) (fmsContainer.getFmsBD().bit ? Short.MAX_VALUE / 2 : Short.MIN_VALUE / 2));
					sfs.setShort(7, (short) (fmsContainer.getSymbolFilterY() * Short.MAX_VALUE / 3));
					sfs.nextSample();
				}
			}
		}
		Assert.assertEquals(data.size(), currentDataIndex);
	}

	protected File createFile(String dir, String name) throws IOException {
		File result = File.createTempFile(String.format("%s_%s", dir, name), ".wav");
		result.deleteOnExit();
		return result;
	}

	void tearDown() throws Exception {
		if (sfs != null) {
			sfs.close();
		}
		if (showResult && f != null) {
			Runtime.getRuntime().exec(new String[] { "audacity", f.getAbsolutePath() }).waitFor();
		}
	}

	/**
	 * @return the ignoreError
	 */
	public boolean isIgnoreError() {
		return ignoreError;
	}

	/**
	 * @param ignoreError the ignoreError to set
	 */
	public void setIgnoreError(boolean ignoreError) {
		this.ignoreError = ignoreError;
	}

	/**
	 * @return the ignoreCrcError
	 */
	public boolean isIgnoreCrcError() {
		return ignoreCrcError;
	}

	/**
	 * @param ignoreCrcError the ignoreCrcError to set
	 */
	public void setIgnoreCrcError(boolean ignoreCrcError) {
		this.ignoreCrcError = ignoreCrcError;
	}

	/**
	 * @return the ignoreTxtCrcError
	 */
	public boolean isIgnoreTxtCrcError() {
		return ignoreTxtCrcError;
	}

	/**
	 * @param ignoreTxtCrcError the ignoreTxtCrcError to set
	 */
	public void setIgnoreTxtCrcError(boolean ignoreTxtCrcError) {
		this.ignoreTxtCrcError = ignoreTxtCrcError;
	}

	/**
	 * @return the ignoreTxtCharParityError
	 */
	public boolean isIgnoreTxtCharParityError() {
		return ignoreTxtCharParityError;
	}

	/**
	 * @param ignoreTxtCharParityError the ignoreTxtCharParityError to set
	 */
	public void setIgnoreTxtCharParityError(boolean ignoreTxtCharParityError) {
		this.ignoreTxtCharParityError = ignoreTxtCharParityError;
	}

	/**
	 * @return the ignoreTxtLengthParityError
	 */
	public boolean isIgnoreTxtLengthParityError() {
		return ignoreTxtLengthParityError;
	}

	/**
	 * @param ignoreTxtLengthParityError the ignoreTxtLengthParityError to set
	 */
	public void setIgnoreTxtLengthParityError(boolean ignoreTxtLengthParityError) {
		this.ignoreTxtLengthParityError = ignoreTxtLengthParityError;
	}

	/**
	 * @return the ignoreTxtError
	 */
	public boolean isIgnoreTxtError() {
		return ignoreTxtError;
	}

	/**
	 * @param ignoreTxtError the ignoreTxtError to set
	 */
	public void setIgnoreTxtError(boolean ignoreTxtError) {
		this.ignoreTxtError = ignoreTxtError;
	}

}
