package org.bytecodeparser.instruction;

import one.util.streamex.StreamEx;
import org.reflections.Reflections;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstructionReader {

  private static final Map<Integer, Class<? extends Instruction>> opCodeToInstruction;

  static {
    Reflections reflections = new Reflections("org.bytecodeparser.instruction");
    opCodeToInstruction = StreamEx.of(reflections.getSubTypesOf(Instruction.class))
        .toMap(inst -> inst.getAnnotation(Opcode.class).value(), i -> i);
  }

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
        Instruction instruction =
                opCodeToInstruction.get(uOpcode).getConstructor(DataInputStream.class).newInstance(inputStream);
        instructions.add(instruction);
      }
    } catch (Exception e) {
      throw new RuntimeException("Unable tor read instruction with opcode = " + uOpcode + "\n" + e);
    }
    return instructions;
  }

}
