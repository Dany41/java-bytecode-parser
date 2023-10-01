package org.bytecodeparser.utility;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, E extends Exception, V> {
    V apply(T t) throws E;

    static <T, E extends Exception, V> Function<T, V> throwableWrapper(ThrowingFunction<T, E, V> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
