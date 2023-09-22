package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(87)
@CustomPrettyPrint
public class Pop implements Instruction {

    public Pop(DataInputStream dataInputStream) throws IOException {
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode();
    }
}
