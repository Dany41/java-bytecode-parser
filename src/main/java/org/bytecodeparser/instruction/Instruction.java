package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

@CustomPrettyPrint
public interface Instruction {

  default String getOpcode() {
    return this.getClass().getSimpleName().toLowerCase();
  }

}
