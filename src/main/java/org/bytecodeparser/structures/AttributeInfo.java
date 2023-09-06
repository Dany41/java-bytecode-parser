package org.bytecodeparser.structures;

import org.bytecodeparser.exceptions.NoImplementationFoundForGivenAttributeName;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AttributeInfo {
    protected final short attributeNameIndex;
    protected final int attributeLength;

    protected AttributeInfo(short attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    public abstract String toPrettyString(int tabs);
}
