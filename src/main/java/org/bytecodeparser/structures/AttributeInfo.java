package org.bytecodeparser.structures;

import one.util.streamex.IntStreamEx;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.stream.Collectors;

public class AttributeInfo {
    private final short attributeNameIndex;
    private final int attributeLength;
    private final byte[] info;

    // todo: should accept constantPool to identify the attributes
    public AttributeInfo(DataInputStream dataInputStream) throws IOException {
        this.attributeNameIndex = dataInputStream.readShort();
        this.attributeLength = dataInputStream.readInt();
        this.info = new byte[attributeLength];
        for (int i = 0; i < attributeLength; i++) {
            this.info[i] = dataInputStream.readByte();
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
                IntStreamEx.of(info)
                                .boxed()
                                .map(Object::toString)
                                .collect(Collectors.joining(",", "[", "]")) + ";" +
                "\n" + "\t".repeat(tabs - 1) + '}';
    }
}
