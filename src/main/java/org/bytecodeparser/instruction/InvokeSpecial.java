package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(183)
@CustomPrettyPrint
public class InvokeSpecial implements Instruction {
  private final short index;

  public InvokeSpecial(DataInputStream dataInputStream) throws IOException {
    index = dataInputStream.readShort();
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode() + " " + index;
  }
}
