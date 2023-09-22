package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class Signature extends AttributeInfo {
  private final short signatureIndex;

  public Signature(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.signatureIndex = dataInputStream.readShort();
  }

}
