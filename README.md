# tweet-analyzer

# Naive Bayes articles:
http://sebastianraschka.com/Articles/2014_naive_bayes_1.html
http://nlp.stanford.edu/IR-book/pdf/13bayes.pdf

Weka:
http://jmgomezhidalgo.blogspot.com.es/2013/06/baseline-sentiment-analysis-with-weka.html

Data tweets:
http://help.sentiment140.com/for-students

Data movies:
https://www.cs.cornell.edu/people/pabo/movie-review-data/


# Some Experimentation results

Multinomial bayes (Twitter CSV 1000) (time < 1 min):

[sentiment=POSITIVE,precision=58,recall=60]
[sentiment=NEGATIVE,precision=66,recall=64]

Bernulli bayes (Twitter CSV 1000) (time < 1 min):

[sentiment=POSITIVE,precision=57,recall=71]
[sentiment=NEGATIVE,precision=70,recall=55]

Weka J48 (Twitter CSV 1000) (time ~ 3 min):
[sentiment=POSITIVE,precision=58,recall=68]
[sentiment=NEGATIVE,precision=68,recall=58]

Weka Naive Bayes (Twitter CSV 1000) (time ~ 1 min)
[sentiment=POSITIVE,precision=59,recall=57]
[sentiment=NEGATIVE,precision=65,recall=67]

Weka Naive Bayes without NGrams (Twitter CSV 1000) (time ~ 1 min)
[sentiment=POSITIVE,precision=62,recall=63]
[sentiment=NEGATIVE,precision=68,recall=68]
