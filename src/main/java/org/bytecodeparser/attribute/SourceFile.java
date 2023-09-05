package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceFile {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short sourcefileIndex;

  public SourceFile(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.sourcefileIndex = dataInputStream.readShort();
  }
}
