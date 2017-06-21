package com.gg.pos.functions;

public class StarIoExtDisplayStatusFunction implements IStarIoExtFunction {
    public boolean connect = false;

    @Override
    public byte[] createCommands() {
        return new byte[]{0x1b, 0x1e, 'B', 0x41};
    }

    @Override
    public boolean onReceiveCallback(byte[] data) {

        for(int i=0; (i+5) <= data.length; i++) {
            if (5 <= data.length) {
                if (data[i  ] == 0x1b &&
                        data[i+1] == 0x1e &&
                        data[i+2] == 'B' &&
                        data[i+3] == 0x41) {

                    //noinspection RedundantIfStatement
                    if ((data[i+4] & 0x02) != 0x00) {
                        connect = true;
                    }
                    else {
                        connect = false;
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
