package edu.rtu.stl.classifier;

import static java.lang.Math.log;

import edu.rtu.stl.domain.BayesDataSet;
import edu.rtu.stl.domain.Sentiment;
import edu.rtu.stl.parser.Tokenizer;

public class BernulliNaiveBayesClassifier extends MultinomialNaiveBayesClassifier {

    public BernulliNaiveBayesClassifier(BayesDataSet dataSet, Tokenizer tokenizer) {
        super(dataSet, tokenizer);
    }

    @Override
    public double incrementScore(Sentiment sentiment, String key) {
        if (dataSet.getFrequencies(sentiment).containsKey(key)) {
            return log(dataSet.bernulliTermProbability(sentiment, key));
        } else {
            return log(1 - dataSet.bernulliTermProbability(sentiment, key));
        }
    }
}
