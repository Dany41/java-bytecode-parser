package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(12)
public class NameAndTypeConstant implements ConstantType {
    private short nameIndex;
    private short descriptorIndex;

    public NameAndTypeConstant(DataInputStream dataInputStream) throws IOException {
        this.nameIndex = dataInputStream.readShort();
        this.descriptorIndex = dataInputStream.readShort();
    }
}
