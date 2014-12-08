package com.falstad.source;

public class Mp3Waveform extends Waveform {
/*
        Decoder decoder;
	Bitstream bitstream;
	Header header;
	boolean first;
	SampleBuffer output;
	String fileName;
	BufferedInputStream bis;
*/
	public Mp3Waveform(int f)  {
            //fileName = mp3List[f];
        }

        public boolean start(int samplerate) {
/*	    first = false;
	    try {
		// try to load mp3 from cache
		if (bis != null) {
		    try {
			bis.reset();
		    } catch (Exception e) {
			bis = null;
		    }
		}

		// first time, or cache reset failed; get the data
		if (bis == null) {
		    URL url = new URL(applet.getCodeBase() + fileName);
		    Object o = url.getContent();
		    if (o instanceof BufferedInputStream)
			bis = (BufferedInputStream) o;
		    else {
			// we're reading off a network connection, so cache it
			FilterInputStream fs = (FilterInputStream) o;
			bis = new BufferedInputStream(fs);
		    }
		    bis.mark(200000);
		}
		bitstream = new Bitstream(bis);
		header = bitstream.readFrame();
		decoder = new Decoder();
		output = (SampleBuffer)decoder.decodeFrame(header, bitstream);
		setSampleRate(decoder.getOutputFrequency());
		rateChooser.disable();
		first = true;
	    } catch (Exception e) {
		e.printStackTrace();
		mp3Error = "Can't open " + fileName;
		return false;
	    }
	    return true;
 */
	    return true;
	}

	public int getData(boolean logFreq, int inputValue, double inputW) {
            /*
	    if (!first) {
		try {
		    bitstream.closeFrame();
		    header = bitstream.readFrame();
		    if (header == null) {
			if (!start())
			    return 0;
			return getData();
		    }
		    output = (SampleBuffer)decoder.decodeFrame(header, bitstream);
		} catch (Exception e) {
		    e.printStackTrace();
		    return 0;
		}
	    } else
		first = false;

	    buffer = output.getBuffer();
	    return output.getBufferLength();
             */
            return 0;
	}

	public int getChannels()     {
        //    eturn decoder.getOutputChannels();
            return 0;
        }
	public String getInputText() { return null; }
	public boolean needsFrequency() { return false; }
    }

