package edu.rtu.stl.domain;

import edu.rtu.stl.util.WithWekaUtil;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class WekaDataSet implements WithWekaUtil {

    public WekaDataSet(int startSize) {
        this.attributes = new FastVector(2);
        this.attributes.addElement(new Attribute("text", (FastVector) null));
        this.classValues = new FastVector(startSize);
        this.setup = false;
    }

    private Instances trainingData;
    private FastVector classValues;
    private FastVector attributes;
    private boolean setup;

    public WekaDataSet addCategory(Sentiment sentiment) {
        int capacity = classValues.capacity();
        if (classValues.size() > (capacity - 5)) {
            classValues.setCapacity(capacity * 2);
        }
        classValues.addElement(sentiment.name().toLowerCase());
        return this;
    }

    public WekaDataSet buildAttributes() {
        attributes.addElement(new Attribute("classValueAttributeShouldBeUniqueSoDon'tChangeThis", classValues));
        trainingData = new Instances("MessageClassificationProblem", attributes, 100);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        setup = true;
        return this;
    }

    public WekaDataSet addData(String message, Sentiment sentiment) throws IllegalStateException {
        if (!setup) {
            throw new IllegalStateException("Must use setup first");
        }
        Instance instance = makeInstance(message.toLowerCase(), trainingData);
        instance.setClassValue(sentiment.name().toLowerCase());
        trainingData.add(instance);
        return this;
    }

    public Instances getTrainingData() {
        return trainingData;
    }
}
