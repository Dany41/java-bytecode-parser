package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(181)
@CustomPrettyPrint
public class PutField implements Instruction {
  private final short index;

  public PutField(DataInputStream dataInputStream) throws IOException {
    index = dataInputStream.readShort();
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode() + " " + index;
  }
}
