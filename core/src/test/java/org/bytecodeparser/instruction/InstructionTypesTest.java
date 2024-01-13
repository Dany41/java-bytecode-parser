package org.bytecodeparser.instruction;

import lombok.Getter;
import one.util.streamex.EntryStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionTypesTest {

    @Getter(lazy = true)
    private final static Map<String, Integer> allInstructionsToOpcodes = parseAllInstructionsToOpcodes();

    @Getter(lazy = true)
    private final static Map<String, Integer> implementedInstructionsToOpcodes = computeImplementedInstructionsToOpcodes();

    @Test
    void eachInstructionHasUniqueOpCode() {
        long opCodesCount = Arrays.stream(InstructionTypes.values()).map(InstructionTypes::getOpCode).distinct().count();
        assertThat(opCodesCount).isEqualTo(InstructionTypes.values().length);
    }

    @Test
    @Disabled
    void allInstructionsImplemented() {
        Map<String, Integer> expectedInstructionToOpcodes = getAllInstructionsToOpcodes();
        Map<String, Integer> actuallyImplementedInstructionToOpcodes = getImplementedInstructionsToOpcodes();
        List<Map.Entry<String, Integer>> notImplemented = EntryStream.of(expectedInstructionToOpcodes)
                .filterKeys(Predicate.not(actuallyImplementedInstructionToOpcodes::containsKey))
                .sortedBy(Map.Entry::getValue)
                .toList();
        assertThat(notImplemented.size())
                .withFailMessage("Not implemented instructions: " + notImplemented)
                .isZero();
    }

    @Test
    void noNonExistingInstructionsImplemented() {
        Map<String, Integer> expectedInstructionToOpcodes = getAllInstructionsToOpcodes();
        Map<String, Integer> actuallyImplementedInstructionToOpcodes = getImplementedInstructionsToOpcodes();
        List<Map.Entry<String, Integer>> nonExisting = EntryStream.of(actuallyImplementedInstructionToOpcodes)
                .filterKeys(Predicate.not(expectedInstructionToOpcodes::containsKey))
                .sortedBy(Map.Entry::getValue)
                .toList();
        assertThat(nonExisting.size())
                .withFailMessage("Detected non-existing implemented instructions: " + nonExisting)
                .isZero();
    }

    private static Map<String, Integer> parseAllInstructionsToOpcodes() {
        try {
            return Files.readAllLines(Path.of("../scripts/instructions_with_opcodes.txt"))
                    .stream()
                    .map(s -> s.split(" "))
                    .collect(Collectors.toMap(kv -> kv[0], kv -> Integer.parseInt(kv[1])));
        } catch (IOException ignored) {
            throw new AssertionError("Couldn't get instruction_with_opcodes file");
        }
    }

    private static Map<String, Integer> computeImplementedInstructionsToOpcodes() {
        return Arrays.stream(InstructionTypes.values())
                .collect(Collectors.toMap(it -> it.name().toLowerCase(), InstructionTypes::getOpCode));
    }

}