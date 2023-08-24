package org.bytecodeparser;

public class BytecodeClassBuilder {

    private byte[] magicBytes;
    private byte[] minorVersionBytes;
    private byte[] majorVersionBytes;

    public static BytecodeClassBuilder builder() {
        return new BytecodeClassBuilder();
    }

    public BytecodeClassBuilder magic(byte[] magicBytes) {
        this.magicBytes = magicBytes;
        return this;
    }

    public BytecodeClassBuilder minorVersion(byte[] minorVersionBytes) {
        this.minorVersionBytes = minorVersionBytes;
        return this;
    }

    public BytecodeClassBuilder majorVersion(byte[] majorVersionBytes) {
        this.majorVersionBytes = majorVersionBytes;
        return this;
    }

    public BytecodeClass build() {
        BytecodeClass bytecodeClass = new BytecodeClass();
        bytecodeClass.setMagicBytes(this.magicBytes);
        bytecodeClass.setMinorVersionBytes(this.minorVersionBytes);
        bytecodeClass.setMajorVersionBytes(this.majorVersionBytes);
        return bytecodeClass;
    }
}
