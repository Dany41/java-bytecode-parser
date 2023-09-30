package org.bytecodeparser.instruction;

import java.io.DataInputStream;
import java.util.function.Function;

import static org.bytecodeparser.utility.ThrowingFunction.resolveStrategyWrapper;

public enum InstructionArgumentsResolver {
    EMPTY(dataInputStream -> InstructionArguments.EMPTY, arguments -> ""),
    SIMPLE_BYTE(resolveStrategyWrapper(
            dataInputStream -> InstructionArguments.builder()
                    .indexByte(dataInputStream.readByte())
                    .build()),
            arguments -> " " + arguments.getIndexByte()),
    SIMPLE_SHORT(resolveStrategyWrapper(
            dataInputStream -> InstructionArguments.builder()
                    .indexShort((short) ((dataInputStream.readByte() << 8) | dataInputStream.readByte()))
                    .build()),
            arguments -> " " + arguments.getIndexShort()),
    TRIPLE_SHORT_BYTE_ZERO(resolveStrategyWrapper(
            dataInputStream -> InstructionArguments.builder()
            .indexShort((short) ((dataInputStream.readByte() << 8) | dataInputStream.readByte()))
            .count(dataInputStream.readByte())
            .zero1(dataInputStream.readByte())
            .build()),
            arguments -> " " + arguments.getIndexShort() + " " + arguments.getCount() + " " + arguments.getZero1()),
    TRIPLE_SHORT_ZERO_ZERO(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder()
            .indexShort((short) ((dataInputStream.readByte() << 8) | dataInputStream.readByte()))
            .zero1(dataInputStream.readByte())
            .zero2(dataInputStream.readByte())
            .build()),
            arguments -> " " + arguments.getIndexShort() + " " + arguments.getZero1() + " " + arguments.getZero2());

    final Function<DataInputStream, InstructionArguments> argumentsResolveStrategy;
    final Function<InstructionArguments, String> prettyPrintArgumentsStrategy;


    InstructionArgumentsResolver(Function<DataInputStream, InstructionArguments> resolveStrategy, Function<InstructionArguments, String> prettyPrintArgumentsStrategy) {
        this.argumentsResolveStrategy = resolveStrategy;
        this.prettyPrintArgumentsStrategy = prettyPrintArgumentsStrategy;
    }

}
