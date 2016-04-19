package edu.rtu.stl.parser;

import java.io.IOException;
import java.util.List;

import edu.rtu.stl.domain.DataSet;

public interface Parser {
    DataSet parse(List<String> lines) throws IOException;
}
