package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(18)
public class InvokeDynamicConstant implements ConstantType {
    private short bootstrapMethodAttrIndex;
    private short nameAndTypeIndex;

    public InvokeDynamicConstant(DataInputStream dataInputStream) throws IOException {
        this.bootstrapMethodAttrIndex = dataInputStream.readShort();
        this.nameAndTypeIndex = dataInputStream.readShort();
    }
}
