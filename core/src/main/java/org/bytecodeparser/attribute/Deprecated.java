package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class Deprecated {
  private final short attributeNameIndex;
  private final int attributeLength;

  public Deprecated(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
  }

}
