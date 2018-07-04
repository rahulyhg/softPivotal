package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class CourseVideosItem {

    String unitName;
    String topicName;

    public CourseVideosItem(String unitName, String topicName) {
        this.unitName = unitName;
        this.topicName = topicName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
