package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(184)
@CustomPrettyPrint
public class InvokeStatic implements Instruction {
    private final byte indexbyte1;
    private final byte indexbyte2;

    public InvokeStatic(DataInputStream dataInputStream) throws IOException {
        this.indexbyte1 = dataInputStream.readByte();
        this.indexbyte2 = dataInputStream.readByte();
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode() + " " + ((indexbyte1 << 8) | indexbyte2 & 0xff);
    }
}
