package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class ElementValue {
  private final byte tag;

  private final short constValueIndex;
  private final EnumConstValue enumConstValue;
  private final short classInfoIndex;
  private final Annotation annotationValue;
  private final ArrayValue arrayValue;

  public ElementValue(DataInputStream dataInputStream) throws IOException {
    this.tag = dataInputStream.readByte();

    short constValueIndex = 0;
    EnumConstValue enumConstValue = null;
    short classInfoIndex = 0;
    Annotation annotationValue = null;
    ArrayValue arrayValue = null;

    if(isString()) {
      constValueIndex = dataInputStream.readShort();
    } else if(isEnumConstant()) {
      enumConstValue = new EnumConstValue(dataInputStream);
    } else if(isClass()) {
      classInfoIndex = dataInputStream.readShort();
    } else if (isAnnotationType()) {
      annotationValue = new Annotation(dataInputStream);
    } else if (isArray()) {
      arrayValue = new ArrayValue(dataInputStream);
    }

    this.constValueIndex = constValueIndex;
    this.enumConstValue = enumConstValue;
    this.classInfoIndex = classInfoIndex;
    this.annotationValue = annotationValue;
    this.arrayValue = arrayValue;
  }

  public boolean isString() {
    return tag == 's';
  }

  public boolean isEnumConstant() {
    return tag == 'e';
  }

  public boolean isClass() {
    return tag == 'c';
  }

  public boolean isAnnotationType() {
    return tag == '@';
  }

  public boolean isArray() {
    return tag == '[';
  }

  private static ElementValue[] readNElementValue(DataInputStream dataInputStream, int size) throws IOException {
    ElementValue[] elementValues = new ElementValue[size];
    for (int i = 0; i < size; i++) {
      elementValues[i] = new ElementValue(dataInputStream);
    }
    return elementValues;
  }

  static class EnumConstValue {
    private final short typeNameIndex;
    private final short constNameIndex;

    public EnumConstValue(DataInputStream dataInputStream) throws IOException {
      this.typeNameIndex = dataInputStream.readShort();
      this.constNameIndex = dataInputStream.readShort();
    }
  }

  static class ArrayValue {
    private final short numValues;
    private final ElementValue[] values;

    public ArrayValue(DataInputStream dataInputStream) throws IOException {
      this.numValues = dataInputStream.readShort();
      this.values = ElementValue.readNElementValue(dataInputStream, numValues);
    }
  }

}
