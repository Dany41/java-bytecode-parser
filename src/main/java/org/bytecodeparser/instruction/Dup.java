package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(89)
@CustomPrettyPrint
public class Dup implements Instruction {

    public Dup(DataInputStream dataInputStream) throws IOException {
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode();
    }
}
