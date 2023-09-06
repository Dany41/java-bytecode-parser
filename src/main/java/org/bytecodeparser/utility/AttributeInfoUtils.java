package org.bytecodeparser.utility;

import lombok.val;
import org.bytecodeparser.annotation.ConsumeConstantPool;
import org.bytecodeparser.exceptions.NoImplementationFoundForGivenAttributeName;
import org.bytecodeparser.structures.AttributeInfo;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttributeInfoUtils {

    private AttributeInfoUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static final Map<String, Class<?>> relatedAttributeClass = new Reflections("org.bytecodeparser.attribute")
            .getSubTypesOf(AttributeInfo.class)
            .stream()
            .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));

    public static AttributeInfo[] readAttributes(DataInputStream dataInputStream, int attributeCount, ConstantTypeAndStructure[] constantPool)
            throws IOException {
        AttributeInfo[] attributeInfo = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributeInfo[i] = AttributeInfoUtils.of(dataInputStream, constantPool);
        }
        return attributeInfo;
    }

    private static AttributeInfo of(DataInputStream dataInputStream, ConstantTypeAndStructure[] constantPool) throws IOException {
        short attributeNameIndex = dataInputStream.readShort();
        int attributeLength = dataInputStream.readInt();
        String attributeName = new String(constantPool[attributeNameIndex].getStructure());
        Class<?> anAttributeClass = relatedAttributeClass.get(attributeName);
        try {
            boolean consumeCP = anAttributeClass.isAnnotationPresent(ConsumeConstantPool.class);
            Object instance = consumeCP
                    ? anAttributeClass
                        .getConstructor(short.class, int.class, DataInputStream.class, ConstantTypeAndStructure[].class)
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
