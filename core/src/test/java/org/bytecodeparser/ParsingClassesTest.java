package org.bytecodeparser;

import org.bytecodeparser.core.BytecodeClass;
import org.bytecodeparser.files.BytecodeFileResolver;
import org.bytecodeparser.files.BytecodeFileResolverInResources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsingClassesTest {

    private static BytecodeFileResolver bytecodeFileResolver;

    @BeforeAll
    static void setUp() {
        bytecodeFileResolver = new BytecodeFileResolverInResources();
    }

    @ParameterizedTest
    @MethodSource(value = "getClassesAsBytes")
    void parserShouldNotFailWhileParsingClassesFromResources(Path pathToClass) {
        byte[] classAsBytes = bytecodeFileResolver.readFileToByteArrayViaFilePath(pathToClass);
        BytecodeClass bytecodeClass = BytecodeClass.from(classAsBytes);
        assertThat(bytecodeClass).isNotNull();
        System.out.println(bytecodeClass);
    }

    private static Stream<Arguments> getClassesAsBytes() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("Person.class");
        assert resource != null;
        try {
            return Files.walk(Paths.get(resource.toURI()).getParent())
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getParent().endsWith("test-classes\\"))
                    .map(Arguments::of);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
