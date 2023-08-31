package org.bytecodeparser.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bytecodeparser.exceptions.ClassBytecodeParsingException;
import org.bytecodeparser.structures.ConstantType;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.bytecodeparser.structures.FieldInfo;
import org.bytecodeparser.structures.MethodInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.bytecodeparser.structures.AttributeInfo.readAttributes;

public class ByteCodeRunner {

    private static final ConstantType[] TAG_TO_TYPE;
    static {
        // CONSTANT_InvokeDynamic has max 18 tag int value
        int maxTag = 18;
        ConstantType[] constantTypes = new ConstantType[maxTag + 1];
        Arrays.stream(ConstantType.values())
            .forEach(ct -> constantTypes[ct.getTag()] = ct);
        TAG_TO_TYPE = constantTypes;
    }

    public static void run(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            BytecodeClass bytecodeClass = new BytecodeClass();
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
            bytecodeClass.setFieldInfo(readFieldInfo(dataInputStream, bytecodeClass.getFieldsCount()));
            bytecodeClass.setMethodCount(dataInputStream.readShort());
            bytecodeClass.setMethodInfo(readMethodInfo(dataInputStream, bytecodeClass.getMethodCount()));
            bytecodeClass.setAttributesCount(dataInputStream.readShort());
            bytecodeClass.setAttributeInfo(readAttributes(dataInputStream, bytecodeClass.getAttributesCount()));

            System.out.println(bytecodeClass);
//            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
//            System.out.println(gson.toJson(bytecodeClass));
        } catch (IOException e) {
            throw new ClassBytecodeParsingException(e.getMessage(), e.getCause());
        }
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

    private static FieldInfo[] readFieldInfo(DataInputStream dataInputStream, int arraySize) throws IOException {
        FieldInfo[] array = new FieldInfo[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new FieldInfo(dataInputStream);
        }
        return array;
    }

    private static MethodInfo[] readMethodInfo(DataInputStream dataInputStream, int arraySize) throws IOException {
        MethodInfo[] array = new MethodInfo[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new MethodInfo(dataInputStream);
        }
        return array;
    }
}
