package org.bytecodeparser.instruction;

import java.io.DataInputStream;

import static org.bytecodeparser.instruction.PrettyPrintTypes.*;

public enum InstructionTypes {
    ICONST_M1(2, SIMPLE),
    ICONST_0(3, SIMPLE),
    ICONST_1(4, SIMPLE),
    ICONST_2(5, SIMPLE),
    ICONST_3(6, SIMPLE),
    ICONST_4(7, SIMPLE),
    ICONST_5(8, SIMPLE),
    BIPUSH(16, SIMPLE_BYTE),
    LDC(18, SIMPLE_BYTE),
    ILOAD_0(26, SIMPLE),
    ALOAD_0(42, SIMPLE),
    ALOAD_1(43, SIMPLE),
    ALOAD_2(44, SIMPLE),
    ALOAD_3(45, SIMPLE),
    POP(87, SIMPLE),
    DUP(89, SIMPLE),
    IADD(96, SIMPLE),
    ISUB(100, SIMPLE),
    IMUL(104, SIMPLE),
    IDIV(108, SIMPLE),
    IRETURN(172, SIMPLE),
    ARETURN(176, SIMPLE),
    RETURN(177, SIMPLE),
    GETFIELD(180, SIMPLE_SHORT),
    PUTFIELD(181, SIMPLE_SHORT),
    INVOKESPECIAL(183, SIMPLE_SHORT),
    INVOKESTATIC(184, SIMPLE_SHORT),
    INVOKEINTERFACE(185, TRIPLE_SHORT_BYTE_BYTE),
    INVOKEDYNAMIC(186, TRIPLE_SHORT_BYTE_BYTE),
    NEW(187, SIMPLE_SHORT),
    CHECKCAST(192, SIMPLE_SHORT),
    ;

    final Integer opCode;
    final PrettyPrintTypes prettyPrintTypes;


    InstructionTypes(int opCode, PrettyPrintTypes prettyPrintTypes) {

        this.opCode = opCode;
        this.prettyPrintTypes = prettyPrintTypes;
    }

    public String prettyPrint() {
        return name();
    }

    public InstructionArguments resolve(DataInputStream dataInputStream) {
        return prettyPrintTypes.resolveStrategy.apply(dataInputStream);
    }



}
