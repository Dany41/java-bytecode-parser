package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@Opcode(104)
@CustomPrettyPrint
public class IMul implements Instruction {
  public IMul(DataInputStream dataInputStream) {
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode();
  }
}
