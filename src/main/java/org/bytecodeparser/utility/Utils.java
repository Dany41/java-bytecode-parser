package org.bytecodeparser.utility;

import java.math.BigInteger;

public class Utils {
    public static Integer bytesToInt(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }
    public static String bytesToInt(byte[] bytes, int radix) {
        return new BigInteger(bytes).toString(radix);
    }
}
