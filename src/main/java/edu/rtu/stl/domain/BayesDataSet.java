package edu.rtu.stl.domain;

import java.util.HashMap;
import java.util.Map;

public class BayesDataSet extends DataSet {

    private final Map<String, Double>[] termProbabilities = new Map[Sentiment.values().length];

    public BayesDataSet() {

        for (int i = 0; i < Sentiment.values().length; i++) {
            termProbabilities[i] = new HashMap<>();
        }
    }

    public double termProbability(Sentiment sentiment, String key) {
        if (termProbabilities[sentiment.ordinal()].containsKey(key)) {
            return termProbabilities[sentiment.ordinal()].get(key);
        }

        if (!getFrequencies(sentiment).containsKey(key)) {
            return probability(sentiment, 0);
        }

        int termFrequencyInSentiment = getFrequencies(sentiment).get(key);

        double probability = probability(sentiment, termFrequencyInSentiment);
        termProbabilities[sentiment.ordinal()].put(key, probability);
        return probability;
    }

    public double sentimentProbability(Sentiment sentiment) {
        return getDocumentCount(sentiment) / (double) (getDocumentCount());
    }

    private double probability(Sentiment sentiment, int termFrequencyInSentiment) {
        int totalTermCountInSentiment = getTotalTerms(sentiment);
        int distinctTermCount = distinctTermCount();

        return termFrequencyInSentiment + 1 /  (double) (totalTermCountInSentiment + distinctTermCount);
    }

}
