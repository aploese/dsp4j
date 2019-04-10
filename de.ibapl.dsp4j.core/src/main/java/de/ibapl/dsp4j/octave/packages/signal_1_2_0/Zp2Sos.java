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
package de.ibapl.dsp4j.octave.packages.signal_1_2_0;

import java.util.Arrays;
import de.ibapl.dsp4j.octave_3_2_4.OctaveBuildIn;
import org.apache.commons.math3.complex.Complex;

/*
 Copyright (C) 2005 Julius O. Smith III <jos@ccrma.stanford.edu>

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU General Public License as published by the Free Software
 Foundation; either version 3 of the License, or (at your option) any later
 version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 details.

 You should have received a copy of the GNU General Public License along with
 this program; if not, see <http://www.gnu.org/licenses/>.
 */
/**
 *
 * @author aploese
 */
public class Zp2Sos {
    
    private double[][] sos;
    private double gain;

    public Zp2Sos(Complex[] zeros, Complex[] poles, double gain) {
        this.gain = gain;
        
        CplxReal cplxReal = new CplxReal();

        cplxReal.cplxReal(0.0000000001, zeros);
        Complex[] zc = cplxReal.getConjComplxPair();
        Complex[] zr = cplxReal.getRealValues();

        cplxReal.cplxReal(0.0000000001,poles);
        Complex[] pc = cplxReal.getConjComplxPair();
        Complex[] pr = cplxReal.getRealValues();

        int nzc = zc.length;
        int npc = pc.length;

        int nzr = zr.length;
        int npr = pr.length;

        // Pair up real zeros:
        int nzrsec = 0;
        Complex[] zrms = new Complex[0];
        Complex[] zrp = new Complex[0];
        if (nzr > 0) {
            if (nzr % 2 == 1) {
                zr = Arrays.copyOf(zr, zr.length + 1);
                zr[zr.length -1] = Complex.ZERO;
                nzr++;
            }
            nzrsec = nzr / 2;
            zrms = new Complex[nzrsec];
            zrp = new Complex[nzrsec];
            for (int i = 0; i < zrms.length; i++) {
                zrms[i] = zr[i * 2].negate().subtract(zr[i * 2 + 1]);
                zrp[i] = zr[i * 2].multiply(zr[i * 2 + 1]);
            }
        }

        // Pair up real poles:
        int nprsec = 0;
        Complex[] prms = new Complex[0];
        Complex[] prp = new Complex[0];
        if (npr > 0) {
            if (npr % 2 == 1) {
                pr = Arrays.copyOf(pr, pr.length + 1);
                pr[pr.length -1] = Complex.ZERO;
                npr++;
            }
            nprsec = npr / 2;
            prms = new Complex[nprsec];
            prp = new Complex[nprsec];
            for (int i = 0; i < prms.length; i++) {
                prms[i] = pr[i * 2].negate().subtract(pr[i * 2 + 1]);
                prp[i] = pr[i * 2].multiply(pr[i * 2 + 1]);
            }
        }

        int nsecs = Math.max(nzc + nzrsec, npc + nprsec);

        // Convert complex zeros and poles to real 2nd-order section form:
        double[] zcm2r = OctaveBuildIn.real(zc);
        for (int i = 0; i < zcm2r.length; i++) {
            zcm2r[i] *= -2;
        }
        double[] zca2 = OctaveBuildIn.abs(zc);
        for (int i = 0; i < zca2.length; i++) {
            zca2[i] = Math.pow(zca2[i], 2);
        }
        double[] pcm2r = OctaveBuildIn.real(pc);
        for (int i = 0; i < pcm2r.length; i++) {
            pcm2r[i] *= -2;
        }
        double[] pca2 = OctaveBuildIn.abs(pc);
        for (int i = 0; i < pca2.length; i++) {
            pca2[i] = Math.pow(pca2[i], 2);
        }

        //all 2nd-order polynomials are monic
        sos = new double[nsecs][6];
        for (int i = 0; i < nsecs; i++) {
            sos[i][0] = 1;
            sos[i][3] = 1;
        }

        int nzrl = nzc + nzrsec; // index of last real zero section
        int nprl = npc + nprsec; // index of last real pole section

        for (int i = 0; i < nsecs; i++) {

            if (i < nzc) {
                // lay down a complex zero pair:
                sos[i][1] = zcm2r[i];
                sos[i][2] = zca2[i];
            } else if (i < nzrl) {
                // lay down a pair of real zeros:
                sos[i][1] = zrms[i - nzc].getReal();
                sos[i][2] = zrp[i - nzc].getReal();
            }

            if (i < npc) {
                // lay down a complex pole pair:
                sos[i][4] = pcm2r[i];
                sos[i][5] = pca2[i];
            } else if (i < nprl) {
                //lay down a pair of real poles:
                sos[i][4] = prms[i - npc].getReal();
                sos[i][5] = prp[i - npc].getReal();
            }
        }
    }
    
    public int getRowCount() {
        return sos.length;
    }
    
    public double[] getRow(int i) {
        return sos[i];
    }
    
    public double[][] getSos() {
        return sos;
    }
    
    public double getGain() {
        return gain;
    }

}
