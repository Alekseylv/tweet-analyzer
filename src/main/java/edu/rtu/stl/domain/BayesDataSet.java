package edu.rtu.stl.domain;

import java.util.HashMap;
import java.util.Map;

public class BayesDataSet {

    private final DataSet dataSet;
    private final Map<String, Double>[] termProbabilities = new Map[Sentiment.values().length];
    private final double[] zeroTermProbability = new double[Sentiment.values().length];

    public BayesDataSet(DataSet dataSet) {
        this.dataSet = dataSet;

        for (int i = 0; i < Sentiment.values().length; i++) {
            termProbabilities[i] = new HashMap<>();
            zeroTermProbability[i] = probability(Sentiment.values()[i], 0);
        }
    }

    public double termProbability(Sentiment sentiment, String key) {
        if (termProbabilities[sentiment.ordinal()].containsKey(key)) {
            return termProbabilities[sentiment.ordinal()].get(key);
        }

        if (!dataSet.getFrequencies(sentiment).containsKey(key)) {
            return zeroTermProbability[sentiment.ordinal()];
        }

        int termFrequencyInSentiment = dataSet.getFrequencies(sentiment).get(key);

        double probability = probability(sentiment, termFrequencyInSentiment);
        termProbabilities[sentiment.ordinal()].put(key, probability);
        return probability;
    }

    public double sentimentProbablity(Sentiment sentiment) {
        return dataSet.getDocumentCount(sentiment) / (double) (dataSet.getDocumentCount());
    }

    private double probability(Sentiment sentiment, int termFrequencyInSentiment) {
        int totalTermCountInSentiment = dataSet.getTotalTerms(sentiment);
        int distinctTermCount = dataSet.distinctTermCount();

        return termFrequencyInSentiment + 1 /  (double) (totalTermCountInSentiment + distinctTermCount);
    }

}
