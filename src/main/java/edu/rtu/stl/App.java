package edu.rtu.stl;

import static edu.rtu.stl.domain.Sentiment.NEGATIVE;
import static edu.rtu.stl.domain.Sentiment.POSITIVE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rtu.stl.analyzer.BayesAnalyzer;
import edu.rtu.stl.classifier.BernulliNaiveBayesClassifier;
import edu.rtu.stl.classifier.Classifier;
import edu.rtu.stl.classifier.Classifier.Result;
import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.Sentiment;
import edu.rtu.stl.io.AllFileReader;
import edu.rtu.stl.io.FileReader;
import edu.rtu.stl.parser.ImdbReviewParser;
import edu.rtu.stl.parser.Parser;
import edu.rtu.stl.parser.Tokenizer;
import edu.rtu.stl.parser.UniqueTokenizer;
import edu.rtu.stl.report.HoldoutPartitioner;
import edu.rtu.stl.report.HoldoutPartitioner.HoldoutPartition;
import edu.rtu.stl.report.PrecisionRecallReportGenerator;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        Path base = Paths.get("src", "main", "resources");
        FileReader fileReader = new AllFileReader();

        Path stopWordPath = base.resolve("stopwords.txt");
        Set<String> stopWords = fileReader.readLines(stopWordPath).stream().map(x ->
                x.replaceAll("[^a-zA-Z ]", "").toLowerCase()).collect(Collectors.toSet());

        Tokenizer tokenizer = new Tokenizer(stopWords);
        Parser positiveParser = new ImdbReviewParser(POSITIVE);
        Parser negativeParser = new ImdbReviewParser(NEGATIVE);

        BayesAnalyzer bayesAnalyzer = new BayesAnalyzer(new UniqueTokenizer(stopWords));
        PrecisionRecallReportGenerator reportGenerator = new PrecisionRecallReportGenerator();
        HoldoutPartitioner holdoutPartitioner = new HoldoutPartitioner();

        Path positiveData = base.resolve("rt-polarity.pos");
        Path negativeData = base.resolve("rt-polarity.neg");

        List<Document> documents = ListUtils.union(
                positiveParser.parse(fileReader.readLines(positiveData)),
                negativeParser.parse(fileReader.readLines(negativeData))
        );
        HoldoutPartition data = holdoutPartitioner.partition(documents);

        Classifier naiveBayes = new BernulliNaiveBayesClassifier(bayesAnalyzer.analyze(data.learningSet), tokenizer);

        List<Result> classificationResults = data.testingSet.stream().map(naiveBayes::classify).collect(Collectors.toList());

        for (Sentiment sentiment : Sentiment.values()) {
            LOG.info(reportGenerator.generateFor(sentiment, classificationResults).toString());
        }

    }

}
