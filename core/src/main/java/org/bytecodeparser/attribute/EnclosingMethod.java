package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class EnclosingMethod {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short classIndex;
  private final short methodIndex;

  public EnclosingMethod(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.classIndex = dataInputStream.readShort();
    this.methodIndex = dataInputStream.readShort();
  }
}
