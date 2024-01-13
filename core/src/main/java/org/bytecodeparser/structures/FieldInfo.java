package org.bytecodeparser.structures;

import org.bytecodeparser.accessflags.FieldAccessFlags;
import org.bytecodeparser.attribute.AttributeInfo;
import org.bytecodeparser.constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.attribute.AttributeInfoReader.read;

public class FieldInfo {
    private final String accessFlags;
    private final short nameIndex;
    private final short descriptorIndex;
    private final short attributeCount;
    private final AttributeInfo[] attributeInfo;

    public FieldInfo(DataInputStream dataInputStream, ConstantType[] constantPool) throws IOException {
        short accessFlags = dataInputStream.readShort();
        this.accessFlags = Integer.toString(accessFlags, 2) + " " + FieldAccessFlags.parseAccessFlags(accessFlags);
        this.nameIndex = dataInputStream.readShort();
        this.descriptorIndex = dataInputStream.readShort();
        this.attributeCount = dataInputStream.readShort();
        this.attributeInfo = read(dataInputStream, attributeCount, constantPool);
    }
}
