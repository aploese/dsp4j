/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.fms4j.fms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import net.sf.dsp4j.AudioSink;
import net.sf.dsp4j.datatypes._short.MonoShortFileSource;
import net.sf.dsp4j.datatypes._short.MonoShortSpeakerSink;
import net.sf.dsp4j.datatypes._short.ShortFileSink;
import org.junit.Assert;

/**
 *
 * @author aploese
 */
public class FmsTestListener implements FmsContainerListener {

    List<FmsData> data = new ArrayList();
    int[] startIndices = new int[0];
    int currentDataIndex;
    private File f;
    private ShortFileSink sfs;
    private boolean showResult;
    private MonoShortSpeakerSink msss;
    
    private boolean ignoreError;
    private boolean ignoreCrcError;
    private boolean ignoreTxtCharParityError;
    private boolean ignoreTxtLengthParityError;
    private boolean ignoreTxtCrcError;
    private boolean ignoreTxtError;

    public FmsTestListener(boolean soundOut, boolean showResult){
        this.showResult = showResult;
        if (soundOut) {
            try {
                msss = new MonoShortSpeakerSink(11025);
            } catch (LineUnavailableException ex) {
                throw  new RuntimeException(ex);
            }
        }
    }

    @Override
    public void success(FmsData fmsData) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex), fmsData);
        currentDataIndex++;
    }

    @Override
    public boolean error(FmsData fmsData) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex), FmsData.ERROR_DUMMY);
        currentDataIndex++;
        return !ignoreError;
    }

    @Override
    public boolean crcError(FmsData fmsData) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex), FmsData.CRC_ERROR_DUMMY);
        currentDataIndex++;
        return !ignoreCrcError;
    }

    @Override
    public boolean txtCharParityError(FmsData fmsData, String txt) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\nTXT:%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString(), txt), data.get(currentDataIndex), FmsData.TXT_CHAR_PARITY_ERROR_DUMMY);
        currentDataIndex++;
        return !ignoreTxtCharParityError;
    }

    @Override
    public boolean txtLengthParityError(FmsData fmsData) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString()), data.get(currentDataIndex), FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY);
        currentDataIndex++;
        return !ignoreTxtLengthParityError;
    }

    @Override
    public boolean txtCrcError(FmsData fmsData, String txt) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\nTXT:%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString(), txt), data.get(currentDataIndex), FmsData.TXT_CRC_ERROR_DUMMY);
        currentDataIndex++;
        return !ignoreTxtCrcError;
    }

    @Override
    public boolean txtError(FmsData fmsData, String txt) {
        Assert.assertEquals(String.format("Index %d\n%s\n%s\nTXT:%s\n", currentDataIndex, data.get(currentDataIndex).toCsvString(), fmsData.toCsvString(), txt), data.get(currentDataIndex), FmsData.TXT_ERROR_DUMMY);
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
        MonoShortFileSource msfs = new MonoShortFileSource(MultipleFmsTest.class.getResourceAsStream(String.format("/%s/%s", dirName, resFileName)));
        FmsModulator m = new FmsModulator(0.001);
        m.setSampleRate(msfs.getSampleRate());
        FmsContainer fmsContainer = new FmsContainer(this);
        fmsContainer.setSampleRate(msfs.getSampleRate());
        f = createFile(dirName, resFileName, showResult);
        sfs = new ShortFileSink(f, msfs.getSampleRate(), 6);
        if (msss != null) {
            msss.setSampleRate(msfs.getSampleRate());
        }
        
        short synthBit = 0;
        short realBit = 0;
        short lastRealBit = 0;
        int lastRealBitCount = 0;
        int sample = 0;
        m.addDuration(0, Double.MIN_NORMAL);
        while (msfs.clock()) {
            fmsContainer.setX(msfs.getY());
            if (msss != null) {
                msss.setX(msfs.getY());
            }
            if (showResult) {
                sample++;
                if (currentDataIndex < startIndices.length && sample == startIndices[currentDataIndex]) {
                    m.reset();
                    m.addData(data.get(currentDataIndex));
                }
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
                m.clock();
                synthBit = m.getCurrentFrequency() == 1200 ? bit_1 : bit_0;
                sfs.setX(
//                        msfs.getY(), // Ungefiltert
                        (short)fmsContainer.getInFilter().getY(), //gefiltert 
                        AudioSink.scale0_2PI_to_short(fmsContainer.getNco().getPhi()),
                        (short) (fmsContainer.getCostasLoop().getPhiError() * Short.MAX_VALUE / Math.PI), 
                        synthBit, 
                        realBit, 
                        (short) (fmsContainer.getSymbolFilterY() * Short.MAX_VALUE / 3));
            }
        }
        Assert.assertEquals(data.size(), currentDataIndex);
    }

    protected File createFile(String dir, String name, boolean showResult) throws IOException {
        this.showResult = showResult;
        this.f = File.createTempFile(String.format("%s_%s", dir, name), ".wav");
        f.deleteOnExit();
        return f;
    }

    void tearDown() throws Exception {
        if (sfs != null) {
            sfs.close();
        }
        if (msss != null) {
            msss.close();
        }
        msss = null;
        if (showResult) {
            Runtime.getRuntime().exec(new String[]{"audacity", f.getAbsolutePath()}).waitFor();
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
