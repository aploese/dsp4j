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
public enum Laenderkennung {
    SA("Sachsen"), BUND("Bund"), BW("Baden-Württemberg"), BY_I("Bayern I"), B("Berlin"), BR("Bremen"), HH("Hamburg"), HE("Hessen"), NS("Niedersachsen"), NRW("Nordrhein-Westfalen"), RP("Rheinland-Pfalz"), SH("Schleswig-Holstein"), SL("Saarland"), BY_II("Bayern II"), LK_E("MV oder SAH"), LK_F("BRB oder THÜ");
    //        MV("Mecklenburg-Vorpommern"), // Ort 00..49
    //        SAH("Sachsen-Anhalt"), // Ort 50..99
    //        BRB("Brandenburg"), // Ort 00..49
    //        THÜ("Thüringen"); // Ort 50..99
    public final String label;

    private Laenderkennung(String label) {
        this.label = label;
    }

    public static Laenderkennung valueOf(byte value) {
        return Laenderkennung.values()[value];
    }
    
}
