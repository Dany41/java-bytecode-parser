package org.bytecodeparser.instruction;

import java.io.DataInputStream;
import java.util.List;

import static org.bytecodeparser.instruction.PrettyPrintTypes.*;

// todo: rewrite to use name of instruction like constant name
public enum InstructionTypes {
    opcode2("iconst_m1", simple),
    opcode3("iconst_0", simple),
    opcode16("bipush", simpleByte),
    opcode42("aload_0", simple),
    opcode43("aload_1", simple),
    opcode176("areturn", simple),
    opcode177("return", simple),
    opcode180("getfield", simpleShort),
    opcode181("putfield", simpleShort),
    opcode183("invokespecial", simpleShort),
    ;

    final String name;
    final PrettyPrintTypes prettyPrintTypes;


    InstructionTypes(String name, PrettyPrintTypes prettyPrintTypes) {
        this.name = name;
        this.prettyPrintTypes = prettyPrintTypes;
    }

    public String prettyPrint(InstructionArguments arguments) {
        return prettyPrintTypes.prettyPrintStrategy.apply(this, arguments);
    }

    public InstructionArguments resolve(DataInputStream dataInputStream) {
        return prettyPrintTypes.resolveStrategy.apply(dataInputStream);
    }



}
