package org.bytecodeparser.core;

import org.bytecodeparser.files.BytecodeFileResolver;
import org.bytecodeparser.files.BytecodeFileResolverInResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.assertions.BytecodeClassAssertion.assertThat;

class BytecodeClassTest {

    private byte[] personClassBytes;

    @BeforeEach
    void setUp() {
        BytecodeFileResolver bytecodeFileResolver = new BytecodeFileResolverInResources();
        personClassBytes = bytecodeFileResolver.readFileToByteArrayViaFileName("Person.class");
    }

    @Test
    void fromShouldCreateValidBytecodeClassObjectFromPersonClass() {
        BytecodeClass personBytecodeClass = BytecodeClass.from(personClassBytes);

        assertThat(personBytecodeClass).cafebabed();
    }
}