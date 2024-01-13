package org.bytecodeparser.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.bytecodeparser.core.BytecodeClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BytecodeClassAssertion extends AbstractAssert<BytecodeClassAssertion, BytecodeClass> {
    protected BytecodeClassAssertion(BytecodeClass actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public static BytecodeClassAssertion assertThat(BytecodeClass actual) {
        return new BytecodeClassAssertion(actual, BytecodeClassAssertion.class);
    }

    public BytecodeClassAssertion cafebabed() {
        isNotNull();
        Assertions.assertThat(actual.getMagic()).isEqualTo(0xcafebabe);
        return this;
    }

    public BytecodeClassAssertion hasVersion(double version) {
        isNotNull();
        BigDecimal actualMinorVersion = BigDecimal.valueOf(actual.getMinorVersion());
        BigDecimal actualMajorVersion = BigDecimal.valueOf(actual.getMajorVersion());
        BigDecimal expectedVersion = BigDecimal.valueOf(version);
        Assertions.assertThat(actualMajorVersion)
                .as("Checking the major version")
                .isEqualTo(expectedVersion.setScale(0, RoundingMode.FLOOR));
        Assertions.assertThat(actualMinorVersion)
                .as("Checking the minor version")
                .isEqualTo(expectedVersion.subtract(actualMajorVersion).movePointRight(1));
        return this;
    }

    public BytecodeClassAssertion cpCountIs(short cpCountExpected) {
        isNotNull();
        Assertions.assertThat(actual.getConstantPoolCount()).isEqualTo(cpCountExpected);
        return this;
    }
}
