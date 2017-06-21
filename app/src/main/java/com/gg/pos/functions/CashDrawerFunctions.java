package com.gg.pos.functions;


import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;

import static com.starmicronics.starioextension.ICommandBuilder.PeripheralChannel;

public class CashDrawerFunctions {
    public static byte[] createData(StarIoExt.Emulation emulation, PeripheralChannel channel) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendPeripheral(channel);

        builder.endDocument();

        return builder.getCommands();
    }
}
