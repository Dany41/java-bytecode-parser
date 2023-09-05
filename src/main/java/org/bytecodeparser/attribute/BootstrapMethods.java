package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.attribute.BootstrapMethods.BootstrapMethod.readNBootstrapMethod;
import static org.bytecodeparser.utility.Utils.readShortArray;

public class BootstrapMethods {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short numBootstrapMethods;
  private final BootstrapMethod[] bootstrapMethods;

  public BootstrapMethods(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.numBootstrapMethods = dataInputStream.readShort();
    this.bootstrapMethods = readNBootstrapMethod(dataInputStream, numBootstrapMethods);
  }

  static class BootstrapMethod {
    private final short bootstrapMethodRef;
    private final short numBootstrapArguments;
    private final short[] bootstrapArguments;

    public static BootstrapMethod[] readNBootstrapMethod(DataInputStream dataInputStream, int size)
        throws IOException {
      BootstrapMethod[] et = new BootstrapMethod[size];
      for (int i = 0; i < size; i++) {
        et[i] = new BootstrapMethod(dataInputStream);
      }
      return et;
    }

    public BootstrapMethod(DataInputStream dataInputStream) throws IOException {
      this.bootstrapMethodRef = dataInputStream.readShort();
      this.numBootstrapArguments = dataInputStream.readShort();
      this.bootstrapArguments = readShortArray(dataInputStream, numBootstrapArguments);
    }

  }
}
