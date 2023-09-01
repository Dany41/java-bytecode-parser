package org.bytecodeparser.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import org.bytecodeparser.structures.AttributeInfo;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.bytecodeparser.structures.FieldInfo;
import org.bytecodeparser.structures.MethodInfo;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.bytecodeparser.accessflags.ClassAccessFlags.parseAccessFlags;

// todo: work on annotation processing to reduce boilerplate for [field]Bytes + [field]String (s) below
@Getter
@Setter(AccessLevel.PROTECTED)
public class BytecodeClass {

    private int magic;
    private short minorVersion;
    private short majorVersion;
    private short constantPoolCount;
    private ConstantTypeAndStructure[] constantPool;
    private short accessFlags;
    private short thisClass;
    private short superClass;
    private short interfaceCount;
    private short[] interfaces;
    private short fieldsCount;
    private FieldInfo[] fieldInfo;
    private short methodCount;
    private MethodInfo[] methodInfo;
    private short attributesCount;
    private AttributeInfo[] attributeInfo;

    public String getMagicPretty() {
        return Integer.toHexString(magic);
    }

    public String getConstantPoolPretty() {
        return getConstantPoolAsPrettyString(2);
    }

    private String getConstantPoolAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.constantPool)
                .filterValues(Objects::nonNull)
                .mapKeyValue((index, ctas) -> index + " -> " + ctas.getConstantType().getFun().apply(ctas.getStructure()))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getAccessFlagsPretty() {
        return Integer.toString(this.accessFlags, 2) + " " + parseAccessFlags(this.accessFlags);
    }

    public String getInterfacesPretty() {
        return getInterfacesAsPrettyString(2);
    }

    private String getInterfacesAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + IntStreamEx.range(interfaces.length)
                .boxed()
                .map(index -> index + " -> " + interfaces[index])
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getFieldInfoPretty() {
        return getFieadInfoAsPrettyString(2);
    }

    private String getFieadInfoAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.fieldInfo)
                .mapKeyValue((index, fi) -> index + " -> " + fi.toPrettyString(tabs + 1))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getMethodInfoPretty() {
        return getMethodInfoAsPrettyString(2);
    }

    private String getMethodInfoAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.methodInfo)
                .mapKeyValue((index, mi) -> index + " -> " + mi.toPrettyString(tabs + 1))
                .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    public String getAttributeInfoPretty() {
        return getAttributeInfoAsPrettyString(2);
    }

    private String getAttributeInfoAsPrettyString(int tabs) {
        return "[\n" + "\t".repeat(tabs) + EntryStream.of(this.attributeInfo)
            .mapKeyValue((index, ai) -> index + " -> " + ai.toPrettyString(tabs + 1))
            .collect(Collectors.joining("\n" + "\t".repeat(tabs))) + "\n" + "\t".repeat(tabs - 1) + "]";
    }

    @Override
    public String toString() {
        // todo: make toString representation prettier

        return "BytecodeClass {\n" +
                "\tmagic = " + getMagicPretty() + ",\n" +
                "\tminor_version = " + getMinorVersion() + ",\n" +
                "\tmajor_version = " + getMajorVersion() + "\n" +
                "\tconstant_pool_count = " + getConstantPoolCount() + "\n" +
                "\tcp_info = " + getConstantPoolPretty() + "\n" +
                "\taccess_flags = " + getAccessFlagsPretty() + "\n" +
                "\tthis_class = " + getThisClass() + "\n" +
                "\tsuper_class = " + getSuperClass() + "\n" +
                "\tinterfaces_count = " + getInterfaceCount() + "\n" +
                "\tinterfaces = " + getInterfacesPretty() + "\n" +
                "\tfields_count = " + getFieldsCount() + "\n" +
                "\tfield_info = " + getFieldInfoPretty() + "\n" +
                "\tmethods_count = " + getMethodCount() + "\n" +
                "\tmethod_info = " + getMethodInfoPretty() + "\n" +
                "\tattributes_count = " + getAttributesCount() + "\n" +
                "\tattribute_info = " + getAttributeInfoPretty() + "\n" +
                '}';
    }
}
