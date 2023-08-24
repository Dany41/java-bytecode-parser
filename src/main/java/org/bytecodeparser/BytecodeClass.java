package org.bytecodeparser;

import lombok.Getter;
import lombok.Setter;
import one.util.streamex.EntryStream;
import org.apache.commons.codec.binary.Hex;

import java.util.Map;
import java.util.stream.Collectors;

import static org.bytecodeparser.Utils.bytesToInt;

public class BytecodeClass {

    @Setter
    private byte[] magicBytes;
    @Setter
    private byte[] minorVersionBytes;
    @Setter
    private byte[] majorVersionBytes;
    @Setter
    private byte[] constantPoolCountBytes;
    @Setter
    @Getter
    private Map<Integer, ConstantTypeAndStructure> constantPool;
    private String magicString;
    private String minorVersionString;
    private String majorVersionString;
    private String constantPoolCountString;

    public String getMagic() {
        if (this.magicString == null && this.magicBytes != null) {
            this.magicString = new String(Hex.encodeHex(magicBytes));
        }
        return this.magicString;
    }

    public String getMinorVersion() {
        if (this.minorVersionString == null && this.minorVersionBytes != null) {
            this.minorVersionString = bytesToInt(this.minorVersionBytes).toString();
        }
        return this.minorVersionString;
    }

    public String getMajorVersion() {
        if (this.majorVersionString == null && this.majorVersionBytes != null) {
            this.majorVersionString = bytesToInt(this.majorVersionBytes).toString();
        }
        return this.majorVersionString;
    }

    public String getConstantPoolCount() {
        if (this.constantPoolCountString == null && this.constantPoolCountBytes != null) {
            this.constantPoolCountString = bytesToInt(this.constantPoolCountBytes).toString();
        }
        return this.constantPoolCountString;
    }

    @Override
    public String toString() {
        // todo: make toString representation prettier
        return "BytecodeClass {\n" +
                "\tmagic = " + getMagic() + ",\n" +
                "\tminor_version = " + getMinorVersion() + ",\n" +
                "\tmajor_version = " + getMajorVersion() + "\n" +
                "\tconstant_pool_count = " + getConstantPoolCount() + "\n" +
                "\tcp_info = " + getConstantPoolAsPrettyString(2) + "\n" +
                '}';
    }

    private String getConstantPoolAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.constantPool)
                .mapKeyValue((index, ctas) -> index + " -> " + ctas.getConstantType().getFun().apply(ctas.getStructure()))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }
}
