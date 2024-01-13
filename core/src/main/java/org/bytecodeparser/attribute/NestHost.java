package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class NestHost extends AttributeInfo {

    private final short hostClassIndex;

    public NestHost(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
        super(attributeNameIndex, attributeLength);
        this.hostClassIndex = dataInputStream.readShort();
    }
}
