package edu.rtu.stl;

import static edu.rtu.stl.domain.Sentiment.NEGATIVE;
import static edu.rtu.stl.domain.Sentiment.POSITIVE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rtu.stl.analyzer.WekaAnalyzer;
import edu.rtu.stl.classifier.Classifier;
import edu.rtu.stl.classifier.Classifier.Result;
import edu.rtu.stl.classifier.WekaClassifier;
import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.Sentiment;
import edu.rtu.stl.io.AllFileReader;
import edu.rtu.stl.io.FileReader;
import edu.rtu.stl.parser.ImdbReviewParser;
import edu.rtu.stl.parser.Parser;
import edu.rtu.stl.parser.Tokenizer;
import edu.rtu.stl.parser.TwitterCSVParser;
import edu.rtu.stl.report.HoldoutPartitioner;
import edu.rtu.stl.report.HoldoutPartitioner.HoldoutPartition;
import edu.rtu.stl.report.PrecisionRecallReportGenerator;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        Path base = Paths.get("src", "main", "resources");
        FileReader fileReader = new AllFileReader();

        Path stopWordPath = base.resolve("stopwords.txt");
        Set<String> stopWords = fileReader.readLines(stopWordPath).stream().map(x ->
                x.replaceAll("[^a-zA-Z ]", "").toLowerCase()).collect(Collectors.toSet());

        Tokenizer tokenizer = new Tokenizer(stopWords);
        Parser positiveParser = new ImdbReviewParser(POSITIVE);
        Parser negativeParser = new ImdbReviewParser(NEGATIVE);

//        BayesAnalyzer bayesAnalyzer = new BayesAnalyzer(new UniqueTokenizer(stopWords));

        PrecisionRecallReportGenerator reportGenerator = new PrecisionRecallReportGenerator();
        HoldoutPartitioner holdoutPartitioner = new HoldoutPartitioner();

        Path positiveData = base.resolve("rt-polarity.pos");
        Path negativeData = base.resolve("rt-polarity.neg");

        Path dataPath = base.resolve("twitter_test_data_big_.csv.zip");

//        List<Document> documents = ListUtils.union(
//                positiveParser.parse(fileReader.readLines(positiveData)),
//                negativeParser.parse(fileReader.readLines(negativeData))
//        );

        List<Document> documents = new TwitterCSVParser().parse(fileReader.readLines(dataPath));
        HoldoutPartition data = holdoutPartitioner.partition(documents);

        WekaAnalyzer analyzer = new WekaAnalyzer();
        Classifier classifier = new WekaClassifier(wekaClassifier()).trainClassifier(analyzer.analyze(data.learningSet));

        List<Result> classificationResults = data.testingSet.stream().map(classifier::classify).collect(Collectors.toList());

        for (Sentiment sentiment : Sentiment.values()) {
            LOG.info(reportGenerator.generateFor(sentiment, classificationResults).toString());
        }

    }

    public static weka.classifiers.Classifier wekaClassifier() throws Exception {

        Ranker searchClass = new Ranker();
        searchClass.setThreshold(0.0);

        NGramTokenizer tokenizer = new NGramTokenizer();
        tokenizer.setDelimiters("\\s+");
        tokenizer.setNGramMaxSize(3);
        tokenizer.setNGramMinSize(1);

        StringToWordVector filter1 = new StringToWordVector();
//        filter1.setTokenizer(tokenizer);
        filter1.setWordsToKeep(10 * 1000 * 1000);
        filter1.setDoNotOperateOnPerClassBasis(true);

        AttributeSelection filter2 = new AttributeSelection();
        filter2.setEvaluator(new InfoGainAttributeEval());
        filter2.setSearch(searchClass);

        MultiFilter multiFilter = new MultiFilter();
        multiFilter.setFilters(new Filter[] {filter1});

        FilteredClassifier classifier = new FilteredClassifier();
        classifier.setFilter(multiFilter);
        classifier.setClassifier(new NaiveBayes());

        return classifier;
    }
}
