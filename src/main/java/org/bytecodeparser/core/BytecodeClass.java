package org.bytecodeparser.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import org.bytecodeparser.exceptions.ClassBytecodeParsingException;
import org.bytecodeparser.structures.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.bytecodeparser.accessflags.ClassAccessFlags.parseAccessFlags;
import static org.bytecodeparser.core.ByteCodeRunner.*;
import static org.bytecodeparser.utility.AttributeInfoUtils.readAttributes;
import static org.bytecodeparser.utility.Utils.readShortArray;

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

    private static final ConstantType[] TAG_TO_TYPE;
    static {
        // CONSTANT_InvokeDynamic has max 18 tag int value
        int maxTag = 18;
        ConstantType[] constantTypes = new ConstantType[maxTag + 1];
        Arrays.stream(ConstantType.values())
                .forEach(ct -> constantTypes[ct.getTag()] = ct);
        TAG_TO_TYPE = constantTypes;
    }

    public static BytecodeClass from(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        BytecodeClass bytecodeClass = new BytecodeClass();
        try {
            bytecodeClass.setMagic(dataInputStream.readInt());
            bytecodeClass.setMinorVersion(dataInputStream.readShort());
            bytecodeClass.setMajorVersion(dataInputStream.readShort());
            bytecodeClass.setConstantPoolCount(dataInputStream.readShort());
            bytecodeClass.setConstantPool(readConstantPool(dataInputStream, bytecodeClass.getConstantPoolCount()));
            bytecodeClass.setAccessFlags(dataInputStream.readShort());
            bytecodeClass.setThisClass(dataInputStream.readShort());
            bytecodeClass.setSuperClass(dataInputStream.readShort());
            bytecodeClass.setInterfaceCount(dataInputStream.readShort());
            bytecodeClass.setInterfaces(readShortArray(dataInputStream, bytecodeClass.getInterfaceCount()));
            bytecodeClass.setFieldsCount(dataInputStream.readShort());
            bytecodeClass.setFieldInfo(readFieldInfo(dataInputStream, bytecodeClass.getFieldsCount(), bytecodeClass.getConstantPool()));
            bytecodeClass.setMethodCount(dataInputStream.readShort());
            bytecodeClass.setMethodInfo(readMethodInfo(dataInputStream, bytecodeClass.getMethodCount(), bytecodeClass.getConstantPool()));
            bytecodeClass.setAttributesCount(dataInputStream.readShort());
            bytecodeClass.setAttributeInfo(readAttributes(dataInputStream, bytecodeClass.getAttributesCount(), bytecodeClass.getConstantPool()));
            return bytecodeClass;
        } catch (IOException e) {
            throw new ClassBytecodeParsingException(e.getMessage(), e.getCause());
        }
    }

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

    private static ConstantTypeAndStructure[] readConstantPool(DataInputStream dataInputStream,
                                                               int constantPoolCount) throws IOException {
        ConstantTypeAndStructure[] constantPool = new ConstantTypeAndStructure[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            byte tag = dataInputStream.readByte();

            ConstantType constantType = TAG_TO_TYPE[tag];
            constantPool[i] = new ConstantTypeAndStructure(constantType, constantType.read(dataInputStream));
        }
        return constantPool;
    }

    private static short[] readShortArray(DataInputStream dataInputStream, int arraySize) throws IOException {
        short[] array = new short[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = dataInputStream.readShort();
        }
        return array;
    }

    private static FieldInfo[] readFieldInfo(DataInputStream dataInputStream, int arraySize, ConstantTypeAndStructure[] constantPool) throws IOException {
        FieldInfo[] array = new FieldInfo[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new FieldInfo(dataInputStream, constantPool);
        }
        return array;
    }

    private static MethodInfo[] readMethodInfo(DataInputStream dataInputStream, int arraySize, ConstantTypeAndStructure[] constantPool) throws IOException {
        MethodInfo[] array = new MethodInfo[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new MethodInfo(dataInputStream, constantPool);
        }
        return array;
    }
}
