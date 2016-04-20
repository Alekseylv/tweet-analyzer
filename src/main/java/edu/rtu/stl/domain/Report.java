package edu.rtu.stl.domain;

import java.math.BigDecimal;

public class Report extends DomainObject {
    public final Sentiment sentiment;
    public final BigDecimal precision;
    public final BigDecimal recall;

    public Report(Sentiment sentiment, BigDecimal precision, BigDecimal recall) {
        this.sentiment = sentiment;
        this.precision = precision;
        this.recall = recall;
    }
}
