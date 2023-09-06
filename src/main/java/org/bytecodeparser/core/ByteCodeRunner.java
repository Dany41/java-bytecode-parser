package org.bytecodeparser.core;

import org.bytecodeparser.exceptions.ClassBytecodeParsingException;
import org.bytecodeparser.structures.ConstantType;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.bytecodeparser.structures.FieldInfo;
import org.bytecodeparser.structures.MethodInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.bytecodeparser.utility.AttributeInfoUtils.readAttributes;

public class ByteCodeRunner {

    public static void run(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            BytecodeClass bytecodeClass = BytecodeClass.from(dataInputStream);

            System.out.println(bytecodeClass);
//            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
//            System.out.println(gson.toJson(bytecodeClass));
        } catch (IOException e) {
            throw new ClassBytecodeParsingException(e.getMessage(), e.getCause());
        }
    }
}
