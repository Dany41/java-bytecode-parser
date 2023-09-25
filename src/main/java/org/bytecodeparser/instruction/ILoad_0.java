package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@Opcode(26)
@CustomPrettyPrint
public class ILoad_0 implements Instruction {
  public ILoad_0(DataInputStream dataInputStream) {
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode();
  }
}
