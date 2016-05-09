package edu.rtu.stl.classifier;

import edu.rtu.stl.domain.BaselineDataSet;
import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.Sentiment;

/**
 * Created by a.drozdovs on 09/05/2016.
 */
public class BaselineClassifier implements Classifier<BaselineDataSet> {

    private Sentiment mostCommonSentiment;

    @Override
    public Result classify(Document document) {
        return new Result(mostCommonSentiment, 1, document);
    }

    @Override
    public Classifier<BaselineDataSet> trainClassifier(BaselineDataSet dataSet) {
        mostCommonSentiment = dataSet.getMostCommonSentiment();
        return this;
    }
}
