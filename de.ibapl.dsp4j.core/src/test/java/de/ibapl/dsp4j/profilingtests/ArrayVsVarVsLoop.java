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
