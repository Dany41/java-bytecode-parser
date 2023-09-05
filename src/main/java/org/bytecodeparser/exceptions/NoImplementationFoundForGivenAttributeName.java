package org.bytecodeparser.exceptions;

public class NoImplementationFoundForGivenAttributeName extends RuntimeException {
    public NoImplementationFoundForGivenAttributeName(Exception e) {
        super(e);
    }
}
