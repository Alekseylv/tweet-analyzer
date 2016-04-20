package edu.rtu.stl.analyzer;

import java.util.Collection;
import java.util.HashSet;

import edu.rtu.stl.domain.Document;
import edu.rtu.stl.parser.Tokenizer;

public class BernulliBayesAnalyzer extends BayesAnalyzer {

    public BernulliBayesAnalyzer(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Collection<String> tokenize(Document document) {
        return new HashSet<>(super.tokenize(document));
    }
}
