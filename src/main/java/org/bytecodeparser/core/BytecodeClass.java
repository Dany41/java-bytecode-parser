package org.bytecodeparser.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bytecodeparser.attribute.AttributeInfo;
import org.bytecodeparser.constant.ConstantPoolReader;
import org.bytecodeparser.constant.ConstantType;
import org.bytecodeparser.exceptions.ClassBytecodeParsingException;
import org.bytecodeparser.print.CustomPrettyPrint;
import org.bytecodeparser.print.PrettyPrintUtils;
import org.bytecodeparser.structures.FieldInfo;
import org.bytecodeparser.structures.MethodInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.accessflags.ClassAccessFlags.parseAccessFlags;
import static org.bytecodeparser.attribute.AttributeInfoReader.read;

@Getter
@Setter(AccessLevel.PROTECTED)
public class BytecodeClass {
    @CustomPrettyPrint
    private int magic;
    private short minorVersion;
    private short majorVersion;
    private short constantPoolCount;
    private ConstantType[] constantPool;
    @CustomPrettyPrint
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


    public static BytecodeClass from(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        BytecodeClass bytecodeClass = new BytecodeClass();
        try {
            bytecodeClass.setMagic(dataInputStream.readInt());
            bytecodeClass.setMinorVersion(dataInputStream.readShort());
            bytecodeClass.setMajorVersion(dataInputStream.readShort());
            bytecodeClass.setConstantPoolCount(dataInputStream.readShort());
            bytecodeClass.setConstantPool(ConstantPoolReader.read(dataInputStream, bytecodeClass.getConstantPoolCount()));
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
            bytecodeClass.setAttributeInfo(read(dataInputStream, bytecodeClass.getAttributesCount(), bytecodeClass.getConstantPool()));
            return bytecodeClass;
        } catch (IOException e) {
            throw new ClassBytecodeParsingException(e.getMessage(), e.getCause());
        }
    }

    @SuppressWarnings("unused")
    private String magicPrettyPrint() {
        return getMagicPretty();
    }

    @SuppressWarnings("unused")
    private String accessFlagsPrettyPrint() {
        return getAccessFlagsPretty();
    }

    public String getMagicPretty() {
        return Integer.toHexString(magic);
    }

    public String getAccessFlagsPretty() {
        return Integer.toString(this.accessFlags, 2) + " " + parseAccessFlags(this.accessFlags);
    }

    @Override
    public String toString() {
        return PrettyPrintUtils.prettyPrint(this);
    }

    private static short[] readShortArray(DataInputStream dataInputStream, int arraySize) throws IOException {
        short[] array = new short[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = dataInputStream.readShort();
        }
        return array;
    }

    private static FieldInfo[] readFieldInfo(DataInputStream dataInputStream, int arraySize, ConstantType[] constantPool) throws IOException {
        FieldInfo[] array = new FieldInfo[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new FieldInfo(dataInputStream, constantPool);
        }
        return array;
    }

    private static MethodInfo[] readMethodInfo(DataInputStream dataInputStream, int arraySize, ConstantType[] constantPool) throws IOException {
        MethodInfo[] array = new MethodInfo[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new MethodInfo(dataInputStream, constantPool);
        }
        return array;
    }
}
