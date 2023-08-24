package org.bytecodeparser;

import org.bytecodeparser.files.BytecodeFileResolver;
import org.bytecodeparser.files.BytecodeFileResolverInResources;

public class Main {

    /**
     * Input params:
     *  -fn --filename (file must be located under resources/ folder)
     *  -fp --filepath (full filepath)
     *  -l --local - flag to if true - will look under resources/local
     * @param args
     */
    public static void main(String[] args) {
        AppConfig config = AppConfig.resolve(args);
        BytecodeFileResolver bytecodeFileResolver = new BytecodeFileResolverInResources(config.isLocal());
        byte[] bytes = bytecodeFileResolver.readFileToByteArrayViaFileName(config.getFileName());
        ByteCodeRunner.run(bytes);
    }
}
