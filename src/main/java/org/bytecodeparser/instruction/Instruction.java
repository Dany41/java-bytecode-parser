package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@CustomPrettyPrint
public class Instruction {

    private static final Map<Integer, InstructionTypes> instructions = Arrays.stream(InstructionTypes.values())
            .collect(Collectors.toMap(instructionTypes -> instructionTypes.opCode, Function.identity()));
    final InstructionArguments args;
    private final InstructionTypes type;

    public Instruction(Integer opCode, DataInputStream dataInputStream) {
        this.type = instructions.get(opCode);
        this.args = type.resolve(dataInputStream);
        this.args.setType(type);
    }

    @SuppressWarnings("unused")
    public String prettyPrint() {
        return type.prettyPrint() + args.prettyPrint();
    }

}
