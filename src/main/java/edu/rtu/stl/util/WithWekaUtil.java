package edu.rtu.stl.util;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public interface WithWekaUtil {

    default Instance makeInstance(String text, Instances data) {
        Instance instance = new Instance(2);
        Attribute attribute = data.attribute("text");
        instance.setValue(attribute, attribute.addStringValue(text));
        instance.setDataset(data);
        return instance;
    }
}
