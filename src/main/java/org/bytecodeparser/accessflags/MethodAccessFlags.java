package org.bytecodeparser.accessflags;

import java.math.BigInteger;
import java.util.EnumSet;
import java.util.stream.Collectors;

public enum MethodAccessFlags {
    ACC_PUBLIC(0x0001), ACC_PRIVATE(0x0002), ACC_PROTECTED(0x0004), ACC_STATIC(0x0008),
    ACC_FINAL(0x0010), ACC_SYNCHRONIZED(0x0020), ACC_BRIDGE(0x0040), ACC_VARARGS(0x0080),
    ACC_NATIVE(0x0100), ACC_ABSTRACT(0x0400), ACC_STRICT(0x0800), ACC_SYNTHETIC(0x1000);
    private int value;

    MethodAccessFlags(int value) {
        this.value = value;
    }

    public static String parseAccessFlags(byte[] bytes) {
        int givenValue = new BigInteger(bytes).intValue();
        return EnumSet.allOf(MethodAccessFlags.class).stream()
                .filter(af -> (af.value & givenValue) == af.value)
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "(", ")"));
    }
}
