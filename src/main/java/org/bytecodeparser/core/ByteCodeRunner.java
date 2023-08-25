package org.bytecodeparser.core;

import org.bytecodeparser.exceptions.ClassBytecodeParsingException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ByteCodeRunner {

    public static void run(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        try {

            BytecodeClass bytecodeClass = BytecodeClassBuilder.builder()
                    .magic(dataInputStream.readNBytes(4))
                    .minorVersion(dataInputStream.readNBytes(2))
                    .majorVersion(dataInputStream.readNBytes(2))
                    .constantPoolCount(dataInputStream.readNBytes(2))
                    .constantPool(dataInputStream)
                    .accessFlags(dataInputStream.readNBytes(2))
                    .thisClass(dataInputStream.readNBytes(2))
                    .superClass(dataInputStream.readNBytes(2))
                    .interfaceCount(dataInputStream.readNBytes(2))
                    .interfaces(dataInputStream)
                    .fieldsCount(dataInputStream.readNBytes(2))
                    .fieldInfo(dataInputStream)
                    .methodCount(dataInputStream.readNBytes(2))
                    .methodInfo(dataInputStream)
                    .build();

            System.out.println(bytecodeClass);
        } catch (IOException e) {
            throw new ClassBytecodeParsingException(e.getMessage(), e.getCause());
        }
    }
}
