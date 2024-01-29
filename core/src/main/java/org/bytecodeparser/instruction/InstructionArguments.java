package org.bytecodeparser.instruction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructionArguments {

    public static final InstructionArguments EMPTY = InstructionArguments.builder().build();

    private byte indexByte;
    private byte constByte;
    private short indexShort;
    private int indexInt;
    private byte count;
    private byte zero1;
    private byte zero2;
    private byte value;
    private int branchOffset;
    private byte dimensions;

}
