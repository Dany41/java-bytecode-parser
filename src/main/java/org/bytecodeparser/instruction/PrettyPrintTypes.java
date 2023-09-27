package org.bytecodeparser.instruction;

import org.bytecodeparser.utility.ThrowingFunction;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Function;

public enum PrettyPrintTypes {
    SIMPLE(dataInputStream -> InstructionArguments.builder().build(), arguments -> ""),
    SIMPLE_BYTE(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder().indexByte(dataInputStream.readByte()).build())
            , arguments -> " " + arguments.getIndexByte()),
    SIMPLE_SHORT(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder().indexShort((short) ((dataInputStream.readByte() << 8) | dataInputStream.readByte())).build())
            , arguments -> " " + arguments.getIndexShort()),
    TRIPLE_SHORT_BYTE_BYTE(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder()
            .indexShort((short) ((dataInputStream.readByte() << 8) | dataInputStream.readByte()))
            .count(dataInputStream.readByte())
            .zero(dataInputStream.readByte())
            .build())
            , arguments -> " " + arguments.getIndexShort() + " " + arguments.getCount() + " " + arguments.getZero()),

    ;

    final Function<DataInputStream, InstructionArguments> resolveStrategy;
    final Function<InstructionArguments, String> prettyPrintArgumentsStrategy;


    PrettyPrintTypes(Function<DataInputStream, InstructionArguments> resolveStrategy, Function<InstructionArguments, String> prettyPrintArgumentsStrategy) {
        this.resolveStrategy = resolveStrategy;
        this.prettyPrintArgumentsStrategy = prettyPrintArgumentsStrategy;
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
