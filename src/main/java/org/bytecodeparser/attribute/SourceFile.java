package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceFile extends AttributeInfo {
  private final short sourcefileIndex;

  public SourceFile(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.sourcefileIndex = dataInputStream.readShort();
  }
}
