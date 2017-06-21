package com.gg.pos.functions;

public interface IStarIoExtFunction {
    byte[] createCommands();
    boolean onReceiveCallback(byte[] data);
}
