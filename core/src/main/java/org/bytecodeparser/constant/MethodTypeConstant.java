package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(16)
public class MethodTypeConstant implements ConstantType {
    private short descriptorIndex;

    public MethodTypeConstant(DataInputStream dataInputStream) throws IOException {
        this.descriptorIndex = dataInputStream.readShort();
    }
}
