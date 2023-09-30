package org.bytecodeparser.instruction;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.*;

public class InstructionReader {

    public static List<Instruction> read(byte[] data) {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
        List<Instruction> instructions = new ArrayList<>();
        byte opcode = -1;
        int uOpcode = opcode;
        try {
            while (inputStream.available() > 0) {
                if ((opcode = inputStream.readByte()) == 0) {
                    break;
                }
                uOpcode = Byte.toUnsignedInt(opcode);
                Instruction instruction = new Instruction(uOpcode, inputStream);
                instructions.add(instruction);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to read instruction with opcode = " + uOpcode + "\n" + e);
        }
        return instructions;
    }

}
