package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class Annotation {
  private final short typeIndex;
  private final short numElementValuePairs;
  private final ElementValuePair[] elementValuePairs;

  public Annotation(DataInputStream dataInputStream) throws IOException {
    this.typeIndex = dataInputStream.readShort();
    this.numElementValuePairs = dataInputStream.readShort();
    this.elementValuePairs = ElementValuePair.readNElementValuePair(dataInputStream, numElementValuePairs);
  }

  public static Annotation[] readNAnnotation(DataInputStream dataInputStream, int size)
      throws IOException {
    Annotation[] annotations = new Annotation[size];
    for (int i = 0; i < size; i++) {
      annotations[i] = new Annotation(dataInputStream);
    }
    return annotations;
  }

  static class ElementValuePair {
    private final short elementNameIndex;
    private final ElementValue value;

    public static ElementValuePair[] readNElementValuePair(DataInputStream dataInputStream, int size)
        throws IOException {
      ElementValuePair[] valuePairs = new ElementValuePair[size];
      for (int i = 0; i < size; i++) {
        valuePairs[i] = new ElementValuePair(dataInputStream);
      }
      return valuePairs;
    }

    ElementValuePair(DataInputStream dataInputStream) throws IOException {
      this.elementNameIndex = dataInputStream.readShort();
      this.value = new ElementValue(dataInputStream);
    }
  }
}
