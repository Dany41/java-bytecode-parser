package org.bytecodeparser.utility;

@FunctionalInterface
public interface ThrowingFunction<T, E extends Exception, V> {
    V apply(T t) throws E;
}
