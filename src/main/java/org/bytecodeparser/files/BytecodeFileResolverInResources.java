package org.bytecodeparser.files;

import org.bytecodeparser.exceptions.FileReadException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BytecodeFileResolverInResources implements BytecodeFileResolver {

    private String resourcePath = "";

    public BytecodeFileResolverInResources() {}

    public BytecodeFileResolverInResources(String resourcePath) {
        this.resourcePath = resourcePath + "/";
    }

    public BytecodeFileResolverInResources(boolean local) {
        this.resourcePath = local ? "local/" : "";
    }

    @Override
    public String readFileToStringViaFileName(String fileName) {
        String fullPathName = resourcePath + fileName;
        URL url = Thread.currentThread().getContextClassLoader().getResource(fullPathName);

        if (url == null)
            throw new FileReadException("Could not find file with name '" + fullPathName + "' in resource folder" );

        try {
            Path path = Paths.get(url.toURI());
            List<String> strings = Files.readAllLines(path);
            return String.join("", strings);
        } catch (IOException | URISyntaxException e) {
            throw new FileReadException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public String readFileToStringViaFilePath(String filePath) {
        return null;
    }

    @Override
    public String readFileToStringViaFilePath(Path filePath) {
        return null;
    }

    @Override
    public byte[] readFileToByteArrayViaFileName(String fileName) {
        String fullPathName = resourcePath + fileName;
        URL url = Thread.currentThread().getContextClassLoader().getResource(fullPathName);

        if (url == null)
            throw new FileReadException("Could not find file with name '" + fullPathName + "' in resource folder" );

        try {
            Path path = Paths.get(url.toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new FileReadException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public byte[] readFileToByteArrayViaFilePath(String filePath) {
        return new byte[0];
    }

    @Override
    public byte[] readFileToByteArrayViaFilePath(Path filePath) {
        return new byte[0];
    }
}
