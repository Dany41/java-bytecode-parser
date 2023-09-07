package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class LocalVariableTable extends AttributeInfo {
  private final short localVariableTableLength;
  private final LocalVariable[] localVariableTable;

  public LocalVariableTable(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.localVariableTableLength = dataInputStream.readShort();
    this.localVariableTable = LocalVariable.readNLocalVariable(dataInputStream, localVariableTableLength);
  }

  static class LocalVariable {
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
  }
}
