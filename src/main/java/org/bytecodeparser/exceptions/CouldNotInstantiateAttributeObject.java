package org.bytecodeparser.exceptions;

public class CouldNotInstantiateAttributeObject extends RuntimeException {
    public CouldNotInstantiateAttributeObject(String attributeName, Exception e) {
        super("Couldn't instantiate attribute object [" + attributeName + "]", e);
    }
}
