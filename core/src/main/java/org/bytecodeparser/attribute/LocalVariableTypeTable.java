package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class LocalVariableTypeTable extends AttributeInfo {
  private final short localVariableTypeTableLength;
  private final LocalVariableType[] localVariableTypeTable;

  public LocalVariableTypeTable(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.localVariableTypeTableLength = dataInputStream.readShort();
    this.localVariableTypeTable = LocalVariableType.readNLocalVariableType(dataInputStream, localVariableTypeTableLength);
  }

  static class LocalVariableType {
    private final short startPc;
    private final short length;
    private final short nameIndex;
    private final short signatureIndex;
    private final short index;

    public static LocalVariableType[] readNLocalVariableType(DataInputStream dataInputStream, int size)
        throws IOException {
      LocalVariableType[] localVariableTypes = new LocalVariableType[size];
      for (int i = 0; i < size; i++) {
        localVariableTypes[i] = new LocalVariableType(dataInputStream);
      }
      return localVariableTypes;
    }

    public LocalVariableType(DataInputStream dataInputStream) throws IOException {
      this.startPc = dataInputStream.readShort();
      this.length = dataInputStream.readShort();
      this.nameIndex = dataInputStream.readShort();
      this.signatureIndex = dataInputStream.readShort();
      this.index = dataInputStream.readShort();
    }
  }
}
