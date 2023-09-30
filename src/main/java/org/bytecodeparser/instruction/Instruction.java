package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@CustomPrettyPrint
public class Instruction {

    private static final Map<Integer, InstructionTypes> instructions = EnumSet.allOf(InstructionTypes.class).stream()
            .collect(Collectors.toMap(InstructionTypes::getOpCode, Function.identity()));

    final InstructionArguments args;
    private final InstructionTypes type;

    public Instruction(Integer opCode, DataInputStream dataInputStream) {
        this.type = instructions.get(opCode);
        this.args = type.resolve(dataInputStream);
    }

    @SuppressWarnings("unused")
    public String prettyPrint() {
        return type.name() + type.instructionArgumentResolver.prettyPrintArgumentsStrategy.apply(args);
    }

}
