package org.bytecodeparser.instruction;

import org.bytecodeparser.utility.ThrowingFunction;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Function;

public enum PrettyPrintTypes {
    simple(dataInputStream -> InstructionArguments.builder().build()),
    simpleByte(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder().first(dataInputStream.readByte()).build())),
    simpleShort(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder().first((dataInputStream.readByte() << 8) | dataInputStream.readByte()).build())),
    tripleShortByteByte(resolveStrategyWrapper(dataInputStream -> InstructionArguments.builder()
            .first((dataInputStream.readByte() << 8) | dataInputStream.readByte())
            .second(dataInputStream.readByte())
            .third(dataInputStream.readByte())
            .build())),

    ;

    Function<DataInputStream, InstructionArguments> resolveStrategy;


    PrettyPrintTypes(Function<DataInputStream, InstructionArguments> resolveStrategy) {
        this.resolveStrategy = resolveStrategy;
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
