package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisibleAnnotations {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short numAnnotations;
  private final Annotation[] annotations;

  public RuntimeInvisibleAnnotations(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.numAnnotations = dataInputStream.readShort();
    this.annotations = Annotation.readNAnnotation(dataInputStream, numAnnotations);
  }
}
