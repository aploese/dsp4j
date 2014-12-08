package com.falstad.filter;

public class FFT {

    private double wtabf[];
    private double wtabi[];
    private final int size;

    public FFT(int sz) {
        size = sz;
        if ((size & (size - 1)) != 0) {
            System.out.println("size must be power of two!");
        }
        calcWTable();
    }

    private void calcWTable() {
        // calculate table of powers of w
        wtabf = new double[size];
        wtabi = new double[size];
        for (int i = 0; i < size; i += 2) {
            double th = Math.PI * i / size;
            wtabf[i] = Math.cos(th);
            wtabf[i + 1] = Math.sin(th);
            wtabi[i] = wtabf[i];
            wtabi[i + 1] = -wtabf[i + 1];
        }
    }

    public void fft(double[] data) {
        transform(data, false);
    }

    public void ifft(double[] data) {
        transform(data, true);
    }
    
    private void transform(double[] data, boolean inv) {

        if ((size & (size - 1)) != 0) {
            System.out.println("size must be power of two!");
        }

        // bit-reversal
        int j = 0;
        final int size2 = size * 2;
        for (int i = 0; i < size2; i += 2) {
            if (i > j) {
                double q = data[i];
                data[i] = data[j];
                data[j] = q;
                q = data[i + 1];
                data[i + 1] = data[j + 1];
                data[j + 1] = q;
            }
            // increment j by one, from the left side (bit-reversed)
            int bit = size;
            while ((bit & j) != 0) {
                j &= ~bit;
                bit >>= 1;
            }
            j |= bit;
        }

        // amount to skip through w table
        int tabskip = size << 1;

        // unroll the first iteration of the main loop
        for (int i = 0; i < size2; i += 4) {
            final double d1r = data[i];
            final double d1i = data[i + 1];
            final double d2r = data[i + 2];
            final double d2i = data[i + 3];
            data[i] = d1r + d2r;
            data[i + 1] = d1i + d2i;
            data[i + 2] = d1r - d2r;
            data[i + 3] = d1i - d2i;
        }
        tabskip >>= 1;

        // unroll the second iteration of the main loop
        final int imult = (inv) ? -1 : 1;
        for (int i = 0; i < size2; i += 8) {
            double d1r = data[i];
            double d1i = data[i + 1];
            double d2r = data[i + 4];
            double d2i = data[i + 5];
            data[i] = d1r + d2r;
            data[i + 1] = d1i + d2i;
            data[i + 4] = d1r - d2r;
            data[i + 5] = d1i - d2i;
            d1r = data[i + 2];
            d1i = data[i + 3];
            d2r = data[i + 6] * imult;
            d2i = data[i + 7] * imult;
            data[i + 2] = d1r - d2i;
            data[i + 3] = d1i + d2r;
            data[i + 6] = d1r + d2i;
            data[i + 7] = d1i - d2r;
        }
        tabskip >>= 1;

        final double[] wtab = (inv) ? wtabi : wtabf;
        for (int skip1 = 16; skip1 <= size2; skip1 <<= 1) {
            // skip2 = length of subarrays we are combining
            // skip1 = length of subarray after combination
            final int skip2 = skip1 >> 1;
            tabskip >>= 1;
            // for each subarray
            for (int i = 0; i < size2; i += skip1) {
                int ix = 0;
                // for each pair of complex numbers (one in each subarray)
                for (j = i; j < i + skip2; j += 2, ix += tabskip) {
                    final double wr = wtab[ix];
                    final double wi = wtab[ix + 1];
                    final double d1r = data[j];
                    final double d1i = data[j + 1];
                    final int j2 = j + skip2;
                    final double d2r = data[j2];
                    final double d2i = data[j2 + 1];
                    final double d2wr = d2r * wr - d2i * wi;
                    final double d2wi = d2r * wi + d2i * wr;
                    data[j] = d1r + d2wr;
                    data[j + 1] = d1i + d2wi;
                    data[j2] = d1r - d2wr;
                    data[j2 + 1] = d1i - d2wi;
                }
            }
        }
    }

    public int getSize() {
        return size;
    }
}
