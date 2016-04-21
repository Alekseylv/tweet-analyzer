package edu.rtu.stl.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.rtu.stl.domain.Document;

public class HoldoutPartitioner {

    private final Random random = new Random(1L);
    private final int learningSetProportion = 70; // out of 100

    public HoldoutPartition partition(List<Document> documents) {
        ArrayList<Document> docs = new ArrayList<>(documents);
        Collections.shuffle(docs, random);

        docs = new ArrayList<>(docs.subList(0, 1000));

        int partitionPoint = docs.size() * learningSetProportion / 100;
        List<Document> learningSet = docs.subList(0, partitionPoint);
        List<Document> testingSet = docs.subList(partitionPoint, docs.size());

        return new HoldoutPartition(learningSet, testingSet);
    }

    public static class HoldoutPartition {
        public final List<Document> learningSet;
        public final List<Document> testingSet;

        public HoldoutPartition(List<Document> learningSet, List<Document> testingSet) {
            this.learningSet = learningSet;
            this.testingSet = testingSet;
        }
    }
}
