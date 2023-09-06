package org.bytecodeparser.core;

import org.bytecodeparser.structures.ConstantType;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.bytecodeparser.structures.FieldInfo;
import org.bytecodeparser.structures.MethodInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

// todo: rework design of BytecodeClass to encapsulate the processing logic there based on DataInputStream -> delete this
/**
 * Since ordering of parameters is important using builder isn't safe
 */
@Deprecated
public class BytecodeClassBuilder {

    private static final ConstantType[] TAG_TO_TYPE;
    static {
        int maxTag = Arrays.stream(ConstantType.values())
            .mapToInt(ConstantType::getTag)
            .max().orElse(0);
        ConstantType[] constantTypes = new ConstantType[maxTag + 1];
        Arrays.stream(ConstantType.values())
            .forEach(ct -> constantTypes[ct.getTag()] = ct);
        TAG_TO_TYPE = constantTypes;
    }

    private int magic;
    private short minorVersionBytes;
    private short majorVersionBytes;
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

    public static BytecodeClassBuilder builder() {
        return new BytecodeClassBuilder();
    }

    public BytecodeClassBuilder magic(int magic) {
        this.magic = magic;
        return this;
    }

    public BytecodeClassBuilder minorVersion(short minorVersionBytes) {
        this.minorVersionBytes = minorVersionBytes;
        return this;
    }

    public BytecodeClassBuilder majorVersion(short majorVersionBytes) {
        this.majorVersionBytes = majorVersionBytes;
        return this;
    }

    public BytecodeClassBuilder constantPoolCount(short constantPoolCount) {
        this.constantPoolCount = constantPoolCount;
        return this;
    }

    public BytecodeClassBuilder constantPool(DataInputStream dataInputStream) throws IOException {
        constantPool = new ConstantTypeAndStructure[constantPoolCount - 1];
        for (int i = 0; i < constantPoolCount - 1; i++) {
            byte tag = dataInputStream.readByte();

            ConstantType constantType = TAG_TO_TYPE[tag];
            constantPool[i] = new ConstantTypeAndStructure(constantType, constantType.read(dataInputStream));
        }
        return this;
    }

    public BytecodeClassBuilder accessFlags(short accessFlags) {
        this.accessFlags = accessFlags;
        return this;
    }

    public BytecodeClassBuilder thisClass(short thisClass) {
        this.thisClass = thisClass;
        return this;
    }

    public BytecodeClassBuilder superClass(short superClass) {
        this.superClass = superClass;
        return this;
    }

    public BytecodeClassBuilder interfaceCount(short interfaceCount) {
        this.interfaceCount = interfaceCount;
        return this;
    }

    public BytecodeClassBuilder interfaces(DataInputStream dataInputStream) throws IOException {
        this.interfaces = new short[interfaceCount];
        for (int i = 0; i < interfaceCount; i++) {
            this.interfaces[i] = dataInputStream.readShort();
        }
        return this;
    }

    public BytecodeClassBuilder fieldsCount(short fieldsCount) {
        this.fieldsCount = fieldsCount;
        return this;
    }

    public BytecodeClassBuilder fieldInfo(DataInputStream dataInputStream) throws IOException {
        this.fieldInfo = new FieldInfo[fieldsCount];
        for (int i = 0; i < fieldsCount; i++) {
            fieldInfo[i] = new FieldInfo(dataInputStream, constantPool);
        }
        return this;
    }
    public BytecodeClassBuilder methodCount(short methodCount) {
        this.methodCount = methodCount;
        return this;
    }

    public BytecodeClassBuilder methodInfo(DataInputStream dataInputStream) throws IOException {
        this.methodInfo =  new MethodInfo[fieldsCount];;
        for (int i = 0; i < methodCount; i++) {
            methodInfo[i] = new MethodInfo(dataInputStream, constantPool);
        }
        return this;
    }

    public BytecodeClass build() {
        BytecodeClass bytecodeClass = new BytecodeClass();
        bytecodeClass.setMagic(this.magic);
        bytecodeClass.setMinorVersion(this.minorVersionBytes);
        bytecodeClass.setMajorVersion(this.majorVersionBytes);
        bytecodeClass.setConstantPoolCount(this.constantPoolCount);
        bytecodeClass.setConstantPool(this.constantPool);
        bytecodeClass.setAccessFlags(this.accessFlags);
        bytecodeClass.setThisClass(this.thisClass);
        bytecodeClass.setSuperClass(this.superClass);
        bytecodeClass.setInterfaceCount(this.interfaceCount);
        bytecodeClass.setInterfaces(this.interfaces);
        bytecodeClass.setFieldsCount(this.fieldsCount);
        bytecodeClass.setFieldInfo(this.fieldInfo);
        bytecodeClass.setMethodCount(this.methodCount);
        bytecodeClass.setMethodInfo(this.methodInfo);
        return bytecodeClass;
    }
}
