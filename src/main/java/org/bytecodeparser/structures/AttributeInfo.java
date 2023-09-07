package org.bytecodeparser.structures;

public abstract class AttributeInfo {
    protected final short attributeNameIndex;
    protected final int attributeLength;

    protected AttributeInfo(short attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }
}
