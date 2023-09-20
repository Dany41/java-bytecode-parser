package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(10)
public class MethodRefConstant implements ConstantType {

    private short classIndex;
    private short nameAndTypeIndex;

    public MethodRefConstant(DataInputStream dataInputStream) throws IOException {
        this.classIndex = dataInputStream.readShort();
        this.nameAndTypeIndex = dataInputStream.readShort();
    }
}
