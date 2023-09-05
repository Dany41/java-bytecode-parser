package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceDebugExtension {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final byte[] debugExtension;

  public SourceDebugExtension(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.debugExtension = dataInputStream.readNBytes(attributeLength);
  }
}
