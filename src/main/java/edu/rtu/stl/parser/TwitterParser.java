package edu.rtu.stl.parser;

import static edu.rtu.stl.domain.Sentiment.fromValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rtu.stl.domain.DataSet;
import edu.rtu.stl.domain.Sentiment;

public class TwitterParser implements Parser {
    @Override
    public DataSet parse(Path path) throws IOException {
        DataSet dataSet = new DataSet();
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            List<String> lineItems = new ArrayList<>();
            for (String s : line.split("\"")) {
                if (!",".equals(s) && !s.trim().isEmpty()) {
                    lineItems.add(s);
                }
            }
            String[] parsedLine = lineItems.toArray(new String[lineItems.size()]);
            Sentiment sentiment = fromValue(parsedLine[0]);
            Map<String, Integer> frequencies = dataSet.getFrequencies(sentiment);
            for (String s : parsedLine[5].split("\\W+")) {
                String key = s.toLowerCase().trim();
                if (!key.startsWith("@") && !key.isEmpty()) {
                    frequencies.put(key, frequencies.getOrDefault(key, 0) + 1);
                }
            }
        }
        return dataSet;
    }
}
