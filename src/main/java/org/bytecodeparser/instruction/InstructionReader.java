package org.bytecodeparser.instruction;

import one.util.streamex.StreamEx;
import org.reflections.Reflections;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstructionReader {

  private static final Map<Byte, Class<? extends Instruction>> opCodeToInstruction;

  static {
    Reflections reflections = new Reflections("org.bytecodeparser.instruction");
    opCodeToInstruction = StreamEx.of(reflections.getSubTypesOf(Instruction.class))
        .toMap(inst -> (byte) inst.getAnnotation(Opcode.class).value(), i -> i);
  }

  public static List<Instruction> read(byte[] data) throws IOException {
    DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
    List<Instruction> instructions = new ArrayList<>();
    byte opcode;
      try {
        while (inputStream.available() > 0) {
          if ((opcode = inputStream.readByte()) == 0) {
            break;
          }
          Instruction instruction =
              opCodeToInstruction.get(opcode).getConstructor(DataInputStream.class).newInstance(inputStream);
          instructions.add(instruction);
        }
      } catch (IOException | InstantiationException | IllegalAccessException | InvocationTargetException |
               NoSuchMethodException e) {
        throw new RuntimeException("Unable tor read instructions\n" + e);
      }
    return instructions;
  }

}
