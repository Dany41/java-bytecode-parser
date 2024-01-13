package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantValue {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short constantValueIndex;

  public ConstantValue(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.constantValueIndex = dataInputStream.readShort();
  }
}
