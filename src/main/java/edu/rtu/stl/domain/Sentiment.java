package edu.rtu.stl.domain;

public enum Sentiment {
    POSITIVE("4"), NEUTRAL("2"), NEGATIVE("0");

    public final String value;

    Sentiment(String value) {
        this.value = value;
    }

    public static Sentiment fromValue(String value) {
        for (Sentiment sentiment : Sentiment.values()) {
            if (sentiment.value.equals(value)) {
                return sentiment;
            }
        }
        System.out.println(value);
        throw new IllegalArgumentException("Not a valid value");
    }
}
