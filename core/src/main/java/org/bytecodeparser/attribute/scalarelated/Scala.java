package org.bytecodeparser.attribute.scalarelated;

import org.bytecodeparser.attribute.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class Scala extends AttributeInfo {

    private final byte[] bytes;
    public Scala(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
        super(attributeNameIndex, attributeLength);
        this.bytes = dataInputStream.readNBytes(attributeLength);
    }
}
