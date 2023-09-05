package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class Signature {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short signatureIndex;

  public Signature(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.signatureIndex = dataInputStream.readShort();
  }

}
