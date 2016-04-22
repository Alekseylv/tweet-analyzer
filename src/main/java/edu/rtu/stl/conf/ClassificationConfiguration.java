package edu.rtu.stl.conf;

import edu.rtu.stl.analyzer.Analyzer;
import edu.rtu.stl.classifier.Classifier;
import edu.rtu.stl.domain.ClassificationType;

public class ClassificationConfiguration<T> {

    private final ClassificationType type;
    private final Analyzer<T> analyzer;
    private final Classifier<T> classifier;

    public ClassificationConfiguration(ClassificationType type, Analyzer<T> analyzer, Classifier<T> classifier) {
        this.type = type;
        this.analyzer = analyzer;
        this.classifier = classifier;
    }

    public Analyzer<T> getAnalyzer() {
        return analyzer;
    }

    public Classifier<T> getClassifier() {
        return classifier;
    }

    public ClassificationType type() {
        return type;
    }
}
