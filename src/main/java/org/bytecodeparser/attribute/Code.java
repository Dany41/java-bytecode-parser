package org.bytecodeparser.attribute;

import org.bytecodeparser.constant.ConstantType;
import org.bytecodeparser.instruction.Instruction;
import org.bytecodeparser.instruction.InstructionReader;
import org.bytecodeparser.annotation.ConsumeConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import static org.bytecodeparser.attribute.Code.ExceptionTable.readNExceptionTable;
import static org.bytecodeparser.attribute.AttributeInfoReader.read;

@ConsumeConstantPool
public class Code extends AttributeInfo {
  private final short maxStack;
  private final short maxLocals;
  private final int codeLength;
  private final List<Instruction> code;
  private final short exceptionTableLength;
  private final ExceptionTable[] exceptionTable;
  private final short attributesCount;
  private final AttributeInfo[] attributes;

  public Code(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream, ConstantType[] constantPool) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.maxStack = dataInputStream.readShort();
    this.maxLocals = dataInputStream.readShort();
    this.codeLength = dataInputStream.readInt();
    this.code = InstructionReader.read(dataInputStream.readNBytes(codeLength));
    this.exceptionTableLength = dataInputStream.readShort();
    this.exceptionTable = readNExceptionTable(dataInputStream, exceptionTableLength);
    this.attributesCount = dataInputStream.readShort();
    this.attributes = read(dataInputStream, attributesCount, constantPool);
  }

  static class ExceptionTable {
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
