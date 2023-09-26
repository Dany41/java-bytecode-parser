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

    String prettyPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        if (first != null) {
            stringBuilder.append(" " + first);
        } else if (second != null) {
            stringBuilder.append(" " + second);
        } else if (third != null) {
            stringBuilder.append(" " + third);
        } else if (fourth != null) {
            stringBuilder.append(" " + fourth);
        }
        return stringBuilder.toString();
    }
}
