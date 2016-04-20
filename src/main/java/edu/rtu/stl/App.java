package edu.rtu.stl;

import static edu.rtu.stl.domain.Sentiment.NEGATIVE;
import static edu.rtu.stl.domain.Sentiment.NEUTRAL;
import static edu.rtu.stl.domain.Sentiment.POSITIVE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rtu.stl.analyzer.BayesAnalyzer;
import edu.rtu.stl.classifier.Classifier.Result;
import edu.rtu.stl.classifier.NaiveBayes;
import edu.rtu.stl.domain.Document;
import edu.rtu.stl.io.AllFileReader;
import edu.rtu.stl.io.FileReader;
import edu.rtu.stl.parser.Parser;
import edu.rtu.stl.parser.Tokenizer;
import edu.rtu.stl.parser.TwitterCSVParser;
import edu.rtu.stl.report.PrecisionRecallReportGenerator;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new AllFileReader();

        Path stopWordPath = Paths.get("src", "main", "resources", "stopwords.txt");
        Set<String> stopWords = fileReader.readLines(stopWordPath).stream().map(x ->
                x.replaceAll("[^a-zA-Z ]", "").toLowerCase()).collect(Collectors.toSet());

        Tokenizer tokenizer = new Tokenizer(stopWords);
        Parser parser = new TwitterCSVParser();
        BayesAnalyzer bayesAnalyzer = new BayesAnalyzer(tokenizer);
        PrecisionRecallReportGenerator reportGenerator = new PrecisionRecallReportGenerator();

        Path learningDataPath = Paths.get("src", "main", "resources", "twitter_test_data_big_.csv.zip");
        NaiveBayes naiveBayes = new NaiveBayes(bayesAnalyzer.analyze(parser.parse(fileReader.readLines(learningDataPath))), tokenizer);


        Path testingDataPath = Paths.get("src", "main", "resources", "twitter_test_data_small.csv");
        List<Document> testingDocuments = parser.parse(fileReader.readLines(testingDataPath));
        List<Result> classificationResults = testingDocuments.stream().map(naiveBayes::classify).collect(Collectors.toList());

        LOG.info(reportGenerator.generateFor(POSITIVE, classificationResults).toString());
        LOG.info(reportGenerator.generateFor(NEGATIVE, classificationResults).toString());
        LOG.info(reportGenerator.generateFor(NEUTRAL, classificationResults).toString());
    }

    private static <T> List<T> subList(List<T> list, int n) {
        return list.subList(0, list.size() >= n ? 50 : list.size());
    }

}
