package edu.rtu.stl.classifier;

import static java.lang.Math.log;

import java.util.List;

import edu.rtu.stl.domain.BayesDataSet;
import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.Sentiment;
import edu.rtu.stl.parser.Tokenizer;

public class MultinomialNaiveBayesClassifier implements Classifier {

    public final BayesDataSet dataSet;
    public final Tokenizer tokenizer;

    public MultinomialNaiveBayesClassifier(BayesDataSet dataSet, Tokenizer tokenizer) {
        this.dataSet = dataSet;
        this.tokenizer = tokenizer;
    }

    @Override
    public Result classify(Document document) {
        List<String> tokens = tokenizer.tokenize(document.text);
        double[] score = new double[Sentiment.values().length];

        for (int i = 0; i < Sentiment.values().length; i++) {
            Sentiment sentiment = Sentiment.values()[i];
            score[i] = log(dataSet.sentimentProbability(sentiment));
            for (String token : tokens) {
                score[i] += incrementScore(sentiment, token);
            }
        }

        Result max = new Result(Sentiment.values()[0], score[0], document);
        for (int i = 1; i < Sentiment.values().length; i++) {
            if (max.score < score[i]) {
                max = new Result(Sentiment.values()[i], score[i], document);
            }
        }
        return max;
    }

    public double incrementScore(Sentiment sentiment, String key) {
        return log(dataSet.multinomialTermProbability(sentiment, key));
    }

}