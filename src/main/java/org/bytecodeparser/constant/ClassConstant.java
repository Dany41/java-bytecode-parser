package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(7)
public class ClassConstant implements ConstantType {
    private short nameIndex;

    public ClassConstant(DataInputStream dataInputStream) throws IOException {
        this.nameIndex = dataInputStream.readShort();
    }
}
