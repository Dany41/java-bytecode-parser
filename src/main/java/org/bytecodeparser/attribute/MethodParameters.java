package org.bytecodeparser.attribute;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.attribute.MethodParameters.Parameter.readNParameters;

public class MethodParameters extends AttributeInfo {

    private final short parametersCount;
    private final Parameter[] parameters;

    public MethodParameters(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
        super(attributeNameIndex, attributeLength);
        this.parametersCount = (short) Byte.toUnsignedInt(dataInputStream.readByte());
        this.parameters = readNParameters(dataInputStream, this.parametersCount);
    }

    static class Parameter {

        private final short nameIndex;
        private final short accessFlags;

        public static Parameter[] readNParameters(DataInputStream dataInputStream, int size)
                throws IOException {
            Parameter[] parameters = new Parameter[size];
            for (int i = 0; i < size; i++) {
                parameters[i] = new Parameter(dataInputStream);
            }
            return parameters;
        }

        public Parameter(DataInputStream dataInputStream) throws IOException {
            this.nameIndex = dataInputStream.readShort();
            this.accessFlags = dataInputStream.readShort();
        }

    }
}
