package net.sf.dsp4j.fms4j.fms;

import java.util.Arrays;
import net.sf.dsp4j.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmsDemodulator {

    private final static Logger LOG = LoggerFactory.getLogger(FmsDemodulator.class);
    private DecodeState state = DecodeState.DIENSTKENNUNG;
    private final FmsCrc crc = new FmsCrc();
    private final BitStack bitStack = new BitStack(0);
    private FmsData fmsData;
    private final FmsContainerListener fmsContainerListener;
    private boolean lastTxtBlock;
    private boolean firstTxtBlock;
    private int eotCount;

    public FmsDemodulator(FmsContainerListener fmsContainerListener) {
        super();
        reset();
        this.fmsContainerListener = fmsContainerListener;
    }

    @In
    public FmsData setX(boolean sample) {
        crc.addBit(sample);
        switch (state) {
            case DIENSTKENNUNG:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    try {
                        fmsData.setDienstkennung(Dienstkennung.valueOf(bitStack.toInt()));
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        boolean fail = true;
                        if (fmsContainerListener != null) {
                            fail = fmsContainerListener.error(fmsData);
                        }
                        if (fail) {
                            LOG.info("Failed: " + fmsData, ex);
                            reset();
                            return FmsData.ERROR_DUMMY;
                        }
                    }
                    bitStack.reset(4);
                    setState(DecodeState.LAENDERKENNUNG);
                }
                break;
            case LAENDERKENNUNG:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    fmsData.setLaenderkennung(Laenderkennung.valueOf(bitStack.toByte()));
                    bitStack.reset(8);
                    setState(DecodeState.ORTSKENNUNG);
                }
                break;
            case ORTSKENNUNG:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    fmsData.setOrtskennung(bitStack.toByte());
                    bitStack.reset(16);
                    setState(DecodeState.FAHRZEUGKENNUNG);
                }
                break;
            case FAHRZEUGKENNUNG:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    fmsData.setFahrzeugkennung(bitStack.toShort());
                    bitStack.reset(4);
                    setState(DecodeState.STATUS);
                }
                break;
            case STATUS:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    fmsData.setStatus(bitStack.toByte());
                    bitStack.reset(0);
                    setState(DecodeState.BAUSTUFENKENNUNG);
                }
                break;
            case BAUSTUFENKENNUNG:
                fmsData.setBaustufenkennung(sample ? Baustufenkennung.BEIDE_RICHTUNGEN : Baustufenkennung.NUR_VOM_FZG);
                setState(DecodeState.RICHTUNGSKENNUNG);
                break;
            case RICHTUNGSKENNUNG:
                fmsData.setRichtungskennung(sample ? Richtungskennung.LST_ZU_FZG : Richtungskennung.FZG_ZU_LST);
                setState(DecodeState.TKI);
                bitStack.reset(2);
                break;
            case TKI:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    fmsData.setTaktischeKurzinfirmation(TaktischeKurzinformation.valueOf(bitStack.toInt()));
                    bitStack.reset(7);
                    setState(DecodeState.CRC_SUM);
                }
                break;
            case CRC_SUM:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    if (!crc.isValid()) {
                        boolean fail = true;
                        if (fmsContainerListener != null) {
                            fail = fmsContainerListener.crcError(fmsData);
                        }
                        if (fail) {
                            LOG.info("crc error " + fmsData.toCsvString());
                            reset();
                            return FmsData.CRC_ERROR_DUMMY;
                        }
                    }
                    bitStack.reset(0);
                    setState(DecodeState.SCHLUSSBIT);
                }
                break;
            case SCHLUSSBIT:
                crc.reset();
                if (fmsData.getStatus() == 10) {
                    firstTxtBlock = true;
                    lastTxtBlock = false;
                    bitStack.reset(8);
                    setState(DecodeState.TXT_LENGTH);
                } else {
                    if (fmsContainerListener != null) {
                        fmsContainerListener.success(fmsData);
                    }
                    LOG.debug("fms msg " + fmsData);
                    final FmsData data = fmsData;
                    reset();
                    return data;
                }
                break;
            case TXT_LENGTH:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    try {
                        bitStack.decodeTextLength();
                    } catch (ParityException ex) {
                        boolean fail = true;
                        if (fmsContainerListener != null) {
                            fail = fmsContainerListener.txtLengthParityError(fmsData);
                        }
                        if (fail) {
                            LOG.info("Faied: " + fmsData, ex);
                            reset();
                            return FmsData.TXT_LENGTH_PARITY_ERROR_DUMMY;
                        }
                    }
                    bitStack.reset(8);
                    setState(DecodeState.TXT_CHAR);
                }
                break;
            case TXT_CHAR:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    try {
                        bitStack.decodeChar();
                    } catch (ParityException ex) {
                        boolean fail = true;
                        if (fmsContainerListener != null) {
                            fail = fmsContainerListener.txtCharParityError(fmsData, bitStack.getText());
                        }
                        if (fail) {
                            LOG.info("Faied: " + fmsData, ex);
                            reset();
                            return FmsData.TXT_CHAR_PARITY_ERROR_DUMMY;
                        }
                    }
                    if (firstTxtBlock && bitStack.text.length() + eotCount == 3) {
                        firstTxtBlock = false;
                        bitStack.reset(4);
                        setState(DecodeState.TXT_STATUS);
                    } else if ((bitStack.text.length() + eotCount - 3) % 4 == 0) {//Wir sind entweder bei 3 (1. Block) oder am Ende einer de folgenden Blöcke
                        bitStack.reset(4);
                        setState(DecodeState.TXT_STATUS);
                    } else {
                        bitStack.reset(8);
                    }
                }
                break;
            case TXT_STATUS:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    lastTxtBlock = bitStack.toByte() == 0x0B;
                    if (!lastTxtBlock) {
                        if (bitStack.toByte() != fmsData.getStatus()) {
                            boolean fail = true;
                            String errorTxt = String.format("TXT: %s\nStatus mismatch expected: %d but was: %d", bitStack.getText(), fmsData.getStatus(), bitStack.toByte());
                            if (fmsContainerListener != null) {
                                fail = fmsContainerListener.txtError(fmsData, errorTxt);
                            }
                            if (fail) {
                                LOG.info("Faied: " + fmsData + errorTxt);
                                reset();
                                return FmsData.TXT_ERROR_DUMMY;
                            }
                        }
                    }
                    bitStack.reset(0);
                    setState(DecodeState.TXT_BAUSTUFENKENNUNG);
                }
                break;
            case TXT_BAUSTUFENKENNUNG:
                if (fmsData.getBaustufenkennung() != (sample ? Baustufenkennung.BEIDE_RICHTUNGEN : Baustufenkennung.NUR_VOM_FZG)) {
                    boolean fail = true;
                    String errorTxt = String.format("TXT: %s\nBaustufenkennung mismatch expected: %s but was: %s", bitStack.getText(), fmsData.getBaustufenkennung(), sample ? Baustufenkennung.BEIDE_RICHTUNGEN : Baustufenkennung.NUR_VOM_FZG);
                    if (fmsContainerListener != null) {
                        fail = fmsContainerListener.txtError(fmsData, errorTxt);
                    }
                    if (fail) {
                        LOG.info("Faied: " + fmsData + errorTxt);
                        reset();
                        return FmsData.TXT_ERROR_DUMMY;
                    }
                }
                setState(DecodeState.TXT_RICHTUNGSKENNUNG);
                break;
            case TXT_RICHTUNGSKENNUNG:
                if (fmsData.getRichtungskennung() != (sample ? Richtungskennung.LST_ZU_FZG : Richtungskennung.FZG_ZU_LST)) {
                    boolean fail = true;
                    String errorTxt = String.format("TXT: %s\nRichtungskennung mismatch expected: %s but was: %s", bitStack.getText(), fmsData.getRichtungskennung(), sample ? Richtungskennung.LST_ZU_FZG : Richtungskennung.FZG_ZU_LST);
                    if (fmsContainerListener != null) {
                        fail = fmsContainerListener.txtError(fmsData, errorTxt);
                    }
                    if (fail) {
                        LOG.info("Faied: " + fmsData + errorTxt);
                        reset();
                        return FmsData.TXT_ERROR_DUMMY;
                    }
                }
                setState(DecodeState.TXT_TKI);
                bitStack.reset(2);
                break;
            case TXT_TKI:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    if (fmsData.getTaktischeKurzinfirmation() != TaktischeKurzinformation.valueOf(bitStack.toInt())) {
                        boolean fail = true;
                        String errorTxt = String.format("TXT: %s\nTaktischeKurzinformation mismatch expected: %s but was: %s", bitStack.getText(), fmsData.getTaktischeKurzinfirmation(), TaktischeKurzinformation.valueOf(bitStack.toInt()));
                        if (fmsContainerListener != null) {
                            fail = fmsContainerListener.txtError(fmsData, errorTxt);
                        }
                        if (fail) {
                            LOG.info("Faied: " + fmsData + errorTxt);
                            reset();
                            return FmsData.TXT_ERROR_DUMMY;
                        }
                    }
                    bitStack.reset(7);
                    setState(DecodeState.TXT_CRC_SUM);
                }
                break;
            case TXT_CRC_SUM:
                bitStack.addBit(sample);
                if (bitStack.isFull()) {
                    if (!crc.isValid()) {
                        fmsData.setText(bitStack.getText());
                        boolean fail = true;
                        if (fmsContainerListener != null) {
                            fail = fmsContainerListener.txtCrcError(fmsData, bitStack.getText());
                        }
                        if (fail) {
                            LOG.info("crc error " + fmsData);
                            reset();
                            return FmsData.TXT_CRC_ERROR_DUMMY;
                        }
                    }
                    bitStack.reset(0);
                    setState(DecodeState.TXT_SCHLUSSBIT);
                }

                break;
            case TXT_SCHLUSSBIT:
                crc.reset();
                if (lastTxtBlock) {
                    fmsData.setText(bitStack.getText());
                    bitStack.reset(0);
                    if (fmsContainerListener != null) {
                        fmsContainerListener.success(fmsData);
                    }
                    LOG.debug("fms msg " + fmsData);
                    final FmsData data = fmsData;
                    reset();
                    return data;
                } else {
                    bitStack.reset(8);
                    setState(DecodeState.TXT_CHAR);
                }
                break;
            default:
                throw new RuntimeException("Unknown State: " + state);

        }
        return null;
    }

    private void setState(DecodeState decodeState) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("new state: " + decodeState);
        }
        state = decodeState;
    }

    public FmsData getFmsData() {
        return fmsData;
    }

    public void reset() {
        crc.setCrc(0);
        bitStack.reset();
        bitStack.reset(4);
        fmsData = new FmsData();
        eotCount = 0;
        state = DecodeState.DIENSTKENNUNG;
    }

    public DecodeState getDecodeState() {
        return state;
    }

    class ParityException extends Exception {
    }

    class BitStack {

        int size;
        int index;
        int data;
        boolean parityEven = true;
        StringBuilder text = new StringBuilder();
        int textLength;

        private BitStack(int size) {
            reset(size);
        }

        private void addBit(boolean bitIsSet) {
            if (bitIsSet) {
                parityEven = !parityEven;
                data |= 1 << (index);
            }
            index++;
        }

        private boolean isFull() {
            return index >= size;
        }

        private int toInt() {
            if (size <= 4) {
                return data;
            } else if (size <= 8) {
                return ((data & 0x000F) << 4) | ((data & 0x00F0) >> 4); // swap nibbles
            } else if (size <= 16) {
                return ((data & 0x000F) << 12) | ((data & 0x00F0) << 4) | ((data & 0x0F00) >> 4) | ((data & 0xF000) >> 12); // swap nibbles
            } else {
                throw new RuntimeException("cant handle size > 16");
            }
        }

        private short toShort() {
            if (size <= 4) {
                return (short) data;
            } else if (size <= 8) {
                return (short) (((data & 0x000F) << 4) | ((data & 0x00F0) >> 4)); // swap nibbles
            } else if (size <= 16) {
                return (short) (((data & 0x000F) << 12) | ((data & 0x00F0) << 4) | ((data & 0x0F00) >> 4) | ((data & 0xF000) >> 12)); // swap nibbles
            } else {
                throw new RuntimeException("cant handle size > 16");
            }
        }

        private byte toByte() {
            if (size <= 4) {
                return (byte) data;
            } else if (size <= 8) {
                return (byte) (((data & 0x000F) << 4) | ((data & 0x00F0) >> 4)); // swap nibbles
            } else {
                throw new RuntimeException("cant handle size > 8");
            }
        }

        private void reset(int size) {
            this.size = size;
            index = 0;
            data = 0;
            parityEven = true;
        }

        private void decodeTextLength() throws ParityException {
            textLength = (data & 0x7F) * 2 - 1;
            if (parityEven) {
                throw new ParityException();
            }
        }

        public void reset() {
            reset(0);
            text.delete(0, text.length());
            textLength = 0;
        }

        private void decodeChar() throws ParityException {
            if ((data & 0x7F) == 0x04) {
                //just ignore EOT
                eotCount++;
            } else {
                text.append(byteToChar((byte) (data & 0x7F)));
            }
            if (!parityEven) {
                throw new ParityException();
            }
        }

        private String getText() {
            return text.toString();
        }

        private char byteToChar(byte b) {
            switch (b) {
                case 0x00:
                    return ' ';
                case 0x01:
                    return ' ';
                case 0x02:
                    return ' ';
                case 0x03:
                    return ' ';
                case 0x04:
                    return ' ';
                case 0x05:
                    return ' ';
                case 0x06:
                    return ' ';
                case 0x07:
                    return ' ';
                case 0x08:
                    return '\b';
                case 0x09:
                    return '\t';
                case 0x0a:
                    return '\n';
                case 0x0b:
                    return ' ';
                case 0x0c:
                    return '\f';
                case 0x0d:
                    return '\n';
                case 0x0e:
                    return ' ';
                case 0x0f:
                    return ' ';
                case 0x10:
                    return ' ';
                case 0x11:
                    return ' ';
                case 0x12:
                    return ' ';
                case 0x13:
                    return ' ';
                case 0x14:
                    return ' ';
                case 0x15:
                    return ' ';
                case 0x16:
                    return ' ';
                case 0x17:
                    return ' ';
                case 0x18:
                    return ' ';
                case 0x19:
                    return ' ';
                case 0x1a:
                    return ' ';
                case 0x1b:
                    return ' ';
                case 0x1c:
                    return ' ';
                case 0x1d:
                    return ' ';
                case 0x1e:
                    return ' ';
                case 0x1f:
                    return ' ';
                case 0x20:
                    return ' ';
                case 0x21:
                    return '!';
                case 0x22:
                    return '"';
                case 0x23:
                    return '#';
                case 0x24:
                    return '$';
                case 0x25:
                    return '%';
                case 0x26:
                    return '&';
                case 0x27:
                    return '\'';
                case 0x28:
                    return '(';
                case 0x29:
                    return ')';
                case 0x2a:
                    return '*';
                case 0x2b:
                    return '+';
                case 0x2c:
                    return ',';
                case 0x2d:
                    return '-';
                case 0x2e:
                    return '.';
                case 0x2f:
                    return '/';
                case 0x30:
                    return '0';
                case 0x31:
                    return '1';
                case 0x32:
                    return '2';
                case 0x33:
                    return '3';
                case 0x34:
                    return '4';
                case 0x35:
                    return '5';
                case 0x36:
                    return '6';
                case 0x37:
                    return '7';
                case 0x38:
                    return '8';
                case 0x39:
                    return '9';
                case 0x3a:
                    return ':';
                case 0x3b:
                    return ';';
                case 0x3c:
                    return '<';
                case 0x3d:
                    return '=';
                case 0x3e:
                    return '>';
                case 0x3f:
                    return '?';
                case 0x40:
                    return '§';
                case 0x41:
                    return 'A';
                case 0x42:
                    return 'B';
                case 0x43:
                    return 'C';
                case 0x44:
                    return 'D';
                case 0x45:
                    return 'E';
                case 0x46:
                    return 'F';
                case 0x47:
                    return 'G';
                case 0x48:
                    return 'H';
                case 0x49:
                    return 'I';
                case 0x4a:
                    return 'J';
                case 0x4b:
                    return 'K';
                case 0x4c:
                    return 'L';
                case 0x4d:
                    return 'M';
                case 0x4e:
                    return 'N';
                case 0x4f:
                    return 'O';
                case 0x50:
                    return 'P';
                case 0x51:
                    return 'Q';
                case 0x52:
                    return 'R';
                case 0x53:
                    return 'S';
                case 0x54:
                    return 'T';
                case 0x55:
                    return 'U';
                case 0x56:
                    return 'V';
                case 0x57:
                    return 'W';
                case 0x58:
                    return 'X';
                case 0x59:
                    return 'Y';
                case 0x5a:
                    return 'Z';
                case 0x5b:
                    return 'Ä';
                case 0x5c:
                    return 'Ö';
                case 0x5d:
                    return 'Ü';
                case 0x5e:
                    return '^';
                case 0x5f:
                    return '_';
                case 0x60:
                    return '`';
                case 0x61:
                    return 'a';
                case 0x62:
                    return 'b';
                case 0x63:
                    return 'c';
                case 0x64:
                    return 'd';
                case 0x65:
                    return 'e';
                case 0x66:
                    return 'f';
                case 0x67:
                    return 'g';
                case 0x68:
                    return 'h';
                case 0x69:
                    return 'i';
                case 0x6a:
                    return 'j';
                case 0x6b:
                    return 'k';
                case 0x6c:
                    return 'l';
                case 0x6d:
                    return 'm';
                case 0x6e:
                    return 'n';
                case 0x6f:
                    return 'o';
                case 0x70:
                    return 'p';
                case 0x71:
                    return 'q';
                case 0x72:
                    return 'r';
                case 0x73:
                    return 's';
                case 0x74:
                    return 't';
                case 0x75:
                    return 'u';
                case 0x76:
                    return 'v';
                case 0x77:
                    return 'w';
                case 0x78:
                    return 'x';
                case 0x79:
                    return 'y';
                case 0x7a:
                    return 'z';
                case 0x7b:
                    return 'ä';
                case 0x7c:
                    return 'ö';
                case 0x7d:
                    return 'ü';
                case 0x7e:
                    return 'ß';
                default:
                    throw new RuntimeException();
            }
        }
    }

    public enum DecodeState {

        DIENSTKENNUNG,
        LAENDERKENNUNG,
        ORTSKENNUNG,
        FAHRZEUGKENNUNG,
        STATUS,
        BAUSTUFENKENNUNG,
        RICHTUNGSKENNUNG,
        TKI,
        CRC_SUM,
        SCHLUSSBIT,
        TXT_LENGTH,
        TXT_CHAR,
        TXT_STATUS,
        TXT_BAUSTUFENKENNUNG,
        TXT_RICHTUNGSKENNUNG,
        TXT_TKI,
        TXT_CRC_SUM,
        TXT_SCHLUSSBIT,
    }
}
