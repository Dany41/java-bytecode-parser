package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;
import org.bytecodeparser.structures.ConstantTypeAndStructure;
import org.bytecodeparser.annotation.ConsumeConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.bytecodeparser.attribute.Code.ExceptionTable.readNExceptionTable;
import static org.bytecodeparser.utility.AttributeInfoUtils.readAttributes;

@ConsumeConstantPool
public class Code extends AttributeInfo {
  private final short maxStack;
  private final short maxLocals;
  private final int codeLength;
  private final byte[] code;
  private final short exceptionTableLength;
  private final ExceptionTable[] exceptionTable;
  private final short attributesCount;
  private final AttributeInfo[] attributes;

  public Code(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream, ConstantTypeAndStructure[] constantPool) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.maxStack = dataInputStream.readShort();
    this.maxLocals = dataInputStream.readShort();
    this.codeLength = dataInputStream.readInt();
    this.code = new byte[this.codeLength];
    for (int i = 0; i < codeLength; i++) {
      code[i] = dataInputStream.readByte();
    }
    this.exceptionTableLength = dataInputStream.readShort();
    this.exceptionTable = readNExceptionTable(dataInputStream, exceptionTableLength);
    this.attributesCount = dataInputStream.readShort();
    this.attributes = readAttributes(dataInputStream, attributesCount, constantPool);
  }

  @Override
  public String toPrettyString(int tabs) {
    return "Code {" +
            "\n" + "\t".repeat(tabs) + "attribute_name_index = " + attributeNameIndex + ";" +
            "\n" + "\t".repeat(tabs) + "attribute_length = " + attributeLength + ";" +
            "\n" + "\t".repeat(tabs) + "max_stack = " + maxStack + ";" +
            "\n" + "\t".repeat(tabs) + "max_locals = " + maxLocals + ";" +
            "\n" + "\t".repeat(tabs) + "code_length = " + codeLength + ";" +
            "\n" + "\t".repeat(tabs) + "code = " + Arrays.toString(code) + ";" +
            "\n" + "\t".repeat(tabs) + "exception_table_length = " + exceptionTableLength + ";" +
            "\n" + "\t".repeat(tabs) + "exception_table = " + Arrays.toString(exceptionTable) + ";" + // todo: implement
            "\n" + "\t".repeat(tabs) + "attributes_count = " + attributesCount + ";" +
            "\n" + "\t".repeat(tabs) + "attributes = " +
              Arrays.stream(attributes)
                .map(ai -> ai.toPrettyString(tabs + 3))
                .collect(Collectors
                .joining(
                        "\n" + "\t".repeat(tabs + 2),
                        "{\n" + "\t".repeat(tabs + 2),
                        "\n" + "\t".repeat(tabs + 1) + "}"))
            + ";" +
            "\n" + "\t".repeat(tabs - 1) + '}';
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
