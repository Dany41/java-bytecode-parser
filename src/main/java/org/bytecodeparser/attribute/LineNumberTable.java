package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class LineNumberTable {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short lineNumberTableLength;
  private final LineNumber[] lineNumberTable;

  public LineNumberTable(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.lineNumberTableLength = dataInputStream.readShort();
    this.lineNumberTable = LineNumber.readNClassInfo(dataInputStream, lineNumberTableLength);
  }

  static class LineNumber {
    private final short startPc;
    private final short lineNumber;

    public static LineNumber[] readNClassInfo(DataInputStream dataInputStream, int size)
        throws IOException {
      LineNumber[] lineNumbers = new LineNumber[size];
      for (int i = 0; i < size; i++) {
        lineNumbers[i] = new LineNumber(dataInputStream);
      }
      return lineNumbers;
    }

    public LineNumber(DataInputStream dataInputStream) throws IOException {
      this.startPc = dataInputStream.readShort();
      this.lineNumber = dataInputStream.readShort();
    }
  }
}
