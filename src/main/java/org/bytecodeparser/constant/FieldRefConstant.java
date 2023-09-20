package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(9)
public class FieldRefConstant implements ConstantType {

    private short classIndex;
    private short nameAndTypeIndex;

    public FieldRefConstant(DataInputStream dataInputStream) throws IOException {
        this.classIndex = dataInputStream.readShort();
        this.nameAndTypeIndex = dataInputStream.readShort();
    }

}
