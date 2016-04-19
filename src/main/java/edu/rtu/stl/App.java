package edu.rtu.stl;

import static edu.rtu.stl.App.Sentiment.fromValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class App {
    public static void main(String[] args) throws IOException {
        Map<String, Integer>[] counts = new Map[Sentiment.values().length];

        for (int i = 0; i < Sentiment.values().length; i++) {
            counts[i] = new HashMap<>();
        }

        List<String> lines = Files.readAllLines(Paths.get("src", "main", "resources", "twitter_test_data_small.csv"));
        for (String line : lines) {
            List<String> lineItems = new ArrayList<>();
            for (String s : line.split("\"")) {
                if (!",".equals(s) && !s.trim().isEmpty()) {
                    lineItems.add(s);
                }
            }
            String[] parsedLine = lineItems.toArray(new String[lineItems.size()]);
            Sentiment sentiment = fromValue(parsedLine[0]);
            Map<String, Integer> frequencies = counts[sentiment.ordinal()];
            for (String s : parsedLine[5].split("\\W+")) {
                String key = s.toLowerCase().trim();
                if (!key.startsWith("@") && !key.isEmpty()) {
                    frequencies.put(key, frequencies.getOrDefault(key, 0) + 1);
                }
            }
        }

        for (int i = 0; i < Sentiment.values().length; i++) {
            System.out.println(Sentiment.values()[i]);
            List<Entry<String, Integer>> entries = new ArrayList<>(counts[i].entrySet());
            Collections.sort(entries, (x, y) -> - x.getValue().compareTo(y.getValue()));
            System.out.println(entries.subList(0, 50));
        }
    }

    public enum Sentiment {
        POSITIVE("4"), NEUTRAL("2"), NEGATIVE("0");

        public final String value;

        Sentiment(String value) {
            this.value = value;
        }

        public static Sentiment fromValue(String value) {
            for (Sentiment sentiment : Sentiment.values()) {
                if (sentiment.value.equals(value)) {
                    return sentiment;
                }
            }
            System.out.println(value);
            throw new IllegalArgumentException("Not a valid value");
        }
    }
}
