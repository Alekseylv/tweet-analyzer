package edu.rtu.stl.parser;

import static edu.rtu.stl.domain.Sentiment.fromValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rtu.stl.domain.DataSet;
import edu.rtu.stl.domain.Sentiment;

public class TwitterParser implements Parser {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterParser.class);

    @Override
    public DataSet parse(List<String> lines) throws IOException {
        DataSet dataSet = new DataSet();
        int count = 0;
        for (String line : lines) {
            count++;
            List<String> lineItems = new ArrayList<>();
            for (String s : line.split("\"")) {
                if (!",".equals(s) && !s.trim().isEmpty()) {
                    lineItems.add(s);
                }
            }
            String[] parsedLine = lineItems.toArray(new String[lineItems.size()]);
            if (parsedLine.length < 6) {
                LOG.error("Invalid line detected at {}. Parsed line: {}", count, Arrays.asList(parsedLine));
                continue;
            }
            Sentiment sentiment = fromValue(parsedLine[0]);
            Map<String, Integer> frequencies = dataSet.getFrequencies(sentiment);
            for (String s : parsedLine[5].replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")) {
                String key = s.trim();
                if (!key.startsWith("@") && !key.isEmpty()) {
                    frequencies.put(key, frequencies.getOrDefault(key, 0) + 1);
                }
            }
            if (count % 1000 == 0) {
                LOG.debug("Count {}", count);
            }
        }
        return dataSet;
    }
}
