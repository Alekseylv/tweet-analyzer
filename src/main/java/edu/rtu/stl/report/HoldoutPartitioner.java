package edu.rtu.stl.report;

import java.util.ArrayList;
import java.util.List;

import edu.rtu.stl.domain.Document;
import edu.rtu.stl.util.WithDefaultProperties;

public class HoldoutPartitioner implements WithDefaultProperties {

    private final int learningSetProportion = getLongProperty("hold.out.training.data.proportion", 70).intValue();

    public HoldoutPartition partition(List<Document> documents) {
        ArrayList<Document> docs = new ArrayList<>(documents);
        int partitionPoint = docs.size() * learningSetProportion / 100;
        List<Document> learningSet = docs.subList(0, partitionPoint);
        List<Document> testingSet = docs.subList(partitionPoint, docs.size());

        return new HoldoutPartition(learningSet, testingSet);
    }

    public static class HoldoutPartition {
        public final List<Document> trainingSet;
        public final List<Document> testingSet;

        public HoldoutPartition(List<Document> trainingSet, List<Document> testingSet) {
            this.trainingSet = trainingSet;
            this.testingSet = testingSet;
        }
    }
}
