package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.attribute.Code.ExceptionTable.readNExceptionTable;
import static org.bytecodeparser.structures.AttributeInfo.readAttributes;

public class Code {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short maxStack;
  private final short maxLocals;
  private final int codeLength;
  private final byte code;
  private final short exceptionTableLength;
  private final ExceptionTable[] exceptionTable;
  private final short attributesCount;
  private final AttributeInfo[] attributes;

  public Code(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.maxStack = dataInputStream.readShort();
    this.maxLocals = dataInputStream.readShort();
    this.codeLength = dataInputStream.readInt();
    this.code = dataInputStream.readByte();
    this.exceptionTableLength = dataInputStream.readShort();
    this.exceptionTable = readNExceptionTable(dataInputStream, exceptionTableLength);
    this.attributesCount = dataInputStream.readShort();
    this.attributes = readAttributes(dataInputStream, attributesCount);
  }

  static class  ExceptionTable {
    private final short startPc;
    private final short endPc;
    private final short handlerPc;
    private final short catchType;

    public static ExceptionTable[] readNExceptionTable(DataInputStream dataInputStream, int size)
        throws IOException {
      ExceptionTable[] et = new ExceptionTable[size];
      for (int i = 0; i < size; i++) {
        et[i] = new ExceptionTable(dataInputStream);
      }
      return et;
    }

    public ExceptionTable(DataInputStream dataInputStream) throws IOException {
      this.startPc = dataInputStream.readShort();
      this.endPc = dataInputStream.readShort();
      this.handlerPc = dataInputStream.readShort();
      this.catchType = dataInputStream.readShort();
    }
  }
}
