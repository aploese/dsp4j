package net.sf.dsp4j;

/**
 *
 * @author aploese
 */
public interface PllLockListener {

    void locked(Object src);

    void tooLow(Object src);

    void tooHigh(Object src);
}
