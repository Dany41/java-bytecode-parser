package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(185)
@CustomPrettyPrint
public class InvokeInterface implements Instruction {
    private final byte indexbyte1;
    private final byte indexbyte2;
    private final byte count;
    private final byte zero;

    public InvokeInterface(DataInputStream dataInputStream) throws IOException {
        this.indexbyte1 = dataInputStream.readByte();
        this.indexbyte2 = dataInputStream.readByte();
        this.count = dataInputStream.readByte();
        this.zero = dataInputStream.readByte();
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode() + " " + ((indexbyte1 << 8) | indexbyte2 & 0xff) + " " + count + " " + zero;
    }
}
