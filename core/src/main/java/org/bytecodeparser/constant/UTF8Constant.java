package org.bytecodeparser.constant;

import lombok.Getter;

import java.io.DataInputStream;
import java.io.IOException;

@Tag(1)
public class UTF8Constant implements ConstantType {
    @Getter
    private String content;

    private short length;

    public UTF8Constant(DataInputStream dataInputStream) throws IOException {
        this.length = dataInputStream.readShort();
        this.content = new String(dataInputStream.readNBytes(this.length));
    }
}
