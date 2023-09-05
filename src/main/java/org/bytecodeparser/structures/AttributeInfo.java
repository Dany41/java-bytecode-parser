package org.bytecodeparser.structures;

import org.bytecodeparser.exceptions.NoImplementationFoundForGivenAttributeName;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AttributeInfo {
    protected final short attributeNameIndex;
    protected final int attributeLength;

    private static final Map<String, Class<?>> relatedAttributeClass = new Reflections("org.bytecodeparser.attribute")
            .getSubTypesOf(AttributeInfo.class)
            .stream()
            .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));;

    protected AttributeInfo(short attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    // todo: delete; for now mocked to hold compatibility with other classes
    public static AttributeInfo[] readAttributes(DataInputStream dataInputStream, int attributeCount)
            throws IOException {
        AttributeInfo[] attributeInfo = new AttributeInfo[]{};
        for (int i = 0; i < attributeCount; i++) {
            short attributeNameIndex = dataInputStream.readShort();
            int attributeLength = dataInputStream.readInt();
            byte[] info = new byte[attributeLength];
            for (int j = 0; j < attributeLength; j++) {
                info[j] = dataInputStream.readByte();
            }
        }
        return attributeInfo;
    }

    public static AttributeInfo[] readAttributes(DataInputStream dataInputStream, int attributeCount, ConstantTypeAndStructure[] constantPool)
        throws IOException {
        AttributeInfo[] attributeInfo = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributeInfo[i] = AttributeInfo.of(dataInputStream, constantPool);
        }
        return attributeInfo;
    }

    private static AttributeInfo of(DataInputStream dataInputStream, ConstantTypeAndStructure[] constantPool) throws IOException {
        short attributeNameIndex = dataInputStream.readShort();
        int attributeLength = dataInputStream.readInt();
        String attributeName = new String(constantPool[attributeNameIndex].getStructure());
        Class<?> anAttributeClass = relatedAttributeClass.get(attributeName);
        try {
            Constructor<?> constructor = anAttributeClass.getConstructor(short.class, int.class, DataInputStream.class);
            Object instance = constructor.newInstance(attributeNameIndex, attributeLength, dataInputStream);
            return (AttributeInfo) instance;
        } catch (Exception e) {
            throw new NoImplementationFoundForGivenAttributeName(e);
        }
    }

    public abstract String toPrettyString(int tabs);
}
