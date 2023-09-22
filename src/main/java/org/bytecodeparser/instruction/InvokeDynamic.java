package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;
import java.io.IOException;

@Opcode(186)
@CustomPrettyPrint
public class InvokeDynamic implements Instruction {
    private final byte indexbyte1;
    private final byte indexbyte2;
    private final byte zero1;
    private final byte zero2;

    public InvokeDynamic(DataInputStream dataInputStream) throws IOException {
        this.indexbyte1 = dataInputStream.readByte();
        this.indexbyte2 = dataInputStream.readByte();
        this.zero1 = dataInputStream.readByte();
        this.zero2 = dataInputStream.readByte();
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode() + " " + ((indexbyte1 << 8) | indexbyte2 & 0xff) + " " + zero1 + " " + zero2;
    }
}
