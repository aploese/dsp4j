% ##############################################################################
% ##  Funkmeldeempfänger                                                      ##
% ##############################################################################

close all;      % alle Figures schliessen
clear all;      % alle Variablen löschen
% Abtastrate
fa=8000;        

% Frequenzen
ftf = [2400 1060 1160 1270 1400 1530 1670 1830 2000 2200 2600];

m = 72;

disp('Filter 0');
b1 = berechneFilter(ftf(10), ftf(1), ftf(11),fa, m); 
disp('Filter 1');
b2 = berechneFilter(960, ftf(2), ftf(3), fa, m); 
disp('Filter 2');
b3 = berechneFilter(ftf(2), ftf(3), ftf(4), fa, m); 
disp('Filter 3');
b4 = berechneFilter(ftf(3), ftf(4), ftf(5), fa, m); 
disp('Filter 4');
b5 = berechneFilter(ftf(4), ftf(5), ftf(6), fa, m); 
disp('Filter 5');
b6 = berechneFilter(ftf(5), ftf(6), ftf(7), fa, m); 
disp('Filter 6');
b7 = berechneFilter(ftf(6), ftf(7), ftf(8), fa, m); 
disp('Filter 7');
b8 = berechneFilter(ftf(7), ftf(8), ftf(9), fa, m); 
disp('Filter 8');
b9 = berechneFilter(ftf(8), ftf(9), ftf(10), fa, m); 
disp('Filter 9');
b10 = berechneFilter(ftf(9), ftf(10), ftf(1),  fa, m); 
disp('Filter 10');
b11 = berechneFilter(ftf(1), ftf(11), 2700,  fa, m); 

% Tastzeit in ms entspricht 560 Abtastwerte
t_on = 0.070;
t_off = 0.030; 

tic;
%[data_in k tastwerte] = ftg(ftf(7), ftf(4), ftf(2), ftf(7), ftf(1), fa, t_on, t_off, 0);
[data_in,fa_1] = wavread('ftf-test-lang-16-bit-pcm.wav');
if (fa != fa_1) 
	disp('F tast ');
endif;
sound(data_in, fa);
tastwerte = length(data_in);
k = (0:tastwerte - 1)* 1000/fa; %i  
toc

%save test.dat data		
%sound(data_in, fa);

y1 = zeros(tastwerte,1);
y2 = zeros(tastwerte, 1) + 10;
y3 = zeros(tastwerte, 1) + 20;
y4 = zeros(tastwerte, 1) + 30;
y5 = zeros(tastwerte, 1) + 40;
y6 = zeros(tastwerte, 1) + 50;
y7 = zeros(tastwerte, 1) + 60;
y8 = zeros(tastwerte, 1) + 70;
y9 = zeros(tastwerte, 1) + 80;
y10 = zeros(tastwerte, 1) + 90;
y11 = zeros(tastwerte, 1) + 100;



tic;
for i = m+1:tastwerte
	for j = 1:m
		y1(i) = y1(i) + b1(m -  j + 1)*data_in(i-j);
		y2(i) = y2(i) + b2(m -  j + 1)*data_in(i-j);
		y3(i) = y3(i) + b3(m -  j + 1)*data_in(i-j);
		y4(i) = y4(i) + b4(m -  j + 1)*data_in(i-j);
		y5(i) = y5(i) + b5(m -  j + 1)*data_in(i-j);
		y6(i) = y6(i) + b6(m -  j + 1)*data_in(i-j);
		y7(i) = y7(i) + b7(m -  j + 1)*data_in(i-j);
		y8(i) = y8(i) + b8(m -  j + 1)*data_in(i-j);
		y9(i) = y9(i) + b9(m -  j + 1)*data_in(i-j);
		y10(i) = y10(i) + b10(m -  j + 1)*data_in(i-j);
		y11(i) = y11(i) + b11(m -  j + 1)*data_in(i-j);
	endfor
endfor
disp('Werte berechnet');
toc

%wavwrite(y_sum,fa,'raus.wav');

tic
% # Grafische Ausgabe # %
figure;                 
plot(k,y1);           
hold on
plot(k,y2);
plot(k,y3);
plot(k,y4);
plot(k,y5);
plot(k,y6);
plot(k,y7);
plot(k,y8);
plot(k,y9);
plot(k,y10);
plot(k,y11);
plot(k,data_in - 10);

hold off           

xlabel('k');            % Achsenbeschriftung an der x-Achse
ylabel('values');   % Achsenbeschriftung an der y-Achse

printf('Ende figure');
toc
