package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(16)
@CustomPrettyPrint
public class BiPush implements Instruction {
  private final byte value;

  public BiPush(DataInputStream dataInputStream) throws IOException {
    value = dataInputStream.readByte();
  }

  @SuppressWarnings("unused")
  private String prettyPrint() {
    return getOpcode() + " " + value;
  }
}
