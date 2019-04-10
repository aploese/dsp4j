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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aploese
 */
public class FmsFileDataStore implements FmsDataStore {
    private File dir;

    @Override
    public String getFmsOrtsmname(FmsData data) {
        FmsOrtsdaten d = ortsdaten.get(data.getOrtsId());
        if (d != null) {
            return d.name;
        } else {
            return FmsData.formatOrtskennung(data.getOrtskennung());
        }
    }

    @Override
    public String getFmsFahrzeugname(FmsData data) {
        FmsFahrzeugdaten d = fahrzeuge.get(data.getFahrzeugId());
        if (d != null) {
            return d.name;
        } else {
            return FmsData.formatFahrzeugkennung(data.getFahrzeugkennung());
        }
    }

    @Override
    public String getZveiname(int id) {
        FmsZveidaten d = zvei.get(id);
        if (d != null) {
            return d.name;
        } else {
            return Integer.toString(id);
        }
    }

    public static class FmsFahrzeugdaten {
          final int id;
          private String name;
          private String description;
          
          public FmsFahrzeugdaten(int id) {
              this.id = id;
          }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("id: %08x", id));
            sb.append(", name: ").append(name);
            sb.append(", description: ").append(description);
            return sb.toString();
        }
    }
    
    public static class FmsOrtsdaten {
          final int id;
          private String name;
        
          public FmsOrtsdaten(int id) {
              this.id = id;
          }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("id: %06x", id));
            sb.append(", name: ").append(name);
            return sb.toString();
        }
    }
    
    public static class FmsZveidaten {
          final int id;
          private String name;
        
          public FmsZveidaten(int id) {
              this.id = id;
          }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("id: ").append(id);
            sb.append(", name: ").append(name);
            return sb.toString();
        }
    }
    
    Map<Integer, FmsOrtsdaten> ortsdaten = new HashMap();
    Map<Integer, FmsFahrzeugdaten> fahrzeuge = new HashMap();
    Map<Integer, FmsZveidaten> zvei = new HashMap();
    
    private void readFarzeuge() throws IOException {
        char[] cbuf = new char[515];
        File fzg = new File(dir, "Fahrzeug.DAT");
        FileInputStream fis = new FileInputStream(fzg);
        InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
        while (isr.read(cbuf) > 0) {
            FmsFahrzeugdaten f = new FmsFahrzeugdaten((int)Long.parseLong(String.copyValueOf(cbuf, 0, 8), 16));
            f.setName(copyText(cbuf, 8, 29));
            f.setDescription(copyText(cbuf, 38, 29));
            fahrzeuge.put(f.id, f);
        }
    }
    
    private void readOrte() throws IOException {
        char[] cbuf = new char[56];
        File ort = new File(dir, "Orte.DAT");
        FileInputStream fis = new FileInputStream(ort);
        InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
        while (isr.read(cbuf) > 0) {
            FmsOrtsdaten o = new FmsOrtsdaten((int)Long.parseLong(String.copyValueOf(cbuf, 0, 6), 16));
            o.setName(copyText(cbuf, 6, 55 - 6));
            ortsdaten.put(o.id, o);
        }
    }
    
    private void readZvei() throws IOException {
        char[] cbuf = new char[323];
        File zv = new File(dir, "TON5.DAT");
        FileInputStream fis = new FileInputStream(zv);
        InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
        while (isr.read(cbuf) > 0) {
            try {
            FmsZveidaten z = new FmsZveidaten(Integer.parseInt(String.copyValueOf(cbuf, 0, 5)));

            z.setName(copyText(cbuf, 5, 50));
//            z.setaKTION(copyText(cbuf, 55, 130));
            zvei.put(z.id, z);
            } catch (NumberFormatException e) {
                
            } 
        }
    }
    
    private String copyText(char[] cbuf, int start, int length) {
        for (int i = length; i > 0; i--) {
            if (cbuf[start + i] != ' ') {
                return String.copyValueOf(cbuf, start, i + 1);
            }
        }
        return "";
    }
    
    public void read(String pathName) throws IOException {
        read(new File(pathName));
    }
    
    public void read(File fms32Dir) throws IOException {
        dir = fms32Dir;
        readFarzeuge();
        readOrte();
        readZvei();
    }
    
    public static void main(String[] args) throws Exception {
        FmsFileDataStore ds = new FmsFileDataStore();
        ds.read("/home/aploese/FMS32-PRO");
        for (Map.Entry<Integer, FmsFahrzeugdaten> e : ds.fahrzeuge.entrySet()) {
            System.out.println(e.getValue());
        }
        for (Map.Entry<Integer, FmsOrtsdaten> e : ds.ortsdaten.entrySet()) {
            System.out.println(e.getValue());
        }
        for (Map.Entry<Integer, FmsZveidaten> e : ds.zvei.entrySet()) {
            System.out.println(e.getValue());
        }
    }
    
}
