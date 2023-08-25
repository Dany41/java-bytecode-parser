package org.bytecodeparser.core;

import lombok.Setter;
import one.util.streamex.EntryStream;
import org.apache.commons.codec.binary.Hex;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.bytecodeparser.structures.FieldInfo;
import org.bytecodeparser.structures.MethodInfo;

import java.util.Map;
import java.util.stream.Collectors;

import static org.bytecodeparser.accessflags.ClassAccessFlags.parseAccessFlags;
import static org.bytecodeparser.utility.Utils.bytesToInt;

// todo: work on annotation processing to reduce boilerplate for [field]Bytes + [field]String (s) below
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
    private Map<Integer, ConstantTypeAndStructure> constantPool;
    @Setter
    private byte[] accessFlagsBytes;
    @Setter
    private byte[] thisClassBytes;
    @Setter
    private byte[] superClassBytes;
    @Setter
    private byte[] interfaceCountBytes;
    @Setter
    private Map<Integer, byte[]> interfaces;
    @Setter
    private byte[] fieldsCountBytes;
    @Setter
    private Map<Integer, FieldInfo> fieldInfo;
    @Setter
    private byte[] methodCountBytes;
    @Setter
    private Map<Integer, MethodInfo> methodInfo;
    private String magicString;
    private String minorVersionString;
    private String majorVersionString;
    private String constantPoolCountString;
    private String constantPoolString;
    private String accessFlagsString;
    private String thisClassString;
    private String superClassString;
    private String interfaceCountString;
    private String interfacesString;
    private String fieldsCountString;
    private String fieldsInfoString;
    private String methodCountString;
    private String methodInfoString;


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

    public String getConstantPool() {
        if (this.constantPoolString == null && this.constantPool != null) {
            this.constantPoolString = getConstantPoolAsPrettyString(2);
        }
        return this.constantPoolString;
    }

    private String getConstantPoolAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.constantPool)
                .mapKeyValue((index, ctas) -> index + " -> " + ctas.getConstantType().getFun().apply(ctas.getStructure()))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getAccessFlags() {
        if (this.accessFlagsString == null && this.accessFlagsBytes != null) {
            this.accessFlagsString = bytesToInt(this.accessFlagsBytes, 2) + " " + parseAccessFlags(this.accessFlagsBytes);
        }
        return this.accessFlagsString;
    }

    public String getThisClass() {
        if (this.thisClassString == null && this.thisClassBytes != null) {
            this.thisClassString = bytesToInt(this.thisClassBytes).toString();
        }
        return this.thisClassString;
    }

    public String getSuperClass() {
        if (this.superClassString == null && this.superClassBytes != null) {
            this.superClassString = bytesToInt(this.superClassBytes).toString();
        }
        return this.superClassString;
    }

    public String getInterfaceCount() {
        if (this.interfaceCountString == null && this.interfaceCountBytes != null) {
            this.interfaceCountString = bytesToInt(this.interfaceCountBytes).toString();
        }
        return this.interfaceCountString;
    }

    public String getInterfaces() {
        if (this.interfacesString == null && this.interfaces != null) {
            this.interfacesString = getInterfacesAsPrettyString(2);
        }
        return this.interfacesString;
    }

    private String getInterfacesAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.interfaces)
                .mapKeyValue((index, i) -> index + " -> " + bytesToInt(i))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getFieldsCount() {
        if (this.fieldsCountString == null && this.fieldsCountBytes != null) {
            this.fieldsCountString = bytesToInt(fieldsCountBytes).toString();
        }
        return this.fieldsCountString;
    }

    public String getFieldInfo() {
        if (this.fieldsInfoString == null && this.fieldInfo != null) {
            this.fieldsInfoString = getFieadInfoAsPrettyString(2);
        }
        return this.fieldsInfoString;
    }

    private String getFieadInfoAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.fieldInfo)
                .mapKeyValue((index, fi) -> index + " -> " + fi.toPrettyString(tabs + 1))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getMethodCount() {
        if (this.methodCountString == null && this.methodCountBytes != null) {
            this.methodCountString = bytesToInt(this.methodCountBytes).toString();
        }
        return this.methodCountString;
    }

    public String getMethodInfo() {
        if (this.methodInfoString == null && this.methodInfo != null) {
            this.methodInfoString = getMethodInfoAsPrettyString(2);
        }
        return this.methodInfoString;
    }

    private String getMethodInfoAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.methodInfo)
                .mapKeyValue((index, mi) -> index + " -> " + mi.toPrettyString(tabs + 1))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    @Override
    public String toString() {
        // todo: make toString representation prettier
        return "BytecodeClass {\n" +
                "\tmagic = " + getMagic() + ",\n" +
                "\tminor_version = " + getMinorVersion() + ",\n" +
                "\tmajor_version = " + getMajorVersion() + "\n" +
                "\tconstant_pool_count = " + getConstantPoolCount() + "\n" +
                "\tcp_info = " + getConstantPool() + "\n" +
                "\taccess_flags = " + getAccessFlags() + "\n" +
                "\tthis_class = " + getThisClass() + "\n" +
                "\tsuper_class = " + getSuperClass() + "\n" +
                "\tinterfaces_count = " + getInterfaceCount() + "\n" +
                "\tinterfaces = " + getInterfaces() + "\n" +
                "\tfields_count = " + getFieldsCount() + "\n" +
                "\tfield_info = " + getFieldInfo() + "\n" +
                "\tmethods_count = " + getMethodCount() + "\n" +
                "\tmethod_info = " + getMethodInfo() + "\n" +
                '}';
    }
}
