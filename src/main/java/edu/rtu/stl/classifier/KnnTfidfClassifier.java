package edu.rtu.stl.classifier;

import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.KnnTfidfDataSet;
import edu.rtu.stl.domain.TFIDFDocument;
import edu.rtu.stl.parser.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mordavolt.
 */
public class KnnTfidfClassifier implements Classifier<KnnTfidfDataSet> {

    private static final Logger LOG = LoggerFactory.getLogger(KnnTfidfClassifier.class);

    private final Tokenizer tokenizer;
    private KnnTfidfDataSet dataSet;


    public KnnTfidfClassifier(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Result classify(Document document) {
        System.out.println("Classifying document: " + document.text);

        TFIDFDocument workDocument = new TFIDFDocument(document);
        tokenizer.tokenize(document.text).forEach(workDocument::addTerm);
        dataSet.calculateTFIDFValue(workDocument);

        return dataSet.calculateKNNSentiment(workDocument, 7);
    }

    @Override
    public Classifier<KnnTfidfDataSet> trainClassifier(KnnTfidfDataSet data) {
        this.dataSet = data;
        return this;
    }
}
