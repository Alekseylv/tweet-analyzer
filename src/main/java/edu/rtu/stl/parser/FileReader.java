package edu.rtu.stl.parser;

import java.nio.file.Path;
import java.util.List;

public interface FileReader {
    List<String> readLines(Path path);
}
