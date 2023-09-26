package org.bytecodeparser.instruction;

import java.io.DataInputStream;
import java.util.List;

import static org.bytecodeparser.instruction.PrettyPrintTypes.*;

public enum InstructionTypes {
    ICONST_M1("ICONST_M1", 2, simple),
    ICONST_0("ICONST_0", 3, simple),
    ICONST_1("ICONST_1", 4, simple),
    ICONST_2("ICONST_2", 5, simple),
    ICONST_3("ICONST_3", 6, simple),
    ICONST_4("ICONST_4", 7, simple),
    ICONST_5("ICONST_5", 8, simple),
    BIPUSH("BIPUSH", 16, simpleByte),
    LDC("LDC", 18, simpleByte),
    ILOAD_0("ILOAD_0", 26, simple),
    ALOAD_0("ALOAD_0", 42, simple),
    ALOAD_1("ALOAD_1", 43, simple),
    ALOAD_2("ALOAD_2", 44, simple),
    ALOAD_3("ALOAD_3", 45, simple),
    POP("POP", 87, simple),
    DUP("DUP", 89, simple),
    IADD("IADD", 96, simple),
    ISUB("ISUB", 100, simple),
    IMUL("IMUL", 104, simple),
    IDIV("IDIV", 108, simple),
    IRETURN("IRETURN", 172, simple),
    ARETURN("ARETURN", 176, simple),
    RETURN("RETURN", 177, simple),
    GETFIELD("GETFIELD", 180, simpleShort),
    PUTFIELD("PUTFIELD", 181, simpleShort),
    INVOKESPECIAL("INVOKESPECIAL", 183, simpleShort),
    INVOKESTATIC("INVOKESTATIC", 184, simpleShort),
    INVOKEINTERFACE("INVOKEINTERFACE", 185, tripleShortByteByte),
    INVOKEDYNAMIC("INVOKEDYNAMIC", 186, tripleShortByteByte),
    NEW("NEW", 187, simpleShort),
    CHECKCAST("CHECKCAST", 192, simpleShort),
    ;

    final String name;
    final Integer opCode;
    final PrettyPrintTypes prettyPrintTypes;


    InstructionTypes(String name, int opCode, PrettyPrintTypes prettyPrintTypes) {
        this.name = name;
        this.opCode = opCode;
        this.prettyPrintTypes = prettyPrintTypes;
    }

    public String prettyPrint() {
        return name;
    }

    public InstructionArguments resolve(DataInputStream dataInputStream) {
        return prettyPrintTypes.resolveStrategy.apply(dataInputStream);
    }



}
