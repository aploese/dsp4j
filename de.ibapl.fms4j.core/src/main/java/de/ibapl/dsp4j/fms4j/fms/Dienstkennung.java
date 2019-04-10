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
package de.ibapl.dsp4j.fms4j.fms;

/**
 *
 * @author aploese
 */
public enum Dienstkennung {
    N_V("nicht vergeben"), 
    P_D_BL("Polizei der Bundesländer"), 
    BGS("Bundesgrenzschutz"), 
    BKA("Bundeskriminalamt"), 
    KATS("Katastrophenschutz"), 
    ZOLL("Zoll"), 
    FW("Feuerwehr"), 
    THW("Technisches Hilfswerk"), 
    ASB("Arbeiter-Samariter-Bund"), 
    DRK("Deutsches Rotes Kreuz"), 
    JUH("Johanniter-Unfall-Hilfe"), 
    MHD("Malteser Hilfsdienst"), 
    DLRG("Deutsche Lebensrettungs-Gesellschaft"), 
    S_R_D("sonstige Rettungsdienste"), 
    Z_V("Zivilschutz"), 
    F_W_T("Fernwirktelegramm");
    public final String label;

    private Dienstkennung(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Dienstkennung valueOf(int i) {
        return values()[i];
    }
    
}
