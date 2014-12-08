close all;
clear all;

% Abtastrate
fa=44100;

#Symbolrate
baudrate  = 1200;
#Symbollänge
bitlength = fa /baudrate

f_0 = 1200;
d_phi_0 = 2 * pi * f_0 / fa;  % Auf Abtastrate normierte Frequenz

f_1 = 1800;
d_phi_1 = 2 * pi * f_1 / fa;  % Auf Abtastrate normierte Frequenz

f_nco = 1200;
d_phi_nco = 2 * pi * f_nco / fa;  % Auf Abtastrate normierte Frequenz


fg_lp1 = 1200;
wg_lp_1 = fg_lp1 * 2/fa;
order_lp1 = 4;


k = 0:bitlength -1;

inputValues = sin(d_phi_0 * k);
phi = d_phi_0 * length(k);
inputValues = cat(2, inputValues, sin(phi + d_phi_1 * k)); 
phi = phi + d_phi_1 * length(k);
inputValues = cat(2, inputValues, sin(phi + d_phi_0 * k)); 
phi = phi + d_phi_0 * length(k);
inputValues = cat(2, inputValues, sin(phi + d_phi_1 * k)); 
phi = phi + d_phi_1 * length(k);
inputValues = cat(2, inputValues, sin(phi + d_phi_0 * k)); 
phi = phi + d_phi_0 * length(k);
inputValues = cat(2, inputValues, sin(phi + d_phi_1 * k)); 
phi = phi + d_phi_1 * length(k);
inputValues = cat(2, inputValues, sin(phi + d_phi_0 * k)); 
phi = phi + d_phi_0 * length(k);

#inputValues = sin(Omega_0 * k);

tastwerte = length(inputValues);
k = (0:tastwerte - 1);


[b1,a1] = butter(order_lp1, wg_lp_1);
printf('u_off:      f=%f\tw=%f\n', fg_lp1, wg_lp_1);

[H W] = freqz(b1,a1,512);   % Berechnung des Frequenzgangs

   % # grafische Ausgabe # %
    string = sprintf('Gewuenschte/Aktuelle Antwort - Filterordnung: %d', order_lp1);
    figure;
    plot(W/pi,abs(H));
    grid;
    xlabel('Omega/pi');
    ylabel('|H(exp(j*Omega))|');
    title(string);




ncoOutCos = cos(d_phi_nco * k);
ncoOutSin = sin(d_phi_nco * k);
dclImPath = -inputValues .* ncoOutSin; 
dclRePath = inputValues .* ncoOutCos;
 
nco_in = [];
 
#for i = 1 :tastwerte; 
#	dclImPath(i) = - inputValues(i) * ncoOutSin(i);
#	dclRePath(i) = inputValues(i) * ncoOutCos(i);	
#endfor;

dclImLp2Path = filter(b1, a1, dclImPath);
dclReLp1Path = filter(b1, a1, dclRePath);

#ncoLp_in = dclReLp1Path  .* dclImLp2Path;
for i = 1 :tastwerte; 
	ncoLp_in(i) = dclReLp1Path(i)  * dclImLp2Path(i);	
endfor;

nco_in = filter(b1, a1, ncoLp_in);


figure;

x_values = k/fa * 1000;

h = hilbert(inputValues, 64);

plot(
	x_values, dclImPath + 3, ';dclImPath;', 
	x_values, dclRePath + 3, ';dclRePath;',
	
	x_values, dclImLp2Path + 5, ';dclImLp2Path;', 
	x_values, dclReLp1Path + 5, ';dclReLp1Path;',

	x_values, ncoLp_in + 6, ';ncoLp_in;', 
	x_values, nco_in + 6, ';nco_in;',

	x_values, ncoOutCos - 2, ';ncoOutCos;', 
	x_values, ncoOutSin - 2, ';ncoOutSin;',

	x_values, imag(h), ';im;',
#Das immer	
	x_values, real(h), ';real;' 
	    );

hold on;

#plot(x_values, ncoOutCos);
#plot(x_values, nco_in + 2);


#plot(x_values, dclImLp2Path + 10);
#plot(x_values, dclReLp1Path + 14);



hold off;