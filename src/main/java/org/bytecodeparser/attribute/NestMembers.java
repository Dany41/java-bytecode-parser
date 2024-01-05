package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.utility.Utils.readShortArray;

public class NestMembers extends AttributeInfo {
    private final short numberOfClasses;
    private final short[] classes;

    public NestMembers(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
        super(attributeNameIndex, attributeLength);
        this.numberOfClasses = dataInputStream.readShort();
        this.classes =  readShortArray(dataInputStream, numberOfClasses);
    }
}
