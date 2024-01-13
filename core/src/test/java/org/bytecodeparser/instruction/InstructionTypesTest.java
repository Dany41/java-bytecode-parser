package org.bytecodeparser.instruction;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionTypesTest {

    @Test
    void eachInstructionHasUniqueOpCode() {
        long opCodesCount = Arrays.stream(InstructionTypes.values()).map(InstructionTypes::getOpCode).distinct().count();
        assertThat(opCodesCount).isEqualTo(InstructionTypes.values().length);
    }

}