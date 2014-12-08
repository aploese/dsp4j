package net.sf.dsp4j.fms4j.console;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aploese
 */
public class ProfilerEntry {

    public static void main(String[] args) throws Exception {
        Console c = new Console();
        c.parseCmdLine("-f", "/media/hdd-apl/backup-kleiner.local/test-16bit-44.1kHz-20110416-01.wav", "-fms /home/aploese/FMS32-PRO/", "-sq", "900");
    }

}
