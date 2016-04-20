package edu.rtu.stl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rtu.stl.domain.DataSet;
import edu.rtu.stl.domain.Sentiment;
import edu.rtu.stl.parser.Parser;
import edu.rtu.stl.parser.TwitterParser;
import edu.rtu.stl.parser.ZipFileReader;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        Parser parser = new TwitterParser();
        ZipFileReader zipFileReader = new ZipFileReader();
        Path path = Paths.get("src", "main", "resources", "twitter_test_data_big_.csv.zip");
        DataSet dataSet = parser.parse(zipFileReader.readLines(path));

        for (int i = 0; i < Sentiment.values().length; i++) {
            Sentiment sentiment = Sentiment.values()[i];
            LOG.info(sentiment.name());
            List<Entry<String, Integer>> entries = new ArrayList<>(dataSet.getFrequencies(sentiment).entrySet());
            Collections.sort(entries, (x, y) -> - x.getValue().compareTo(y.getValue()));
            LOG.info(subList(entries, 50).toString());
        }
    }

    private static <T> List<T> subList(List<T> list, int n) {
        return list.subList(0, list.size() >= n ? 50 : list.size());
    }

}
