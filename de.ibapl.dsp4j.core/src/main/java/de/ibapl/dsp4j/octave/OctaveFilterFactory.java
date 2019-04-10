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
package de.ibapl.dsp4j.octave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 *
 * @author aploese
 */
public class OctaveFilterFactory {

    private final static Logger LOG = Logger.getLogger(OctaveFilterFactory.class.getCanonicalName());

    /*
    public static void setIirFilter(IirFilter filter, FilterType ft, FilterBandType fbt, int order, float fgu, float fgo) {
        StringBuilder sb = new StringBuilder("[b, a] = ");
        sb.append(ft.octaveName);
        sb.append("(");
        sb.append(order);
        sb.append(", ");
        switch (fbt) {
            case LOW_PASS:
            case HIGH_PASS:
                sb.append(String.format("2 * %e / %e", fgu, filter.getSampleRate()));
                break;
            case BAND_PASS:
            case BAND_STOP:
                sb.append("[");
                sb.append(String.format("2 * %e / %e", fgu, filter.getSampleRate()));
                sb.append(", ");
                sb.append(String.format("2 * %e / %e", fgo, filter.getSampleRate()));
                sb.append("]");
                break;
        }
        sb.append(",\"").append(fbt.octaveName).append("\");printf(\"\\na=[ \");printf(\"%128.128f \",a);printf(\"]\\nb=[ \");printf(\"%128.128f \",b);printf(\"]\");");
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"octave", "--silent", "--eval", sb.toString()});
            p.waitFor();
            int exit = p.exitValue();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                LOG.warn("Octave err:" + line);
            }
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            double[] a = null;
            double[] b = null;
            while ((line = br.readLine()) != null) {
                LOG.debug(line);
                String[] splitted = line.split(" ");
                if (splitted.length > 1) {
                    if (splitted[0].startsWith("a=[")) {
                        a = new double[splitted.length - 2];
                        for (int i = 1; i < splitted.length - 1; i++) {
                            a[i - 1] = Double.parseDouble(splitted[i]);
                        }
                    } else if (splitted[0].startsWith("b=[")) {
                        b = new double[splitted.length - 2];
                        for (int i = 1; i < splitted.length - 1; i++) {
                            b[i - 1] = Double.parseDouble(splitted[i]);
                        }
                    }
                }
            }
            if (a != null & b != null) {
                filter.setCoeff(a, b);
            } else {
                throw new RuntimeException("Cant read coefficents from octave");
            }
        } catch (InterruptedException ex) {
            LOG.error("Get filter coefficients", ex);
        } catch (IOException ex) {
            LOG.error("Get filter coefficients", ex);
        }
    }
     
     */
}
