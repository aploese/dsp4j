function b = berechneFilter(fu, f, fo,fa, m)

%m = 64; %Filterordnung nicht kleiner als 64!!! 64 ist OK...
f_u_edge = (f+fu)/2;
f_o_edge = (f+fo)/2;

w_u_off = fu/ fa*2;
w_u_edge = f_u_edge / fa*2;
w_o_edge = f_o_edge / fa*2;
w_o_off = fo / fa*2;

printf('f:      f=%f\tw=%f\n', f, f/fa*2);
printf('u_off:      f=%f\tw=%f\n', fu, w_u_off);
printf('u_edge: f=%f\tw=%f\n', f_u_edge, w_u_edge);
printf('o_edge: f=%f\tw=%f\n', f_o_edge, w_o_edge);
printf('o_off:     f=%f\tw=%f\n', fo, w_o_off);

f   =  [0 w_u_off w_u_edge w_o_edge w_o_off   1];     % Frequenzen der Sperr- und Durchlassbereiche
g   = [5 0 40  40 0 5];           % Impulsantwort
b = remez(m,f,g);         % Filterentwurf nach Remez
%save test-1.dat b
% b
%load test.dat b

[H W] = freqz(b,1,512);   % Berechnung des Frequenzgangs

   % # grafische Ausgabe # %
    string = sprintf('Gewuenschte/Aktuelle Antwort - Filterordnung: %d', m);
    figure;
    plot(f,g,W/pi,abs(H));
    line([0 w_u_off],[ 2-max(abs(H)) 2-max(abs(H))],'linestyle','--'); % Erzeugung
%    line([0 0.5],[max(abs(H)) max(abs(H))],'linestyle','--');      % einer Linie
%    line([0.5 1],[max(abs(H(400:length(H))))...                    % im Plot
%        max(abs(H(400:length(H))))],'linestyle','--');
%    line([0.3 0.3],[ 2-max(abs(H)) 0],'linestyle','--');
%    line([0.5 0.5],[ max(abs(H)) max(abs(H(400:length(H))))],'linestyle','--');
    grid;
    xlabel('Omega/pi');
    ylabel('|H(exp(j*Omega))|');
    title(string);

endfunction