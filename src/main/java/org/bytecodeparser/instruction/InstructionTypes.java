package org.bytecodeparser.instruction;

import lombok.Getter;

import java.io.DataInputStream;

import static org.bytecodeparser.instruction.InstructionArgumentsResolver.*;

@SuppressWarnings("unused")
public enum InstructionTypes {
    ICONST_M1(2, EMPTY),
    ICONST_0(3, EMPTY),
    ICONST_1(4, EMPTY),
    ICONST_2(5, EMPTY),
    ICONST_3(6, EMPTY),
    ICONST_4(7, EMPTY),
    ICONST_5(8, EMPTY),
    BIPUSH(16, SIMPLE_BYTE),
    LDC(18, SIMPLE_BYTE),
    ALOAD(25, SIMPLE_BYTE),
    ILOAD_0(26, EMPTY),
    ILOAD_1(27, EMPTY),
    ILOAD_2(28, EMPTY),
    ILOAD_3(29, EMPTY),
    ALOAD_0(42, EMPTY),
    ALOAD_1(43, EMPTY),
    ALOAD_2(44, EMPTY),
    ALOAD_3(45, EMPTY),
    ASTORE(58, SIMPLE_BYTE),
    ISTORE_0(59, EMPTY),
    ISTORE_1(60, EMPTY),
    ISTORE_2(61, EMPTY),
    ISTORE_3(62, EMPTY),
    ASTORE_0(75, EMPTY),
    ASTORE_1(76, EMPTY),
    ASTORE_2(77, EMPTY),
    ASTORE_3(78, EMPTY),
    AASTORE(83, EMPTY),
    POP(87, EMPTY),
    DUP(89, EMPTY),
    IADD(96, EMPTY),
    ISUB(100, EMPTY),
    IMUL(104, EMPTY),
    IDIV(108, EMPTY),
    IFEQ(153, SIMPLE_SHORT),
    IFNE(154, SIMPLE_SHORT),
    GOTO(167, SIMPLE_SHORT),
    IRETURN(172, EMPTY),
    ARETURN(176, EMPTY),
    RETURN(177, EMPTY),
    GETSTATIC(178, SIMPLE_SHORT),
    PUTSTATIC(179, SIMPLE_SHORT),
    GETFIELD(180, SIMPLE_SHORT),
    PUTFIELD(181, SIMPLE_SHORT),
    INVOKEVIRTUAL(182, SIMPLE_SHORT),
    INVOKESPECIAL(183, SIMPLE_SHORT),
    INVOKESTATIC(184, SIMPLE_SHORT),
    INVOKEINTERFACE(185, TRIPLE_SHORT_BYTE_ZERO),
    INVOKEDYNAMIC(186, TRIPLE_SHORT_ZERO_ZERO),
    NEW(187, SIMPLE_SHORT),
    ANEWARRAY(189, SIMPLE_SHORT),
    ATHROW(191, EMPTY),
    CHECKCAST(192, SIMPLE_SHORT),
    IFNULL(198, SIMPLE_SHORT);

    @Getter
    final Integer opCode;
    final InstructionArgumentsResolver instructionArgumentsResolver;

    InstructionTypes(int opCode, InstructionArgumentsResolver prettyPrintTypes) {
        this.opCode = opCode;
        this.instructionArgumentsResolver = prettyPrintTypes;
    }

    public InstructionArguments resolve(DataInputStream dataInputStream) {
        return instructionArgumentsResolver.argumentsResolveStrategy.apply(dataInputStream);
    }

}
