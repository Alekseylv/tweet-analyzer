package edu.rtu.stl.runner;

import java.util.List;

import edu.rtu.stl.classifier.Classifier;
import edu.rtu.stl.conf.ClassificationConfiguration;
import edu.rtu.stl.domain.Document;

public abstract class Runner {

    public final ClassificationConfiguration conf;
    public final Loader loader;

    public Runner(ClassificationConfiguration conf, Loader loader) {
        this.conf = conf;
        this.loader = loader;
    }

    Classifier trainClassifier(List<Document> documents) {
        return conf.getClassifier().trainClassifier(conf.getAnalyzer().analyze(documents));
    }

    abstract void run();
}
