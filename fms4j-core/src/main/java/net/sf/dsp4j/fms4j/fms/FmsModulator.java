package net.sf.dsp4j.fms4j.fms;

import java.util.Arrays;
import net.sf.dsp4j.datatypes._double.SinWaveSource;

/**
 *
 * @author aploese
 */
public class FmsModulator extends SinWaveSource {

    public final static int SYMBOL_RATE = 1200;
    public final static int SPACE_FREQ = 1800;
    public final static int MARK_FREQ = 1200;
    public final static int[] SYNC_BLOCK = new int[]{0, 0, 0, 1, 1, 0, 1, 0};
    private final FmsCrc crc;

    public FmsModulator(double precisition) {
        super(precisition);
        crc = new FmsCrc();
    }

    public void addData(FmsData data) {


        addPeriods(MARK_FREQ, 2.25);

        for (int i = 0; i < 12; i++) {
            addBit(true);
        }
        for (int i = 0; i < SYNC_BLOCK.length; i++) {
            addBit(SYNC_BLOCK[i] == 1);
        }
        crc.setCrc(0);
        addBits(4, data.getDienstkennung().ordinal());
        addBits(4, data.getLaenderkennung().ordinal());
        addBits(8, data.getOrtskennung());
        addBits(16, data.getFahrzeugkennung());
        addBits(4, data.getStatus());
        addBits(1, data.getBaustufenkennung().ordinal());
        addBits(1, data.getRichtungskennung().ordinal());
        addBits(2, data.getTaktischeKurzinfirmation().ordinal());

        addCrc();

        addLastBit(); //Schlussbit

        crc.reset();
        if (data.getStatus() == (byte) 10) {
            
            int textLength = data.getText().length();// 0x04 will be appended
            int fillLength;
            if (textLength > 3) {
                fillLength = (textLength - 3) % 4;
            } else {
                fillLength = 3 - textLength;
            }
            textLength += fillLength;
            addTextLength(textLength);
            for (int i = 0; i < 3; i++) {
                if (i < textLength - fillLength) {
                    addChar(data.getText().charAt(i));
                } else {
                    addEOT();
                }
            }
            addBits(4, (textLength == 3) ? 0xB : 0xA); // Status 10
            addBits(1, data.getBaustufenkennung().ordinal());
            addBits(1, data.getRichtungskennung().ordinal());
            addBits(2, data.getTaktischeKurzinfirmation().ordinal());
            addCrc();
            addLastBit(); //Schlussbit
            if (textLength > 3) {
            for (int b = 0; b < ((textLength - 3) / 4); b++) {
                for (int i = b * 4 + 3; i < 4 + b * 4 + 3; i++) {
                if (i < textLength - fillLength) {
                    addChar(data.getText().charAt(i));
                } else {
                    addEOT();
                }
                }
                addBits(4, b == (textLength - 3) / 4 ? 0xB : 0xA); //
                addBits(1, data.getBaustufenkennung().ordinal());
                addBits(1, data.getRichtungskennung().ordinal());
                addBits(2, data.getTaktischeKurzinfirmation().ordinal());
                addCrc();
                addLastBit(); //Schlussbit
            }
            }
        }

        addPeriods(MARK_FREQ, 2.25);
    }

    private void addBit(boolean bit) {
        crc.addBit(bit);
        addDuration(bit ? MARK_FREQ : SPACE_FREQ, 1.0 / SYMBOL_RATE);
    }

    private void addCrc() {
        for (int i = 0; i < 7; i++) {
            crc.addBit(false);
        }
        final int myCrc = crc.getCrc();
        for (int i = 6; i >= 0; i--) {
            addBit(((myCrc >> i) & 0x01) == 0x01);
        }
    }

    private void addTextLength(int length) {
        boolean parityEven = true;
        int data = (length + 1) / 2;
        for (int i = 0; i < 7; i++) {
            boolean bit = ((data >> i) & 0x01) == 0x01;
            if (bit) {
                parityEven = !parityEven;
            }
            addBit(bit);
        }
        addBit(parityEven);
    }

    private void addChar(char c) {
        boolean parityEven = true;
        int data = charToByte(c);
        for (int i = 0; i < 7; i++) {
            boolean bit = ((data >> i) & 0x01) == 0x01;
            if (bit) {
                parityEven = !parityEven;
            }
            addBit(bit);
        }
        addBit(!parityEven);
    }

