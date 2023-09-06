package org.bytecodeparser.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.bytecodeparser.core.BytecodeClass;

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
}
