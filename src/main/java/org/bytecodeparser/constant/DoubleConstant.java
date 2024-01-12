package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(6)
public class DoubleConstant implements ConstantType {

    private int highBytes;
    private int lowBytes;

    public DoubleConstant(DataInputStream dataInputStream) throws IOException {
        this.highBytes = dataInputStream.readInt();
        this.lowBytes = dataInputStream.readInt();
    }

}
