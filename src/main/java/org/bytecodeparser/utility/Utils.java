package org.bytecodeparser.utility;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class Utils {
    public static Integer bytesToInt(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }

    public static short[] readShortArray(DataInputStream stream, int size) throws IOException {
        short[] arr = new short[size];
        for (int i = 0; i < size; i++) {
            arr[i] = stream.readShort();
        }
        return arr;
    }


}
