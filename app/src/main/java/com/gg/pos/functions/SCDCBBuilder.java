package com.gg.pos.functions;

public class SCDCBBuilder {
    public enum InternationalType {
        USA,
        France,
        Germany,
        UK,
        Denmark,
        Sweden,
        Italy,
        Spain,
        Japan,
        Norway,
        Denmark2,
        Spain2,
        LatinAmerica,
        Korea
    }

    public enum CodePageType {
        CP437,
        Katakana,
        CP850,
        CP860,
        CP863,
        CP865,
        CP1252,
        CP866,
        CP852,
        CP858,
        Japanese,
        SimplifiedChinese,
        TraditionalChinese,
        Hangul
    }

    public enum CursorMode {
        Off,
        Blink,
        On,
    }

    public enum ContrastMode {
        Minus3,
        Minus2,
        Minus1,
        Default,
        Plus1,
        Plus2,
        Plus3,
    }

    static SCDCBBuilder createCommandBuilder(){
        return new SCDCBBuilder();
    }

    private byte[] command = new byte[0];

    public void appendByte(byte data) {
        byte[] dataArray = {data};

        appendByte(dataArray);
    }

    public void appendByte(byte[] appendCommand) {
        byte[] tempCommand = new byte[command.length + appendCommand.length];

        System.arraycopy(      command, 0, tempCommand, 0,                    command.length);
        System.arraycopy(appendCommand, 0, tempCommand, command.length, appendCommand.length);

        command = tempCommand;
    }

    public byte[] getCommand() {
        return command;
    }

    public void appendBackSpace() {
        byte data[] = {0x08};
        appendByte(data);
    }

    public void appendHorizontalTab() {
        byte data[] = {0x09};
        appendByte(data);
    }

    public void appendLineFeed() {
        byte data[] = {0x0a};
        appendByte(data);
    }

    public void appendCarriageReturn() {
        byte data[] = {0x0d};
        appendByte(data);
    }

    public void appendGraphic(byte[] image) {
        byte data[]      = {0x1b, 0x20};
        byte imageData[] = new byte[5*160];

        if (imageData.length <= image.length) {
            System.arraycopy(image, 0, imageData, 0, imageData.length);
        }
        else {
            System.arraycopy(image, 0, imageData, 0, image.length);
        }

        appendByte(data);
        appendByte(imageData);
    }

    public void appendCharacterSet(InternationalType internationalType, CodePageType codePageType) {
        appendCharacterSetInternational(internationalType);
        appendCharacterSetCodePage(codePageType);
    }

    public void appendCharacterSetInternational(InternationalType internationalType) {
        byte international;

        switch (internationalType) {
            default:            international = 0x00;   break;
//          case USA:           international = 0x00;   break;
            case France:        international = 0x01;   break;
            case Germany:       international = 0x02;   break;
            case UK:            international = 0x03;   break;
            case Denmark:       international = 0x04;   break;
            case Sweden:        international = 0x05;   break;
            case Italy:         international = 0x06;   break;
            case Spain:         international = 0x07;   break;
            case Japan:         international = 0x08;   break;
            case Norway:        international = 0x09;   break;
            case Denmark2:      international = 0x0a;   break;
            case Spain2:        international = 0x0b;   break;
            case LatinAmerica:  international = 0x0c;   break;
            case Korea:         international = 0x0d;   break;
        }

        byte data[] = {0x1b, 0x52, international};
        appendByte(data);
    }

