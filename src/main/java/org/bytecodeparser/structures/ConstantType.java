package org.bytecodeparser.structures;

import lombok.Getter;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;

import static org.bytecodeparser.utility.Utils.bytesToInt;

public enum ConstantType {
    CONSTANT_UTF8(1, -1, byteArray -> "CONSTANT_Utf8_info { tag -> 1; value -> " + new String(byteArray, StandardCharsets.UTF_8) + " }"),
    CONSTANT_INTEGER(3, 4, byteArray -> "CONSTANT_Integer_info  { tag -> 3; value -> " + bytesToInt(byteArray) + " }"),
    CONSTANT_FLOAT(4, 4, byteArray -> ""),
    CONSTANT_LONG(5, 8, byteArray -> ""),
    CONSTANT_DOUBLE(6, 8, byteArray -> ""),
    CONSTANT_CLASS(7, 2, byteArray -> "CONSTANT_Class_info { tag -> 7; name_index -> " + bytesToInt(byteArray) + " }"),
    CONSTANT_STRING(8, 2, byteArray -> ""),
    CONSTANT_FIELD_REF(9, 4, byteArray -> "CONSTANT_Fieldref_info { tag -> 9; " +
            "class_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 0, 2)) + "; " +
            "name_and_type_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 2, 4)) + " }"),
    CONSTANT_METHOD_REF(10, 4, byteArray -> "CONSTANT_Methodref_info { tag -> 10; " +
            "class_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 0, 2)) + "; " +
            "name_and_type_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 2, 4)) + " }"),
    CONSTANT_INTERFACE_METHOD_REF(11, 4, byteArray -> ""),
    CONSTANT_NAME_AND_TYPE(12, 4, byteArray -> "CONSTANT_NameAndType_info { tag -> 12; " +
            "name_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 0, 2)) + "; " +
            "descriptor_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 2, 4)) + " }"),
    CONSTANT_METHOD_HANDLE(15, 6, byteArray -> ""),
    CONSTANT_METHOD_TYPE(16, 4, byteArray -> "CONSTANT_MethodType_info { tag -> 16; " +
            "descriptor_index -> " + bytesToInt(byteArray) + " }"),
    CONSTANT_INVOKE_DYNAMIC(18, 4, byteArray -> "");


    @Getter
    private final short tag;
    @Getter
    /**
     * data size in bytes. -1 if array
     */
    private final short dataSize;

    @Getter
    private final Function<byte[], String> fun;

    ConstantType(int tag, int dataSize, Function<byte[], String> fun) {
        this.fun = fun;
        this.tag = (short) tag;
        this.dataSize = (short) dataSize;
    }

    public byte[] read(DataInputStream stream) throws IOException {
        if (dataSize < 0) {
            short size = stream.readShort();
            return stream.readNBytes(size);
        } else {
            return stream.readNBytes(dataSize);
        }
    }

}

