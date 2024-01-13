package org.bytecodeparser.constant;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(19)
public class ModuleConstant implements ConstantType {
    private short nameIndex;

    public ModuleConstant(DataInputStream dataInputStream) throws IOException {
        this.nameIndex = dataInputStream.readShort();
    }
}
