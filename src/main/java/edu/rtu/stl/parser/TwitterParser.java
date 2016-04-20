package edu.rtu.stl.parser;

import static edu.rtu.stl.domain.Sentiment.fromValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rtu.stl.domain.DataSet;
import edu.rtu.stl.domain.Sentiment;

public class TwitterParser implements Parser {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterParser.class);

    public final Tokenizer tokenizer;

    public TwitterParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

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
                LOG.error("Invalid line detected at {}. Line: {}", count, line);
                continue;
            }
            Sentiment sentiment = fromValue(parsedLine[0]);
            dataSet.incrementDocumentCount(sentiment);
            for (String key : tokenizer.tokenize(parsedLine[5])) {
                    dataSet.addTerm(sentiment, key);
            }
            if (count % 10000 == 0) {
                LOG.debug("Count {}", count);
            }
        }
        LOG.debug("Count {}", count);
        return dataSet;
    }
}
