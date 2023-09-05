
package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisibleParameterAnnotations {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final byte numParameters;

  public RuntimeInvisibleParameterAnnotations(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.numParameters = dataInputStream.readByte();
    this.parameterAnnotations = ParameterAnnotation.readNParameterAnnotation(dataInputStream, numParameters);
  }

  private final ParameterAnnotation[] parameterAnnotations;

  static class ParameterAnnotation {
    private final short         numAnnotations;
    private final Annotation[] annotations;

    public static ParameterAnnotation[] readNParameterAnnotation(DataInputStream dataInputStream, int size)
        throws IOException {
      ParameterAnnotation[] annotations = new ParameterAnnotation[size];
      for (int i = 0; i < size; i++) {
        annotations[i] = new ParameterAnnotation(dataInputStream);
      }
      return annotations;
    }

    public ParameterAnnotation(DataInputStream dataInputStream) throws IOException {
      this.numAnnotations = dataInputStream.readShort();
      this.annotations = Annotation.readNAnnotation(dataInputStream, numAnnotations);
    }
  }
}
