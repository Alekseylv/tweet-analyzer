package edu.rtu.stl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import edu.rtu.stl.domain.DataSet;
import edu.rtu.stl.domain.Sentiment;
import edu.rtu.stl.parser.Parser;
import edu.rtu.stl.parser.TwitterParser;

public class App {
    public static void main(String[] args) throws IOException {
        Parser parser = new TwitterParser();
        DataSet dataSet = parser.parse(Paths.get("src", "main", "resources", "twitter_test_data_small.csv"));

        for (int i = 0; i < Sentiment.values().length; i++) {
            Sentiment sentiment = Sentiment.values()[i];
            System.out.println(sentiment);
            List<Entry<String, Integer>> entries = new ArrayList<>(dataSet.getFrequencies(sentiment).entrySet());
            Collections.sort(entries, (x, y) -> - x.getValue().compareTo(y.getValue()));
            System.out.println(entries.subList(0, 50));
        }
    }

}
