/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.profilingtests;

/**
 *
 * @author aploese
 */
public class ArrayVsVarVsLoop {

    double[] a = new double[10];
    double a0;
    double a1;
    double a2;
    double a3;
    double a4;
    double a5;
    double a6;
    double a7;
    double a8;
    double a9;
    double y;

    double runVar(double x) {
        y = a0;
        a0 = a1;
        a1 = a2;
        a2 = a3;
        a3 = a4;
        a4 = a5;
        a5 = a6;
        a6 = a7;
        a7 = a8;
        a8 = a9;
        a9 = x;
        return y;
    }

    double runArray(double x) {
        y = a[0];
        a[0] = a[1];
        a[1] = a[2];
        a[2] = a[3];
        a[3] = a[4];
        a[4] = a[5];
        a[5] = a[6];
        a[6] = a[7];
        a[7] = a[8];
        a[8] = a[9];
        a[9] = x;
        return y;
    }

    double runLoop(double x) {
        y = a[0];
        for (int i = 0; i < a.length - 1; i++) {
            a[i] = a[i + 1];
        }
        a[9] = x;
        return y;
    }

    public static void main(String[] args) {
        ArrayVsVarVsLoop test = new ArrayVsVarVsLoop();
        test.run();;

    }

    private int runIn() {
        int j = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            j = i * j;
        }
        return j;
    }

    private void run() {
        runIn();
        final int length = 1000 * 1000 * 100;
        for (int i = 0; i < length ; i++) {
            runVar(i);
            runArray(i);
            runLoop(i);
        }
    }
}
