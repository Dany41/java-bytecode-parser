package org.bytecodeparser.structures;

import org.bytecodeparser.accessflags.MethodAccessFlags;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.utility.AttributeInfoUtils.readAttributes;

public class MethodInfo {
    private final String accessFlags;
    private final short nameIndex;
    private final short descriptorIndex;
    private final short attributeCount;
    private final AttributeInfo[] attributeInfo;

    public MethodInfo(DataInputStream dataInputStream, ConstantTypeAndStructure[] constantPool) throws IOException {
        short accessFlags = dataInputStream.readShort();
        this.accessFlags = Integer.toString(accessFlags, 2) + " " + MethodAccessFlags.parseAccessFlags(accessFlags);
        this.nameIndex = dataInputStream.readShort();
        this.descriptorIndex = dataInputStream.readShort();
        this.attributeCount = dataInputStream.readShort();
        this.attributeInfo = readAttributes(dataInputStream, attributeCount, constantPool);
    }
}
