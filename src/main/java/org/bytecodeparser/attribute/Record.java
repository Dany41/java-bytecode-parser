package org.bytecodeparser.attribute;

import org.bytecodeparser.annotation.ConsumeConstantPool;
import org.bytecodeparser.constant.ConstantType;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.attribute.AttributeInfoReader.read;
import static org.bytecodeparser.attribute.Record.RecordComponentInfo.readNComponents;

@ConsumeConstantPool
public class Record extends AttributeInfo {

    private final short componentsCount;
    private final RecordComponentInfo[] components;

    public Record(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream, ConstantType[] constantPool) throws IOException {
        super(attributeNameIndex, attributeLength);
        this.componentsCount = dataInputStream.readShort();
        this.components = readNComponents(dataInputStream, this.componentsCount, constantPool);
    }

    static class RecordComponentInfo {

        private final short nameIndex;
        private final short descriptorIndex;
        private final short attributesCount;
        private final AttributeInfo[] attributes;

        public static RecordComponentInfo[] readNComponents(DataInputStream dataInputStream, int size, ConstantType[] constantPool)
                throws IOException {
            RecordComponentInfo[] parameters = new RecordComponentInfo[size];
            for (int i = 0; i < size; i++) {
                parameters[i] = new RecordComponentInfo(dataInputStream, constantPool);
            }
            return parameters;
        }

        public RecordComponentInfo(DataInputStream dataInputStream, ConstantType[] constantPool) throws IOException {
            this.nameIndex = dataInputStream.readShort();
            this.descriptorIndex = dataInputStream.readShort();
            this.attributesCount = dataInputStream.readShort();
            this.attributes = read(dataInputStream, attributesCount, constantPool);
        }
    }
}
