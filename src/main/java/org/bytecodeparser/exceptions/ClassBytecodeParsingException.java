package org.bytecodeparser.exceptions;

public class ClassBytecodeParsingException extends RuntimeException {
    public ClassBytecodeParsingException(String message) {
        super(message);
    }

    public ClassBytecodeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
