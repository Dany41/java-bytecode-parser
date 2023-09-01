package org.bytecodeparser.structures;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeInfo {
    private final short attributeNameIndex;
    private final int attributeLength;
    private final List<Integer> info;

    // todo: should accept constantPool to identify the attributes
    public AttributeInfo(DataInputStream dataInputStream) throws IOException {
        this.attributeNameIndex = dataInputStream.readShort();
        this.attributeLength = dataInputStream.readInt();
        this.info = new ArrayList<>();
        for (int i = 0; i < attributeLength; i++) {
            this.info.add((int) dataInputStream.readByte());
        }
    }

    public static AttributeInfo[] readAttributes(DataInputStream dataInputStream, int attributeCount)
        throws IOException {
        AttributeInfo[] attributeInfo = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributeInfo[i] = new AttributeInfo(dataInputStream);
        }
        return attributeInfo;
    }

    public String toPrettyString(int tabs) {
        return "AttributeInfo {" +
                "\n" + "\t".repeat(tabs) + "attribute_name_index = " + attributeNameIndex + ";" +
                "\n" + "\t".repeat(tabs) + "attribute_length = " + attributeLength + ";" +
                "\n" + "\t".repeat(tabs) + "attributeInfo = " +
                        // todo: parse Info
                        info.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(",", "[", "]")) + ";" +
                "\n" + "\t".repeat(tabs - 1) + '}';
    }
}
