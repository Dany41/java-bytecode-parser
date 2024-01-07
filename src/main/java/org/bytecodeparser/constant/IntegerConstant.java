package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(3)
public class IntegerConstant implements ConstantType {

    private int bytes;

    public IntegerConstant(DataInputStream dataInputStream) throws IOException {
        this.bytes = dataInputStream.readInt();
    }

}
