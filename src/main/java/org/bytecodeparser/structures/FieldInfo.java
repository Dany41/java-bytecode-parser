package org.bytecodeparser.structures;

import org.bytecodeparser.accessflags.FieldAccessFlags;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.bytecodeparser.utility.Utils.bytesToInt;

public class FieldInfo {
    String accessFlags;
    int nameIndex;
    int descriptorIndex;
    int attributeCount;
    List<AttributeInfo> attributeInfo;

    public FieldInfo(DataInputStream dataInputStream) throws IOException {
        byte[] accessFlagsBytes = dataInputStream.readNBytes(2);
        this.accessFlags = bytesToInt(accessFlagsBytes, 2) + " " + FieldAccessFlags.parseAccessFlags(accessFlagsBytes);
        this.nameIndex = bytesToInt(dataInputStream.readNBytes(2));
        this.descriptorIndex = bytesToInt(dataInputStream.readNBytes(2));
        this.attributeCount = bytesToInt(dataInputStream.readNBytes(2));
        this.attributeInfo = new ArrayList<>();
        for (int i = 0; i < attributeCount; i++) {
            attributeInfo.add(new AttributeInfo(dataInputStream));
        }
    }

    public String toPrettyString(int tabs) {
        return "FieldInfo {" +
                "\n" + "\t".repeat(tabs) + "access_lags = " + accessFlags + ";" +
                "\n" + "\t".repeat(tabs) + "nameIndex = " + nameIndex + ";" +
                "\n" + "\t".repeat(tabs) + "descriptorIndex = " + descriptorIndex + ";" +
                "\n" + "\t".repeat(tabs) + "attributeCount = " + attributeCount + ";" +
                "\n" + "\t".repeat(tabs) + "attributeInfo = " + attributeInfo.stream()
                        .map(ai -> ai.toPrettyString(tabs + 2))
                        .collect(Collectors.joining(",", "[\n" + "\t".repeat(tabs + 1), "]")) + ";" +
                "\n" + "\t".repeat(tabs - 1) + '}';
    }
}
