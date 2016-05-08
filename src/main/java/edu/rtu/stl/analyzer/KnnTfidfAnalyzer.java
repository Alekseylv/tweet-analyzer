package edu.rtu.stl.analyzer;

import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.KnnTfidfDataSet;
import edu.rtu.stl.domain.TFIDFDocument;
import edu.rtu.stl.parser.Tokenizer;

import java.util.List;

/**
 * Created by Mordavolt.
 */
public class KnnTfidfAnalyzer implements Analyzer<KnnTfidfDataSet> {

    private final Tokenizer tokenizer;

    public KnnTfidfAnalyzer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public KnnTfidfDataSet analyze(List<Document> documents) {
        KnnTfidfDataSet dataSet = new KnnTfidfDataSet();
        for (Document document : documents) {
            TFIDFDocument workDocument = dataSet.addDocument(document);
            for (String key : tokenizer.tokenize(document.text)) {
                dataSet.addTerm(document.sentiment, key);
                workDocument.addTerm(key);
            }
        }
        dataSet.calculateTFIDFValues();
        return dataSet;
    }
}
