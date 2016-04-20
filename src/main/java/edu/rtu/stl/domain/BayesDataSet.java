package edu.rtu.stl.domain;

import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BayesDataSet {

    private final DataSet dataSet;
    private final Map<String, BigDecimal>[] termProbabilities = new Map[Sentiment.values().length];
    private final BigDecimal[] zeroTermProbability = new BigDecimal[Sentiment.values().length];

    {
        for (int i = 0; i < Sentiment.values().length; i++) {
            termProbabilities[i] = new HashMap<>();
            zeroTermProbability[i] = probability(Sentiment.values()[i], 0);
        }
    }

    public BayesDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public BigDecimal termProbability(Sentiment sentiment, String key) {
        if (termProbabilities[sentiment.ordinal()].containsKey(key)) {
            return termProbabilities[sentiment.ordinal()].get(key);
        }

        if (!dataSet.getFrequencies(sentiment).containsKey(key)) {
            return zeroTermProbability[sentiment.ordinal()];
        }

        int termFrequencyInSentiment = dataSet.getFrequencies(sentiment).get(key);

        BigDecimal probability = probability(sentiment, termFrequencyInSentiment);
        termProbabilities[sentiment.ordinal()].put(key, probability);
        return probability;
    }


    private BigDecimal probability(Sentiment sentiment, int termFrequencyInSentiment) {
        int totalTermCountInSentiment = dataSet.getTotalTerms(sentiment);
        int distinctTermCount = dataSet.distinctTermCount();

       return new BigDecimal(termFrequencyInSentiment + 1).divide(new BigDecimal(totalTermCountInSentiment + distinctTermCount), HALF_EVEN);
    }

}
