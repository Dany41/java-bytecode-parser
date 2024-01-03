package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(20)
public class PackageConstant implements ConstantType {
    private short nameIndex;

    public PackageConstant(DataInputStream dataInputStream) throws IOException {
        this.nameIndex = dataInputStream.readShort();
    }
}
