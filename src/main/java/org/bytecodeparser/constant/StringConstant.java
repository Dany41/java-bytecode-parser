package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(8)
public class StringConstant implements ConstantType {

    private short stringIndex;

    public StringConstant(DataInputStream dataInputStream) throws IOException {
        this.stringIndex = dataInputStream.readShort();
    }

}
