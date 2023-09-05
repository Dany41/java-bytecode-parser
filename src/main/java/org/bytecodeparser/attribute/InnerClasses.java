package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class InnerClasses {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short numberOfClasses;
  private final ClassInfo[] classes;

  public InnerClasses(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.numberOfClasses = dataInputStream.readShort();
    this.classes = ClassInfo.readNClassInfo(dataInputStream, numberOfClasses);
  }

  static class ClassInfo {
    private final short innerClassInfoIndex;
    private final short outerClassInfoIndex;
    private final short innerNameIndex;
    private final short innerClassAccessFlags;

    public static ClassInfo[] readNClassInfo(DataInputStream dataInputStream, int size)
        throws IOException {
      ClassInfo[] et = new ClassInfo[size];
      for (int i = 0; i < size; i++) {
        et[i] = new ClassInfo(dataInputStream);
      }
      return et;
    }

    public ClassInfo(DataInputStream dataInputStream) throws IOException {
      this.innerClassInfoIndex = dataInputStream.readShort();
      this.outerClassInfoIndex = dataInputStream.readShort();
      this.innerNameIndex = dataInputStream.readShort();
      this.innerClassAccessFlags = dataInputStream.readShort();
    }

  }
}
