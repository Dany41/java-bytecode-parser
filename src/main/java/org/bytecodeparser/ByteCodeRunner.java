package org.bytecodeparser;

import org.bytecodeparser.exceptions.ClassBytecodeParsingException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ByteCodeRunner {

    static void run(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        try {

            BytecodeClass bytecodeClass = BytecodeClassBuilder.builder()
                    .magic(dataInputStream.readNBytes(4))
                    .minorVersion(dataInputStream.readNBytes(2))
                    .majorVersion(dataInputStream.readNBytes(2))
                    .build();

            System.out.println(bytecodeClass);
        } catch (IOException e) {
            throw new ClassBytecodeParsingException(e.getMessage(), e.getCause());
        }
    }
}
