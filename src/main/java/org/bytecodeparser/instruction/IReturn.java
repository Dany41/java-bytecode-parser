package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@Opcode(172)
@CustomPrettyPrint
public class IReturn implements Instruction {
  public IReturn(DataInputStream dataInputStream) {
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode();
  }
}
