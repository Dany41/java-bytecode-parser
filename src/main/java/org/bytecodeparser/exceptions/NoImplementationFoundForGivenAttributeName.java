package org.bytecodeparser.exceptions;

public class NoImplementationFoundForGivenAttributeName extends RuntimeException {
    public NoImplementationFoundForGivenAttributeName(String attributeName, Exception e) {
        super("Couldn't find implementation for attribute with name [" + attributeName + "]", e);
    }
}
