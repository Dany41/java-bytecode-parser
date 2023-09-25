package org.bytecodeparser.instruction;

import org.bytecodeparser.utility.ThrowingFunction;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum PrettyPrintTypes {
    simple(dataInputStream -> InstructionArguments.builder().build(), (instruction, arguments) -> instruction.name),
    simpleByte(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder().first(dataInputStream.readByte()).build())
            , (instruction, arguments) -> instruction.name + " " + (byte) arguments.getFirst()),
    simpleShort(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder().first(dataInputStream.readShort()).build())
            , (instruction, arguments) -> instruction.name + " " + (short) arguments.getFirst()),

    ;

    Function<DataInputStream, InstructionArguments> resolveStrategy;
    BiFunction<InstructionTypes, InstructionArguments, String> prettyPrintStrategy;

    PrettyPrintTypes(Function<DataInputStream, InstructionArguments> resolveStrategy, BiFunction<InstructionTypes, InstructionArguments, String> prettyPrintStrategy) {
        this.resolveStrategy = resolveStrategy;
        this.prettyPrintStrategy = prettyPrintStrategy;
    }

    static Function<DataInputStream, InstructionArguments> resolveStrategyWrapper(ThrowingFunction<DataInputStream, IOException, InstructionArguments> function) {
        return dataInputStream -> {
            try {
                return function.apply(dataInputStream);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
