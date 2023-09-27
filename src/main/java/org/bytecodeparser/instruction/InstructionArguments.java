package org.bytecodeparser.instruction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructionArguments {

    private InstructionTypes type;

    private byte indexByte;
    private short indexShort;
    private byte count;
    private byte zero;
    private byte value;
    private int branchOffset;

    String prettyPrint() {
        return type.prettyPrintTypes.prettyPrintArgumentsStrategy.apply(this);
    }
}
