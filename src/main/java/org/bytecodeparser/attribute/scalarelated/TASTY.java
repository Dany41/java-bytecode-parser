package org.bytecodeparser.attribute.scalarelated;

import org.bytecodeparser.attribute.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class TASTY extends AttributeInfo {

    private final byte[] bytes;
    public TASTY(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
        super(attributeNameIndex, attributeLength);
        this.bytes = dataInputStream.readNBytes(attributeLength);
    }
}
