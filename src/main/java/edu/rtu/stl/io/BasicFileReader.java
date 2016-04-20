package edu.rtu.stl.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicFileReader implements FileReader {

    private static final Logger LOG = LoggerFactory.getLogger(BasicFileReader.class);

    @Override
    public List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            LOG.error("Failed to read file {}. {}", path, e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean matches(Path path) {
        String name = path.toFile().getName();
        return name.endsWith(".txt") || name.endsWith(".csv");
    }
}