    public void appendCharacterSetCodePage(CodePageType codePageType) {
        byte codePage;

        switch (codePageType) {
            default:                    codePage = 0x30;    break;
//          case CP437:                 codePage = 0x30;    break;
            case Katakana:              codePage = 0x31;    break;
            case CP850:                 codePage = 0x32;    break;
            case CP860:                 codePage = 0x33;    break;
            case CP863:                 codePage = 0x34;    break;
            case CP865:                 codePage = 0x35;    break;
            case CP1252:                codePage = 0x36;    break;
            case CP866:                 codePage = 0x37;    break;
            case CP852:                 codePage = 0x38;    break;
            case CP858:                 codePage = 0x39;    break;
            case Japanese:              codePage = 0x3a;    break;
            case SimplifiedChinese:     codePage = 0x3b;    break;
            case TraditionalChinese:    codePage = 0x3c;    break;
            case Hangul:                codePage = 0x3d;    break;
        }

        byte data[] = {0x1b, 0x52, codePage};
        appendByte(data);
    }

    public void appendDeleteToEndOfLine() {
        byte data[] = {0x1b, 0x5b, 0x30, 0x4b};
        appendByte(data);
    }

    public void appendClearScreen() {
        byte data[] = {0x1b, 0x5b, 0x32, 0x4a};
        appendByte(data);
    }

    public void appendHomePosition() {
        byte data[] = {0x1b, 0x5b, 0x48, 0x27};
        appendByte(data);
    }

    public void appendTurnOn(boolean turnOn) {
        byte turnOnOff;

        if (turnOn) {
            turnOnOff = 0x01;
        }
        else {
            turnOnOff = 0x00;
        }

        byte[] data = {0x1b, 0x5b, turnOnOff, 0x50};
        appendByte(data);
    }

    public void appendSpecifiedPosition(int x, int y) {
        if (! (1 <= x && x <= 20)) {
            x = 1;
        }

        if (! (1 <= y && y <= 2)) {
            y = 1;
        }

        byte data[] = {0x1b, 0x5b, (byte)y, 0x3b, (byte)x, 0x48};
        appendByte(data);
    }

    public void appendCursorMode(CursorMode cursorMode) {
        byte cursor;

        switch (cursorMode) {
//          case Off:
            default:        cursor = 0x00; break;
            case Blink:     cursor = 0x01; break;
            case On:        cursor = 0x02; break;
        }

        byte data[] = {0x1b, 0x5c, 0x3f, 0x4c, 0x43, cursor};

        appendByte(data);
    }

    public void appendContrastMode(ContrastMode contrastMode) {
        byte contrast;

        switch (contrastMode) {
            case Minus3:        contrast = 0x00;    break;
            case Minus2:        contrast = 0x01;    break;
            case Minus1:        contrast = 0x02;    break;
//          case Default:       contrast = 0x03;    break;
            default:            contrast = 0x03;    break;
            case Plus1:         contrast = 0x04;    break;
            case Plus2:         contrast = 0x05;    break;
            case Plus3:         contrast = 0x06;    break;
        }

        byte[] data = {0x1b, 0x5c, 0x3f, 0x4c, 0x44, contrast, 0x03};
        appendByte(data);
    }

    public void appendUserDefinedCharacter(int index, int code,byte[] font) {
        byte data[] = {0x1b, 0x5c, 0x3f, 0x4c, 0x57, 0x01, 0x3b, (byte)index, 0x3b, (byte)code, 0x3b};

        appendByte(data);

        byte fontData[] = new byte[16];

        if (font != null) {
            if (fontData.length <= font.length) {
                System.arraycopy(font, 0, fontData, 0, fontData.length);
            } else {
                System.arraycopy(font, 0, fontData, 0, font.length);
            }
        }

        appendByte(fontData);
    }

    public void appendUserDefinedDbcsCharacter(int index, int code,byte[] font) {
        byte data[] = {0x1b, 0x5c, 0x3f, 0x4c, 0x57, 0x02, 0x3b, (byte)index, 0x3b, (byte)(code / 0x0100), (byte)(code % 0x0100), 0x3b};

        appendByte(data);

        byte fontData[] = new byte[32];

        if (font != null) {
            if (fontData.length <= font.length) {
                System.arraycopy(font, 0, fontData, 0, fontData.length);
            } else {
                System.arraycopy(font, 0, fontData, 0, font.length);
            }
        }

        appendByte(fontData);
    }

}
