package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.util.Arrays;

@CustomPrettyPrint
public class Instruction {
    final InstructionArguments args;
    private final InstructionTypes type;

    public Instruction(Integer opCode, DataInputStream dataInputStream) {
        this.type = Arrays.stream(InstructionTypes.values())
                .filter(instructionTypes -> opCode.equals(instructionTypes.opCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to read instruction with opcode = " + opCode));
        this.args = type.resolve(dataInputStream);
    }

    public String prettyPrint() {
        return type.prettyPrint() + args.prettyPrint();
    }

}
