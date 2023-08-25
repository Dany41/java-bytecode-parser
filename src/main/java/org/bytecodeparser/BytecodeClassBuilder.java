package org.bytecodeparser;

import org.bytecodeparser.exceptions.UnknownConstantTypeException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.bytecodeparser.Utils.bytesToInt;

// todo: rework design of BytecodeClass to encapsulate the processing logic there based on DataInputStream -> delete this
public class BytecodeClassBuilder {

    private byte[] magicBytes;
    private byte[] minorVersionBytes;
    private byte[] majorVersionBytes;
    private byte[] constantPoolCountBytes;
    private Map<Integer, ConstantTypeAndStructure> constantPool = new HashMap<>();
    private byte[] accessFlagsBytes;
    private byte[] thisClassBytes;
    private byte[] superClassBytes;
    private byte[] interfaceCountBytes;
    private Map<Integer, byte[]> interfaces;
    private byte[] fieldsCountBytes;
    private Map<Integer, FieldInfo> fieldInfo;
    private byte[] methodCountBytes;
    private Map<Integer, MethodInfo> methodInfo;

    public static BytecodeClassBuilder builder() {
        return new BytecodeClassBuilder();
    }

    public BytecodeClassBuilder magic(byte[] magicBytes) {
        this.magicBytes = magicBytes;
        return this;
    }

    public BytecodeClassBuilder minorVersion(byte[] minorVersionBytes) {
        this.minorVersionBytes = minorVersionBytes;
        return this;
    }

    public BytecodeClassBuilder majorVersion(byte[] majorVersionBytes) {
        this.majorVersionBytes = majorVersionBytes;
        return this;
    }

    public BytecodeClassBuilder constantPoolCount(byte[] constantPoolCountBytes) {
        this.constantPoolCountBytes = constantPoolCountBytes;
        return this;
    }

    public BytecodeClassBuilder constantPool(DataInputStream dataInputStream) throws IOException {
        for (int i = 1; i < bytesToInt(constantPoolCountBytes); i++) {
            byte constantTypeValue = dataInputStream.readNBytes(1)[0];
            switch (constantTypeValue) {
                case 1 -> {
                    int length = bytesToInt(dataInputStream.readNBytes(2));
                    this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_UTF8, dataInputStream.readNBytes(length)));
                }
                case 3 ->
                        this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_INTEGER, dataInputStream.readNBytes(4)));
                case 7 ->
                        this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_CLASS, dataInputStream.readNBytes(2)));
                case 9 ->
                        this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_FIELD_REF, dataInputStream.readNBytes(4)));
                case 10 ->
                        this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_METHOD_REF, dataInputStream.readNBytes(4)));
                case 12 ->
                        this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_NAME_AND_TYPE, dataInputStream.readNBytes(4)));
                case 16 ->
                        this.constantPool.put(i, new ConstantTypeAndStructure(ConstantType.CONSTANT_METHOD_TYPE, dataInputStream.readNBytes(2)));
                default ->
                        throw new UnknownConstantTypeException("Unknown constant type by value " + constantTypeValue);
            }
        }
        return this;
    }

    public BytecodeClassBuilder accessFlags(byte[] accessFlagsBytes) {
        this.accessFlagsBytes = accessFlagsBytes;
        return this;
    }

    public BytecodeClassBuilder thisClass(byte[] thisClassBytes) {
        this.thisClassBytes = thisClassBytes;
        return this;
    }

    public BytecodeClassBuilder superClass(byte[] superClassBytes) {
        this.superClassBytes = superClassBytes;
        return this;
    }

    public BytecodeClassBuilder interfaceCount(byte[] interfaceCountBytes) {
        this.interfaceCountBytes = interfaceCountBytes;
        return this;
    }

    public BytecodeClassBuilder interfaces(DataInputStream dataInputStream) throws IOException {
        this.interfaces = new HashMap<>();
        for (int i = 0; i < bytesToInt(interfaceCountBytes); i++) {
            this.interfaces.put(i, dataInputStream.readNBytes(2));
        }
        return this;
    }

    public BytecodeClassBuilder fieldsCount(byte[] fieldsCountBytes) {
        this.fieldsCountBytes = fieldsCountBytes;
        return this;
    }

    public BytecodeClassBuilder fieldInfo(DataInputStream dataInputStream) throws IOException {
        this.fieldInfo = new HashMap<>();
        for (int i = 0; i < bytesToInt(fieldsCountBytes); i++) {
            fieldInfo.put(i, new FieldInfo(dataInputStream));
        }
        return this;
    }
    public BytecodeClassBuilder methodCount(byte[] methodCountBytes) {
        this.methodCountBytes = methodCountBytes;
        return this;
    }

    public BytecodeClassBuilder methodInfo(DataInputStream dataInputStream) throws IOException {
        this.methodInfo = new HashMap<>();
        for (int i = 0; i < bytesToInt(methodCountBytes); i++) {
            methodInfo.put(i, new MethodInfo(dataInputStream));
        }
        return this;
    }

    public BytecodeClass build() {
        BytecodeClass bytecodeClass = new BytecodeClass();
        bytecodeClass.setMagicBytes(this.magicBytes);
        bytecodeClass.setMinorVersionBytes(this.minorVersionBytes);
        bytecodeClass.setMajorVersionBytes(this.majorVersionBytes);
        bytecodeClass.setConstantPoolCountBytes(this.constantPoolCountBytes);
        bytecodeClass.setConstantPool(this.constantPool);
        bytecodeClass.setAccessFlagsBytes(this.accessFlagsBytes);
        bytecodeClass.setThisClassBytes(this.thisClassBytes);
        bytecodeClass.setSuperClassBytes(this.superClassBytes);
        bytecodeClass.setInterfaceCountBytes(this.interfaceCountBytes);
        bytecodeClass.setInterfaces(this.interfaces);
        bytecodeClass.setFieldsCountBytes(this.fieldsCountBytes);
        bytecodeClass.setFieldInfo(this.fieldInfo);
        bytecodeClass.setMethodCountBytes(this.methodCountBytes);
        bytecodeClass.setMethodInfo(this.methodInfo);
        return bytecodeClass;
    }
}
