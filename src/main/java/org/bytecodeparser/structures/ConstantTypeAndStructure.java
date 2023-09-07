package org.bytecodeparser.structures;

import lombok.Value;
import org.bytecodeparser.print.CustomPrettyPrint;

@Value
@CustomPrettyPrint
public class ConstantTypeAndStructure {
    ConstantType constantType;
    byte[] structure;

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return constantType.getFun().apply(structure);
    }

}