    private void addBits(int length, int data) {
        if (length <= 4) {
        } else if (length <= 8) {
            data = ((data & 0x000F) << 4) | ((data & 0x00F0) >> 4); // swap nibbles
        } else if (length <= 16) {
            data = ((data & 0x000F) << 12) | ((data & 0x00F0) << 4) | ((data & 0x0F00) >> 4) | ((data & 0xF000) >> 12);
        }
        for (int i = 0; i < length; i++) {
            addBit(((data >> i) & 0x01) == 0x01);
        }
    }

    private void addLastBit() {
        addBit(false);
        crc.reset();
    }

    public byte charToByte(char c) {
        switch (c) {
            case '\b':
                return 0x08;
            case '\t':
                return 0x09;
            case '\n':
                return 0x0d;
            case '\f':
                return 0x0c;
            case '\r':
                return 0x0d;
            case ' ':
                return 0x20;
            case '!':
                return 0x21;
            case '"':
                return 0x22;
            case '#':
                return 0x23;
            case '$':
                return 0x24;
            case '%':
                return 0x25;
            case '&':
                return 0x26;
            case '\'':
                return 0x27;
            case '(':
                return 0x28;
            case ')':
                return 0x29;
            case '*':
                return 0x2a;
            case '+':
                return 0x2b;
            case ',':
                return 0x2c;
            case '-':
                return 0x2d;
            case '.':
                return 0x2e;
            case '/':
                return 0x2f;
            case '0':
                return 0x30;
            case '1':
                return 0x31;
            case '2':
                return 0x32;
            case '3':
                return 0x33;
            case '4':
                return 0x34;
            case '5':
                return 0x35;
            case '6':
                return 0x36;
            case '7':
                return 0x37;
            case '8':
                return 0x38;
            case '9':
                return 0x39;
            case ':':
                return 0x3a;
            case ';':
                return 0x3b;
            case '<':
                return 0x3c;
            case '=':
                return 0x3d;
            case '>':
                return 0x3e;
            case '?':
                return 0x3f;
            case '§':
                return 0x40;
            case 'A':
                return 0x41;
            case 'B':
                return 0x42;
            case 'C':
                return 0x43;
            case 'D':
                return 0x44;
            case 'E':
                return 0x45;
            case 'F':
                return 0x46;
            case 'G':
                return 0x47;
            case 'H':
                return 0x48;
            case 'I':
                return 0x49;
            case 'J':
                return 0x4a;
            case 'K':
                return 0x4b;
            case 'L':
                return 0x4c;
            case 'M':
                return 0x4d;
            case 'N':
                return 0x4e;
            case 'O':
                return 0x4f;
            case 'P':
                return 0x50;
            case 'Q':
                return 0x51;
            case 'R':
                return 0x52;
            case 'S':
                return 0x53;
            case 'T':
                return 0x54;
            case 'U':
                return 0x55;
            case 'V':
                return 0x56;
            case 'W':
                return 0x57;
            case 'X':
                return 0x58;
            case 'Y':
                return 0x59;
            case 'Z':
                return 0x5a;
            case 'Ä':
                return 0x5b;
            case 'Ö':
                return 0x5c;
            case 'Ü':
                return 0x5d;
            case '^':
                return 0x5e;
            case '_':
                return 0x5f;
            case '`':
                return 0x60;
            case 'a':
                return 0x61;
            case 'b':
                return 0x62;
            case 'c':
                return 0x63;
            case 'd':
                return 0x64;
            case 'e':
                return 0x65;
            case 'f':
                return 0x66;
            case 'g':
                return 0x67;
            case 'h':
                return 0x68;
            case 'i':
                return 0x69;
            case 'j':
                return 0x6a;
            case 'k':
                return 0x6b;
            case 'l':
                return 0x6c;
            case 'm':
                return 0x6d;
            case 'n':
                return 0x6e;
            case 'o':
                return 0x6f;
            case 'p':
                return 0x70;
            case 'q':
                return 0x71;
            case 'r':
                return 0x72;
            case 's':
                return 0x73;
            case 't':
                return 0x74;
            case 'u':
                return 0x75;
            case 'v':
                return 0x76;
            case 'w':
                return 0x77;
            case 'x':
                return 0x78;
            case 'y':
                return 0x79;
            case 'z':
                return 0x7a;
            case 'ä':
                return 0x7b;
            case 'ö':
                return 0x7c;
            case 'ü':
                return 0x7d;
            case 'ß':
                return 0x7e;
            default:
                throw new RuntimeException();
        }
    }

    private void addEOT() {
        boolean parityEven = true;
        int data = 0x04;;
        for (int i = 0; i < 7; i++) {
            boolean bit = ((data >> i) & 0x01) == 0x01;
            if (bit) {
                parityEven = !parityEven;
            }
            addBit(bit);
        }
        addBit(!parityEven);
    }
}
