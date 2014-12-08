/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.profilingtests;

import net.sf.dsp4j.datatypes._double.iirfilter.DirectDoubleIirFilter;
import net.sf.dsp4j.datatypes._double.iirfilter.DoubleIirFilterGenerator;
import net.sf.dsp4j.datatypes._short.iirfilter.DirectShortIirFilter;
import net.sf.dsp4j.datatypes._short.iirfilter.ShortIirFilterGenerator;

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
