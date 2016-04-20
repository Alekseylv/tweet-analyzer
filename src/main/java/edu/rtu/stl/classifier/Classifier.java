package edu.rtu.stl.classifier;

import edu.rtu.stl.domain.Document;
import edu.rtu.stl.domain.DomainObject;
import edu.rtu.stl.domain.Sentiment;

public interface Classifier {

    Result classify(Document line);

    class Result extends DomainObject {
        public final Sentiment sentiment;
        public final double score;
        public final Document document;

        public Result(Sentiment sentiment, double score, Document document) {
            this.sentiment = sentiment;
            this.score = score;
            this.document = document;
        }
    }
}
