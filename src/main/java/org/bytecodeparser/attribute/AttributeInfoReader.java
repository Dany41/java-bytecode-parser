package org.bytecodeparser.attribute;

import org.bytecodeparser.annotation.ConsumeConstantPool;
import org.bytecodeparser.constant.ConstantType;
import org.bytecodeparser.constant.UTF8Constant;
import org.bytecodeparser.exceptions.NoImplementationFoundForGivenAttributeName;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttributeInfoReader {

    private AttributeInfoReader() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static final Map<String, Class<?>> relatedAttributeClass = new Reflections("org.bytecodeparser.attribute")
            .getSubTypesOf(AttributeInfo.class)
            .stream()
            .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));

    public static AttributeInfo[] read(DataInputStream dataInputStream, int attributeCount, ConstantType[] constantPool)
            throws IOException {
        AttributeInfo[] attributeInfo = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributeInfo[i] = AttributeInfoReader.of(dataInputStream, constantPool);
        }
        return attributeInfo;
    }

    private static AttributeInfo of(DataInputStream dataInputStream, ConstantType[] constantPool) throws IOException {
        short attributeNameIndex = dataInputStream.readShort();
        int attributeLength = dataInputStream.readInt();
        String attributeName = ((UTF8Constant) constantPool[attributeNameIndex]).getContent();
        Class<?> anAttributeClass = relatedAttributeClass.get(attributeName);
        try {
            boolean consumeCP = anAttributeClass.isAnnotationPresent(ConsumeConstantPool.class);
            Object instance = consumeCP
                    ? anAttributeClass
                        .getConstructor(short.class, int.class, DataInputStream.class, ConstantType[].class)
                        .newInstance(attributeNameIndex, attributeLength, dataInputStream, constantPool)
                    : anAttributeClass
                        .getConstructor(short.class, int.class, DataInputStream.class)
                        .newInstance(attributeNameIndex, attributeLength, dataInputStream);
            return (AttributeInfo) instance;
        } catch (IllegalAccessException | InvocationTargetException | SecurityException | NoSuchMethodException |
                 InstantiationException | IllegalArgumentException | NullPointerException e) {
            throw new NoImplementationFoundForGivenAttributeName(attributeName, e);
        }
    }
}
