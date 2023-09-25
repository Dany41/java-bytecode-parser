package org.bytecodeparser.instruction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructionArguments {

    private Object first;
    private Object second;
    private Object third;
    private Object fourth;
}
