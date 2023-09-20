package org.bytecodeparser.constant;

import one.util.streamex.StreamEx;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class ConstantPoolReader {
    private static final Map<Byte, Class<? extends ConstantType>> tagToConstantType;

    static {
        Reflections reflections = new Reflections("org.bytecodeparser.constant");
        tagToConstantType = StreamEx.of(reflections.getSubTypesOf(ConstantType.class))
                .toMap(cons -> cons.getAnnotation(Tag.class).value(), c -> c);
    }

    public static ConstantType[] read(DataInputStream dataInputStream,
                                      int constantPoolCount) throws IOException {
        ConstantType[] constantPool = new ConstantType[constantPoolCount];

        for (int i = 1; i < constantPoolCount; i++) {
            byte tag = dataInputStream.readByte();
            try {
                constantPool[i] = tagToConstantType.get(tag).getDeclaredConstructor(DataInputStream.class).newInstance(dataInputStream);
            } catch (NullPointerException e) {
                throw new RuntimeException("Couldn't create instance of Constant with tag " + tag, e);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't create instance of Constant with tag " + tag +
                        " [" + tagToConstantType.get(tag).getSimpleName() + "]", e);
            }
        }
        return constantPool;
    }
}
