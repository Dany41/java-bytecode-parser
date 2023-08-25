package org.bytecodeparser.accessflags;

import java.math.BigInteger;
import java.util.EnumSet;
import java.util.stream.Collectors;

public enum ClassAccessFlags {
    ACC_PUBLIC(0x0001), ACC_FINAL(0x0010), ACC_SUPER(0x0020), ACC_INTERFACE(0x0200),
    ACC_ABSTRACT(0x0400), ACC_SYNTHETIC(0x1000), ACC_ANNOTATION(0x2000), ACC_ENUM(0x4000);
    private int value;

    ClassAccessFlags(int value) {
        this.value = value;
    }

    public static String parseAccessFlags(byte[] bytes) {
        int givenValue = new BigInteger(bytes).intValue();
        return EnumSet.allOf(ClassAccessFlags.class).stream()
                .filter(af -> (af.value & givenValue) == af.value)
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "(", ")"));
    }
}
