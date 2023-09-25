package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@Opcode(96)
@CustomPrettyPrint
public class IAdd implements Instruction {
  public IAdd(DataInputStream dataInputStream) {
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode();
  }
}
