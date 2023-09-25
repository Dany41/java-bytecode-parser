package org.bytecodeparser.instruction;

import one.util.streamex.StreamEx;
import org.reflections.Reflections;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InstructionReader {

//    private static final Map<Integer, Class<? extends Instruction>> opCodeToInstruction;
//
//    static {
//        Reflections reflections = new Reflections("org.bytecodeparser.instruction");
//
//        Set<Class<? extends Instruction>> instructions = reflections.getSubTypesOf(Instruction.class);
//
//        opCodeToInstruction = StreamEx.of(instructions)
//                .flatMapToInt(aClass -> Arrays.stream(aClass.getAnnotation(Opcode.class).value()))
//                .boxed()
//                .collect(Collectors.toMap(o -> o
//                        , o -> StreamEx.of(instructions)
//                                .findAny(aClass -> Arrays.stream(aClass.getAnnotation(Opcode.class).value()).anyMatch(o::equals))
//                                .orElseThrow(() -> new RuntimeException("Impossible!"))));
//
////        opCodeToInstruction = StreamEx.of(reflections.getSubTypesOf(Instruction.class))
////                .toMap(inst -> inst.getAnnotation(Opcode.class).value(), i -> i);
//    }

    public static List<Instructions> read(byte[] data) {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
        List<Instructions> instructions = new ArrayList<>();
        byte opcode = -1;
        int uOpcode = opcode;
        try {
            while (inputStream.available() > 0) {
                if ((opcode = inputStream.readByte()) == 0) {
                    break;
                }
                uOpcode = Byte.toUnsignedInt(opcode);
                Instructions instruction = new Instructions(uOpcode, inputStream);
//                Class<? extends Instruction> aClass = opCodeToInstruction.get(uOpcode);
//                Instruction instruction;
//                if (aClass.getAnnotation(Opcode.class).value().length > 1) {
//                    instruction = aClass.getConstructor(DataInputStream.class, int.class).newInstance(inputStream, uOpcode);
//                } else {
//                    instruction = aClass.getConstructor(DataInputStream.class).newInstance(inputStream);
//                }
                instructions.add(instruction);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable tor read instruction with opcode = " + uOpcode + "\n" + e);
        }
        return instructions;
    }

}
