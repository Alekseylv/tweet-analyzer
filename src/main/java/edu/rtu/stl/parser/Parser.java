package edu.rtu.stl.parser;

import java.io.IOException;
import java.nio.file.Path;

import edu.rtu.stl.domain.DataSet;

public interface Parser {
    DataSet parse(Path file) throws IOException;
}
