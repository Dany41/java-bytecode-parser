package org.bytecodeparser.structures;

import org.bytecodeparser.accessflags.FieldAccessFlags;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.bytecodeparser.structures.AttributeInfo.readAttributes;

public class FieldInfo {
    private final String accessFlags;
    private final short nameIndex;
    private final short descriptorIndex;
    private final short attributeCount;
    private final AttributeInfo[] attributeInfo;

    public FieldInfo(DataInputStream dataInputStream) throws IOException {
        short accessFlags = dataInputStream.readShort();
        this.accessFlags = Integer.toString(accessFlags, 2) + " " + FieldAccessFlags.parseAccessFlags(accessFlags);
        this.nameIndex = dataInputStream.readShort();
        this.descriptorIndex = dataInputStream.readShort();
        this.attributeCount = dataInputStream.readShort();
        this.attributeInfo = readAttributes(dataInputStream, attributeCount);
    }

    public String toPrettyString(int tabs) {
        return "FieldInfo {" +
                "\n" + "\t".repeat(tabs) + "access_flags = " + accessFlags + ";" +
                "\n" + "\t".repeat(tabs) + "nameIndex = " + nameIndex + ";" +
                "\n" + "\t".repeat(tabs) + "descriptorIndex = " + descriptorIndex + ";" +
                "\n" + "\t".repeat(tabs) + "attributeCount = " + attributeCount + ";" +
                "\n" + "\t".repeat(tabs) + "attributeInfo = " + Arrays.stream(attributeInfo)
                        .map(ai -> ai.toPrettyString(tabs + 2))
                        .collect(Collectors.joining(",", "[\n" + "\t".repeat(tabs + 1), "]")) + ";" +
                "\n" + "\t".repeat(tabs - 1) + '}';
    }
}
