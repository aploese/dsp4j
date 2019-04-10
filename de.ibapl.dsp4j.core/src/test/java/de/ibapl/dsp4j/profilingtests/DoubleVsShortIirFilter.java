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
package de.ibapl.dsp4j.profilingtests;

import de.ibapl.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import de.ibapl.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import de.ibapl.dsp4j.datatypes._short.iirfilter.DirectShortIirFilter;
import de.ibapl.dsp4j.datatypes._short.iirfilter.ShortIirFilterGenerator;

/**
 *
 * @author aploese
 */
public class DoubleVsShortIirFilter {

    private DirectShortIirFilter sf;
    private DirectDoubleIirFilter df;
    private final DirectShortIirFilter finalSf;
    private final DirectDoubleIirFilter finalDf;
    private final static int LENGTH = 2000;

    public DoubleVsShortIirFilter() {
        sf = new ShortIirFilterGenerator(8000).getLP_ButterFc(10, 1000, DirectShortIirFilter.class);
        df = new DoubleIirFilterGenerator(8000).getLP_ButterFc(10, 1000, DirectDoubleIirFilter.class);
        finalSf = new ShortIirFilterGenerator(8000).getLP_ButterFc(10, 1000, DirectShortIirFilter.class);
        finalDf = new DoubleIirFilterGenerator(8000).getLP_ButterFc(10, 1000, DirectDoubleIirFilter.class);
    }
    
    int runShort(int x) {
        sf.setX(x);
        return sf.getY();
    }

    int runFinalShort(int x) {
        finalSf.setX(x);
        return finalSf.getY();
    }

    double runDouble(double x) {
        df.setX(x);
        return df.getY();
    }

    double runFinalDouble(double x) {
        finalDf.setX(x);
        return finalDf.getY();
    }

    public static void main(String[] args) {
        DoubleVsShortIirFilter test = new DoubleVsShortIirFilter();
        test.run();;
    }

    private void runIn() {
        for (int j = 0; j < LENGTH; j++) {
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            runShort(i);
            runFinalShort(i);
            runDouble(i);
            runFinalDouble(i);
        }
        }
    }

    private void run() {
        runIn();
       for (int j = 0; j < 10; j++) {
        timeShort();
        timeFinalShort();
        timeDouble();
        timeFinalDouble();
           System.out.println("");
       }
    }

    private void timeShort() {
        final long t = System.currentTimeMillis();
        for (int j = 0; j < LENGTH; j++) {
            for (int i = 0; i < Short.MAX_VALUE; i++) {
                runShort(i);
            }
        }
        final long tdiff = System.currentTimeMillis() - t;
        System.out.printf("Short: %dms\t", tdiff);
    }

    private void timeFinalShort() {
        final long t = System.currentTimeMillis();
        for (int j = 0; j < LENGTH; j++) {
            for (int i = 0; i < Short.MAX_VALUE; i++) {
                runFinalShort(i);
            }
        }
        final long tdiff = System.currentTimeMillis() - t;
        System.out.printf("Final Short: %dms\t", tdiff);
    }

    private void timeDouble() {
        final long t = System.currentTimeMillis();
        for (int j = 0; j < LENGTH; j++) {
            for (int i = 0; i < Short.MAX_VALUE; i++) {
                runDouble(i);
            }
        }
        final long tdiff = System.currentTimeMillis() - t;
        System.out.printf("Double: %dms\t", tdiff);
    }

    private void timeFinalDouble() {
        final long t = System.currentTimeMillis();
        for (int j = 0; j < LENGTH; j++) {
            for (int i = 0; i < Short.MAX_VALUE; i++) {
                runFinalDouble(i);
            }
        }
        final long tdiff = System.currentTimeMillis() - t;
        System.out.printf("Final Double: %dms\t", tdiff);
    }
}
