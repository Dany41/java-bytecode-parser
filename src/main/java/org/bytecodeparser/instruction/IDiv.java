package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@Opcode(108)
@CustomPrettyPrint
public class IDiv implements Instruction {
  public IDiv(DataInputStream dataInputStream) {
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode();
  }
}
