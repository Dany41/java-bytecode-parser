package org.bytecodeparser.files;

import java.nio.file.Path;

public interface BytecodeFileResolver {
    String readFileToStringViaFileName(String fileName);
    String readFileToStringViaFilePath(String filePath);
    String readFileToStringViaFilePath(Path filePath);
    byte[] readFileToByteArrayViaFileName(String fileName);
    byte[] readFileToByteArrayViaFilePath(String filePath);
    byte[] readFileToByteArrayViaFilePath(Path filePath);
}
