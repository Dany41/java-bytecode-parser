package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(15)
public class MethodHandleConstant implements ConstantType {
    private short referenceKind;
    private short referenceIndex;

    public MethodHandleConstant(DataInputStream dataInputStream) throws IOException {
        this.referenceKind = (short) Byte.toUnsignedInt(dataInputStream.readByte());
        this.referenceIndex = dataInputStream.readShort();
    }
}
