package org.bytecodeparser.structures;

import lombok.Value;

@Value
public class ConstantTypeAndStructure {
    ConstantType constantType;
    byte[] structure;
}
