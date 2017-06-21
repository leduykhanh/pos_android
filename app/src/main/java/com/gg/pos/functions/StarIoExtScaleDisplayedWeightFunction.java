package com.gg.pos.functions;

public class StarIoExtScaleDisplayedWeightFunction implements IStarIoExtFunction {
    @SuppressWarnings("WeakerAccess")
    public enum ScaleWeightStatus {
        Invalid,
        Zero,
        NotInMotion,
        Motion
    }

    public String weight            = "";
    public ScaleWeightStatus status = ScaleWeightStatus.Invalid;

    @Override
    public byte[] createCommands() {
        return new byte[]{0x0a, 'W', 0x0d};
    }

    @Override
    public boolean onReceiveCallback(byte[] data) {
        if (20 <= data.length) {
            if (data[0] == 0x0a &&
                    data[19] == 0x0d) {

                byte[] subBuffer = new byte[19 - 6];
                int subIndex = 0;

                if (data[1] == 'Z') {
                    status = ScaleWeightStatus.Zero;
                } else if (data[4] == 'M') {
                    status = ScaleWeightStatus.Motion;
                } else {
                    status = ScaleWeightStatus.NotInMotion;
                }

                for (int i = 6; i < 19; i++) {
                    if (0x21 <= data[i] && data[i] <= 0x7f) {
                        subBuffer[subIndex++] = data[i];
                    }
                }
                weight = new String(subBuffer, 0, subIndex);

                return true;
            }
        }


        return false;
    }
}
