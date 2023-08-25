package org.bytecodeparser.structures;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.bytecodeparser.utility.Utils.bytesToInt;

public class AttributeInfo {
    int attributeNameIndex;
    int attributeLength;
    List<Integer> info;

    // todo: should accept constantPool to identify the attributes
    public AttributeInfo(DataInputStream dataInputStream) throws IOException {
        this.attributeNameIndex = bytesToInt(dataInputStream.readNBytes(2));
        this.attributeLength = bytesToInt(dataInputStream.readNBytes(4));
        this.info = new ArrayList<>();
        for (int i = 0; i < attributeLength; i++) {
            this.info.add(bytesToInt(dataInputStream.readNBytes(1)));
        }
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
