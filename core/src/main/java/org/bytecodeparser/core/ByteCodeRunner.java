package org.bytecodeparser.core;

import static org.bytecodeparser.print.PrettyPrintUtils.prettyPrint;

public class ByteCodeRunner {

    public static void run(byte[] bytes) {
        BytecodeClass bytecodeClass = BytecodeClass.from(bytes);

        System.out.println(bytecodeClass);

        System.out.println("Using PrettyPrintUtils:\n");
        System.out.println(prettyPrint(bytecodeClass));
    }
}
