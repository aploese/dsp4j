
function [data k tastwerte] = ftg(f1, f2, f3, f4, f5, fa, t_on, t_off, noise_gain)

T_Signal = round(t_on * fa);
T_Pause = round(t_off * fa);

Omega1=2 * pi * f1 / fa;  % Auf Abtastrate normierte Frequenz
n_t1_on = 1;
n_t1_off = n_t1_on + T_Signal;

Omega2=2 * pi * f2 / fa; 
n_t2_on = n_t1_off + T_Pause;
n_t2_off = n_t2_on + T_Signal;

Omega3=2 * pi * f3 / fa; 
n_t3_on = n_t2_off + T_Pause;
n_t3_off = n_t3_on + T_Signal;

Omega4=2 * pi * f4 / fa; 
n_t4_on = n_t3_off + T_Pause;
n_t4_off = n_t4_on + T_Signal;
	
Omega5=2 * pi * f5 / fa; 
n_t5_on = n_t4_off + T_Pause;
n_t5_off = n_t5_on + T_Signal;
			
tastwerte = n_t5_off + T_Pause;

printf('Tastwerte %d\n', tastwerte);

my_noise = rand(tastwerte); 

k = 0: tastwerte - 1;

data = zeros(tastwerte, 1, 'float');

for i = n_t1_on : n_t1_off
	data(i) = my_noise(i) *  noise_gain + sin(Omega1 * k(i));
endfor
for i = n_t1_off : n_t2_on
	data(i) = my_noise(i) *  noise_gain;
endfor

for i = n_t2_on : n_t2_off
	data(i) = my_noise(i) *  noise_gain + sin(Omega2 * k(i));
endfor
for i = n_t2_off : n_t3_on
	data(i) = my_noise(i) *  noise_gain;
endfor

for i = n_t3_on : n_t3_off
	data(i) = my_noise(i) *  noise_gain + sin(Omega3 * k(i));
endfor
for i = n_t3_off : n_t4_on
	data(i) = my_noise(i) *  noise_gain;
endfor

for i = n_t4_on : n_t4_off
	data(i) = my_noise(i) *  noise_gain + sin(Omega4 * k(i));
endfor
for i = n_t4_off : n_t5_on
	data(i) = my_noise(i) *  noise_gain;
endfor

for i = n_t5_on : n_t5_off
	data(i) = my_noise(i) *  noise_gain + sin(Omega5 * k(i));
endfor
for i = n_t5_off : tastwerte
	data(i) = my_noise(i) *  noise_gain;
endfor

endfunction