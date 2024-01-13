package org.bytecodeparser.core;

import org.bytecodeparser.files.BytecodeFileResolver;
import org.bytecodeparser.files.BytecodeFileResolverInResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.bytecodeparser.assertions.BytecodeClassAssertion.assertThat;

class BytecodeClassTest {

    private byte[] personClassBytes;

    @BeforeEach
    void setUp() {
        BytecodeFileResolver bytecodeFileResolver = new BytecodeFileResolverInResources();
        personClassBytes = bytecodeFileResolver.readFileToByteArrayViaFileName("Person.class");
    }

    @Test
    void fromShouldCreateBytecodeClassObjectWithValidConstantFieldsFromPersonClass() {
        BytecodeClass personBytecodeClass = BytecodeClass.from(personClassBytes);

        assertThat(personBytecodeClass)
                .cafebabed()
                .hasVersion(61.0)
                .cpCountIs((short) 24);
    }
}