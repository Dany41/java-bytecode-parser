package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(11)
public class InterfaceMethodRefConstant implements ConstantType {
    private short classIndex;
    private short nameAndTypeIndex;

    public InterfaceMethodRefConstant(DataInputStream dataInputStream) throws IOException {
        this.classIndex = dataInputStream.readShort();
        this.nameAndTypeIndex = dataInputStream.readShort();
    }
}
