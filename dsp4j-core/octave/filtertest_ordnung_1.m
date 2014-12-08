close all;
clear all;

% Abtastrate
fa=22050;

flow = 675
fhigh = 2800

delta = 0.1;

fp1 = flow
fs1 = fp1 * (1- delta)
fp2 = fhigh
fs2 = fp2  * (1+ delta)

rp = 0.99
rs = 0.01

wp1 =2 * fp1/fa
ws1 =2 * fs1/fa
wp2 =2 * fp2/fa
ws2 =2 * fs2/fa

[n, Wc] = buttord([wp1, wp2], [ws1,ws2], rp, rs)


f_f =[ 0,  flow, flow, fhigh, fhigh, fa/2];
g =    [rs,    rs,    rp,   rp,    rs,      rs];

Wc(1) / 2 * fa
Wc(2) / 2 * fa

[b, a] = butter(abs(n), Wc);
[H W] = freqz(b,a,1024);   % Berechnung des Frequenzgangs

[b1] = remez(128, [0,ws1, wp1, wp2, ws2, 1], g, [10, 20, 5]);

[H1 W1] = freqz(b1,1,1024);   % Berechnung des Frequenzgangs

 % # grafische Ausgabe # %
 # string = sprintf('Gewuenschte/Aktuelle Antwort - Filterordnung: %d', 1);
 figure;
    plot(f_f, g, W/2/pi*fa,abs(H), W1/2/pi*fa,abs(H1));
    grid;
    xlabel('f');
    ylabel('|H(exp(j*Omega))|');
    
