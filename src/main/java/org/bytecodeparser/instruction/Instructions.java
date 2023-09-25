package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@CustomPrettyPrint
public class Instructions {

    public static final String OPCODE = "opcode";
    final InstructionArguments args;
    private final InstructionTypes type;

    public Instructions(int opCode, DataInputStream dataInputStream) {
        this.type = InstructionTypes.valueOf(OPCODE + opCode);
        this.args = type.resolve(dataInputStream);
    }

    public String prettyPrint() {
        return type.prettyPrint(args);
    }

}
