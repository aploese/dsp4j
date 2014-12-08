close all;
clear all;

% Abtastrate
fa=22050;
figure;

for tastwerte = 10:10:440

k = (0: tastwerte - 1);

data = zeros(tastwerte, 1, 'float');


frq = 560:10:1560;
y = zeros(length(frq), 1);
z = zeros(length(frq), 1) + tastwerte;

for f1 = 1:length(frq)
	Omega=2 * pi * frq(f1) / fa;  % Auf Abtastrate normierte Frequenz
	
	normalized_frequency = 1060; % Frequenz Filter
	s_prev = 0;
	s_prev2 = 0;
	coeff = 2*cos(2*pi*normalized_frequency/fa);
	powNoise = 0;
	powData = 0;
	
	for n = 1:tastwerte
		noise = rand(1)  * 1;
		 powNoise = powNoise + noise * noise;
		 data = sin(Omega * (n -1));
		 powData = powData + data * data;
		 s = data + noise + coeff*s_prev - s_prev2;
 		s_prev2 = s_prev;
		s_prev = s;
	endfor
	y(f1) = s_prev2*s_prev2 + s_prev*s_prev - coeff*s_prev*s_prev2;
%	y(f1)
%	powData
%	powNoise
endfor

hold on;
plot3(z, frq, y);
hold off;
endfor