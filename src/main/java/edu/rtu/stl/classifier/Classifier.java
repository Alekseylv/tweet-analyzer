package edu.rtu.stl.classifier;

import edu.rtu.stl.domain.DomainObject;
import edu.rtu.stl.domain.Sentiment;

public interface Classifier {

    Result classify(String line);

    class Result extends DomainObject {
        public final Sentiment sentiment;
        public final double score;

        public Result(Sentiment sentiment, double score) {
            this.sentiment = sentiment;
            this.score = score;
        }
    }
}
