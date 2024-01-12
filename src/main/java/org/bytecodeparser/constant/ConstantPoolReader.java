package org.bytecodeparser.constant;

import one.util.streamex.StreamEx;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class ConstantPoolReader {
    private static final Map<Short, Class<? extends ConstantType>> tagToConstantType;

    static {
        Reflections reflections = new Reflections("org.bytecodeparser.constant");
        tagToConstantType = StreamEx.of(reflections.getSubTypesOf(ConstantType.class))
                .toMap(cons -> cons.getAnnotation(Tag.class).value(), c -> c);
    }

    public static ConstantType[] read(DataInputStream dataInputStream,
                                      int constantPoolCount) throws IOException {
        ConstantType[] constantPool = new ConstantType[constantPoolCount];

        for (int i = 1; i < constantPoolCount; i++) {
            short tag = (short) Byte.toUnsignedInt(dataInputStream.readByte());
            try {
                constantPool[i] = tagToConstantType.get(tag).getDeclaredConstructor(DataInputStream.class).newInstance(dataInputStream);
                // Double and Long constants take two places in constant pool
                if (tag == 5 || tag == 6) i++;
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
