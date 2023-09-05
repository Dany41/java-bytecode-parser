package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class AnnotationDefault {
  private final short attribute_name_index;
  private final int attribute_length;
  private final ElementValue default_value;

  public AnnotationDefault(DataInputStream dataInputStream) throws IOException {
    this.attribute_name_index = dataInputStream.readShort();
    this.attribute_length = dataInputStream.readInt();
    this.default_value = new ElementValue(dataInputStream);
  }

}
