package org.bytecodeparser.structures;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;

import static org.bytecodeparser.utility.Utils.bytesToInt;

public enum ConstantType {
    CONSTANT_UTF8(byteArray -> "CONSTANT_Utf8_info { tag -> 1; value -> " + new String(byteArray, StandardCharsets.UTF_8) + " }"),
    CONSTANT_INTEGER(byteArray -> "CONSTANT_Integer_info  { tag -> 3; value -> " + bytesToInt(byteArray) + " }"),
    CONSTANT_FLOAT(byteArray -> ""),
    CONSTANT_LONG(byteArray -> ""),
    CONSTANT_DOUBLE(byteArray -> ""),
    CONSTANT_CLASS(byteArray -> "CONSTANT_Class_info { tag -> 7; name_index -> " + bytesToInt(byteArray) + " }"),
    CONSTANT_STRING(byteArray -> ""),
    CONSTANT_FIELD_REF(byteArray -> "CONSTANT_Fieldref_info { tag -> 9; " +
            "class_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 0, 2)) + "; " +
            "name_and_type_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 2, 4)) + " }"),
    CONSTANT_METHOD_REF(byteArray -> "CONSTANT_Methodref_info { tag -> 10; " +
            "class_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 0, 2)) + "; " +
            "name_and_type_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 2, 4)) + " }"),
    CONSTANT_INTERFACE_METHOD_REF(byteArray -> ""),
    CONSTANT_NAME_AND_TYPE(byteArray -> "CONSTANT_NameAndType_info { tag -> 12; " +
            "name_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 0, 2)) + "; " +
            "descriptor_index -> " + bytesToInt(Arrays.copyOfRange(byteArray, 2, 4)) + " }"),
    CONSTANT_METHOD_HANDLE(byteArray -> ""),
    CONSTANT_METHOD_TYPE(byteArray -> "CONSTANT_MethodType_info { tag -> 16; " +
            "descriptor_index -> " + bytesToInt(byteArray) + " }"),
    CONSTANT_INVOKE_DYNAMIC(byteArray -> "");

    @Getter
    private final Function<byte[], String> fun;

    ConstantType(Function<byte[], String> fun) {
        this.fun = fun;
    }
}
