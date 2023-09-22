package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(18)
@CustomPrettyPrint
public class Ldc implements Instruction {
    private final byte index;

    public Ldc(DataInputStream dataInputStream) throws IOException {
        index = dataInputStream.readByte();
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode() + " " + index;
    }
}
