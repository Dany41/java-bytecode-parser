package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(180)
@CustomPrettyPrint
public class GetField implements Instruction {
  private final short index;

  public GetField(DataInputStream dataInputStream) throws IOException {
    index = dataInputStream.readShort();
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode() + " " + index;
  }
}
