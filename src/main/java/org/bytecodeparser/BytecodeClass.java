package org.bytecodeparser;

import lombok.Setter;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

public class BytecodeClass {

    @Setter
    private byte[] magicBytes;
    @Setter
    private byte[] minorVersionBytes;
    @Setter
    private byte[] majorVersionBytes;
    private String magicString;
    private String minorVersionString;
    private String majorVersionString;

    public String getMagic() {
        if (this.magicString == null) {
            this.magicString = new String(Hex.encodeHex(magicBytes));
        }
        return this.magicString;
    }

    public String getMinorVersion() {
        if (this.minorVersionString == null) {
            this.minorVersionString = new BigInteger(this.minorVersionBytes).toString();
        }
        return this.minorVersionString;
    }

    public String getMajorVersion() {
        if (this.majorVersionString == null) {
            this.majorVersionString = new BigInteger(this.majorVersionBytes).toString();
        }
        return this.majorVersionString;
    }

    @Override
    public String toString() {
        // todo: make toString representation prettier
        return "BytecodeClass {\n" +
                "\tmagic = " + getMagic() + ",\n" +
                "\tminorVersion = " + getMinorVersion() + ",\n" +
                "\tmajorVersion = " + getMajorVersion() + "\n" +
                '}';
    }
}
