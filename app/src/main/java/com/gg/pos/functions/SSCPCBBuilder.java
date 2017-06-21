package com.gg.pos.functions;

//ScaleSerialCommunicationProtocolCommandBuilder

class SSCPCBBuilder {
    private byte[] command = new byte[0];

    static StarIoExtScaleDisplayedWeightFunction createRequestDisplayedWeightFunction() {
        return new StarIoExtScaleDisplayedWeightFunction();
    }

    static SSCPCBBuilder createCommandBuilder(){
        return new SSCPCBBuilder();
    }

    void appendZeroClear() {
        byte[] data = {0x0a, 'Z', 0x0d};
        appendCommand(data);
    }

    void appendUnitChange() {
        byte[] data = {0x0a, 'U', 0x0d};
        appendCommand(data);
    }

    private void appendCommand(byte[] appendCommand) {
        byte[] tempCommand = new byte[command.length + appendCommand.length];

        System.arraycopy(      command, 0, tempCommand, 0,                    command.length);
        System.arraycopy(appendCommand, 0, tempCommand, command.length, appendCommand.length);

        command = tempCommand;
    }

    byte[] getCommand() {
        return command;
    }
}

