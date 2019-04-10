close all;
clear all;

% Abtastrate
fa=22050;

[data_in,fa_1] = wavread('/home/aploese/NetBeansProjects/fms4j/fms4j-core/src/test/resources/fms-synth-6-2-101-17689-3-1-0-1.wav');
tastwerte = length(data_in);
k = (0:tastwerte - 1)* 1000/fa; %i  
[data_in_1,fa_2] = wavread('/home/aploese/NetBeansProjects/fms4j/fms4j-core/src/test/resources/fms-real-auf-synth-geschnitten-6-2-101-17689-3-1-0-1.wav');

data_in_1 = data_in_1 * 1.3 + 0.2;

figure;


plot(k, data_in, k, data_in_1);

