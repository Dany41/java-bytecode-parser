package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(192)
@CustomPrettyPrint
public class CheckCast implements Instruction {
    private final byte indexbyte1;
    private final byte indexbyte2;

    public CheckCast(DataInputStream dataInputStream) throws IOException {
        this.indexbyte1 = dataInputStream.readByte();
        this.indexbyte2 = dataInputStream.readByte();
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode() + " " + ((indexbyte1 << 8) | indexbyte2 & 0xff);
    }
}
