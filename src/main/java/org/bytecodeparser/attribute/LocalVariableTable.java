package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;
import org.bytecodeparser.structures.PrettyPrintable;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LocalVariableTable extends AttributeInfo {
  private final short localVariableTableLength;
  private final LocalVariable[] localVariableTable;

  public LocalVariableTable(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.localVariableTableLength = dataInputStream.readShort();
    this.localVariableTable = LocalVariable.readNLocalVariable(dataInputStream, localVariableTableLength);
  }

  @Override
  public String toPrettyString(int tabs) {
    return "LineNumberTable {" +
            "\n" + "\t".repeat(tabs) + "attribute_name_index = " + attributeNameIndex + ";" +
            "\n" + "\t".repeat(tabs) + "attribute_length = " + attributeLength + ";" +
            "\n" + "\t".repeat(tabs) + "local_variable_table_length = " + localVariableTableLength + ";" +
            "\n" + "\t".repeat(tabs) + "local_variable_table = " +
            Arrays.stream(localVariableTable)
                    .map(lv -> lv.toPrettyString(tabs + 3))
                    .collect(Collectors
                            .joining(
                                    "\n" + "\t".repeat(tabs + 2),
                                    "{\n" + "\t".repeat(tabs + 2),
                                    "\n" + "\t".repeat(tabs + 1) + "}"))
            + ";" +
            "\n" + "\t".repeat(tabs - 1) + '}';
  }

  static class LocalVariable implements PrettyPrintable {
    private final short startPc;
    private final short length;
    private final short nameIndex;
    private final short descriptorIndex;
    private final short index;

    public static LocalVariable[] readNLocalVariable(DataInputStream dataInputStream, int size)
        throws IOException {
      LocalVariable[] localVariables = new LocalVariable[size];
      for (int i = 0; i < size; i++) {
        localVariables[i] = new LocalVariable(dataInputStream);
      }
      return localVariables;
    }

    public LocalVariable(DataInputStream dataInputStream) throws IOException {
      this.startPc = dataInputStream.readShort();
      this.length = dataInputStream.readShort();
      this.nameIndex = dataInputStream.readShort();
      this.descriptorIndex = dataInputStream.readShort();
      this.index = dataInputStream.readShort();
    }

    @Override
    public String toPrettyString(int tabs) {
      return "LocalVariable {" +
              "\n" + "\t".repeat(tabs) + "start_pc = " + startPc + ";" +
              "\n" + "\t".repeat(tabs) + "length = " + length + ";" +
              "\n" + "\t".repeat(tabs) + "name_index = " + nameIndex + ";" +
              "\n" + "\t".repeat(tabs) + "descriptor_index = " + descriptorIndex + ";" +
              "\n" + "\t".repeat(tabs) + "index = " + index + ";" +
              "\n" + "\t".repeat(tabs - 1) + '}';
    }
  }
}
