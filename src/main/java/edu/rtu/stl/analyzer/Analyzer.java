package edu.rtu.stl.analyzer;

import java.util.List;

import javax.print.Doc;

import edu.rtu.stl.domain.DataSet;
import edu.rtu.stl.domain.Document;

public interface Analyzer {
    DataSet analyze(List<Document> documents);
}
