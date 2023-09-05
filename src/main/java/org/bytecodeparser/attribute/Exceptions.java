package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.utility.Utils.readShortArray;

public class Exceptions {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short numberOfExceptions;
  private final short[] exceptionIndexTable;

  public Exceptions(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.numberOfExceptions = dataInputStream.readShort();
    this.exceptionIndexTable = readShortArray(dataInputStream, numberOfExceptions);
  }
}
