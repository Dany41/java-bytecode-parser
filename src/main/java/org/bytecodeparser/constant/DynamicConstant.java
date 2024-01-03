package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(17)
public class DynamicConstant implements ConstantType {
    private short bootstrapMethodAttrIndex;
    private short nameAndTypeIndex;

    public DynamicConstant(DataInputStream dataInputStream) throws IOException {
        this.bootstrapMethodAttrIndex = dataInputStream.readShort();
        this.nameAndTypeIndex = dataInputStream.readShort();
    }
}
