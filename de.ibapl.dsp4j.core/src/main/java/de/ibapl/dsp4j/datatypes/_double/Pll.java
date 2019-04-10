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
package de.ibapl.dsp4j.datatypes._double;

import static de.ibapl.dsp4j.DspConst.TWO_PI;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author aploese
 */
public class Pll {
    double d_alpha;
    double d_beta;
    int d_order;
    double d_freq;
    double d_phase;
    double d_max_freq;
    double d_min_freq;

      Pll (double alpha, double beta,
				      double max_freq, double min_freq,
				      int order
				      )
      {
     d_alpha = alpha;
     d_beta = beta;
     d_order = order;
     d_max_freq = max_freq;
     d_min_freq = min_freq;

    }
      double d_phase_detector(Complex sample) {
  switch(d_order) {
  case 2:
  return (sample.getReal() * sample.getImaginary());

  case 4:
  return ((sample.getReal() > 0 ? sample.getImaginary() : -sample.getImaginary()) -
	  (sample.getImaginary() > 0 ? sample.getReal() : -sample.getReal()));

  default:
    throw new RuntimeException("order must be 2 or 4");
 }
}


void setAlpha(double alpha)
{
  d_alpha = alpha;
}

void setBeta(double beta)
{
  d_beta = beta;
}


double frequency(double sample) {
      double error;
  Complex nco_out;
  Complex iptr = new Complex(sample, sample);

      nco_out = new Complex(Math.sin(-d_phase), Math.cos(-d_phase));
      Complex optr = iptr.multiply(nco_out);

      error = d_phase_detector(optr);
      if (error > 1)
	error = 1;
      else if (error < -1)
	error = -1;

      d_freq = d_freq + d_beta * error;
      d_phase = d_phase + d_freq + d_alpha * error;

      while(d_phase>TWO_PI)
	d_phase -= TWO_PI;
      while(d_phase<-TWO_PI)
	d_phase += TWO_PI;

      if (d_freq > d_max_freq)
	d_freq = d_max_freq;
      else if (d_freq < d_min_freq)
	d_freq = d_min_freq;
        System.err.println("FREQ: " + d_freq + "\t" + optr.abs());
      return d_freq;
}


int work (int noutput_items,
			 Complex[] iptr,
			 Complex[] optr,
                         double[] foptr,
                         boolean write_foptr)
{

  double error;
  Complex nco_out;

  if (write_foptr) {

    for (int i = 0; i < noutput_items; i++){
      nco_out = new Complex(Math.sin(-d_phase), Math.cos(-d_phase));
      optr[i] = iptr[i].multiply(nco_out);

      error = d_phase_detector(optr[i]);
      if (error > 1)
	error = 1;
      else if (error < -1)
	error = -1;

      d_freq = d_freq + d_beta * error;
      d_phase = d_phase + d_freq + d_alpha * error;

      while(d_phase>TWO_PI)
	d_phase -= TWO_PI;
      while(d_phase<-TWO_PI)
	d_phase += TWO_PI;

      if (d_freq > d_max_freq)
	d_freq = d_max_freq;
      else if (d_freq < d_min_freq)
	d_freq = d_min_freq;

      foptr[i] = d_freq;
    }
  } else {
    for (int i = 0; i < noutput_items; i++){
      nco_out = new Complex(Math.sin(-d_phase), Math.cos(-d_phase));
      optr[i] = iptr[i].multiply(nco_out);

      error = d_phase_detector(optr[i]);
      if (error > 1)
	error = 1;
      else if (error < -1)
	error = -1;

      d_freq = d_freq + d_beta * error;
      d_phase = d_phase + d_freq + d_alpha * error;

      while(d_phase>TWO_PI)
	d_phase -= TWO_PI;
      while(d_phase<-TWO_PI)
	d_phase += TWO_PI;

      if (d_freq > d_max_freq)
	d_freq = d_max_freq;
      else if (d_freq < d_min_freq)
	d_freq = d_min_freq;

    }
  }
  return noutput_items;
}

}
