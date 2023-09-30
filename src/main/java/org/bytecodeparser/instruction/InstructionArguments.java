package org.bytecodeparser.instruction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructionArguments {

    private byte indexByte;
    private short indexShort;
    private byte count;
    private byte zero1;
    private byte zero2;
    private byte value;
    private int branchOffset;

}
