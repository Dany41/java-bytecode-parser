package org.bytecodeparser.utility;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, X extends Exception, R> {
    R apply(T t) throws X;

    static <T, X extends Exception, R> Function<T, R> throwableWrapper(ThrowingFunction<T, X, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
