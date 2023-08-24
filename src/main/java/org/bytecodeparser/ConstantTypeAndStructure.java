package org.bytecodeparser;

import lombok.Value;

@Value
public class ConstantTypeAndStructure {
    ConstantType constantType;
    byte[] structure;
}
