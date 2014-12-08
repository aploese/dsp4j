close all;
clear all;

% Abtastrate
fa=22050;
order = 5;
fg_lpu = 500;
fg_lpo = 3000;

wg_lpu = fg_lpu * 2/fa

wg_lpo = fg_lpo * 2/fa

f_f =[fg_lpu, fg_lpu, fg_lpo, fg_lpo];
g = [0,1,1,0];

[b, a] = butter(order, [wg_lpu, wg_lpo])
printf("\na = [", a);
printf("%64.1f, ", a);
printf("]\nb = [", a);
printf("%512.512f, ", b);
printf("]\n", a);

#[b1,a1] = cheby2(order, 20,[wg_lpu, wg_lpo]);
printf(' fu=%f\tfo=%f\n', fg_lpu, wg_lpo);

[H W] = freqz(b,a,1024);   % Berechnung des Frequenzgangs

   % # grafische Ausgabe # %
    string = sprintf('Gewuenschte/Aktuelle Antwort - Filterordnung: %d', order);
    figure;
    plot(f_f, g, W/2/pi*fa,abs(H));
    grid;
    xlabel('f');
    ylabel('|H(exp(j*Omega))|');
    title(string);

[x, fa] = wavread('iir-filter-test1661436786036375993.wav');
[gefiltert, si] = filter(b, a, x);

k = 0:length(x)-1;

figure;


plot(k, gefiltert);

[gefiltert, si] = filter([4,5,6], [1,2,3],[1,1,1,1,1,1,1,1,1,1])
