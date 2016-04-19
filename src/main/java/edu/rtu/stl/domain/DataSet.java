package edu.rtu.stl.domain;

import java.util.HashMap;
import java.util.Map;

public class DataSet {

    private final Map<String, Integer>[] wordCounts = new Map[Sentiment.values().length];
    {
        for (int i = 0; i < Sentiment.values().length; i++) {
            wordCounts[i] = new HashMap<>();
        }
    }

    public Map<String, Integer> getFrequencies(Sentiment sentiment) {
        return wordCounts[sentiment.ordinal()];
    }
}
