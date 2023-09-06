package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class LineNumberTable extends AttributeInfo {
  private final short lineNumberTableLength;
  private final LineNumber[] lineNumberTable;

  public LineNumberTable(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.lineNumberTableLength = dataInputStream.readShort();
    this.lineNumberTable = LineNumber.readNClassInfo(dataInputStream, lineNumberTableLength);
  }

  @Override
  public String toPrettyString(int tabs) {
    return "LineNumberTable -> ";
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
