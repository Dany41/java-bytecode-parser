package org.bytecodeparser.utility;

import java.math.BigInteger;

public class Utils {
    public static Integer bytesToInt(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }
}
