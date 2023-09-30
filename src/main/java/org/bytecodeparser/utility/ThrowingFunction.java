package org.bytecodeparser.utility;

import org.bytecodeparser.instruction.InstructionArguments;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, E extends Exception, V> {
    V apply(T t) throws E;

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
