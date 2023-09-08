package org.bytecodeparser.instruction;

import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;

import java.io.DataInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionTest {

  private static final Collection<Class<? extends Instruction>> INSTRUCTIONS =
      new Reflections("org.bytecodeparser.instruction").getSubTypesOf(Instruction.class);

  private static Collection<Class<? extends Instruction>> getInstructions() {
    return INSTRUCTIONS;
  }

  @ParameterizedTest
  @MethodSource("getInstructions")
  void allInstructionsShouldBeAnnotatedWithOpcode(Class<? extends Instruction> inst) {
    assertThat(inst.isAnnotationPresent(Opcode.class)).isTrue();
  }

  @ParameterizedTest
  @MethodSource("getInstructions")
  void allInstructionsShouldHaveDataInputStreamConstructor(Class<? extends Instruction> inst) {
    assertThat(inst.getDeclaredConstructors())
        .withFailMessage(() -> inst.getSimpleName() + " instruction doesn't contain constructor with DataInputStream")
        .anyMatch(constructor -> Arrays.stream(constructor.getParameterTypes()).collect(Collectors.toSet())
            .equals(Set.of(DataInputStream.class)))
    ;
  }

}