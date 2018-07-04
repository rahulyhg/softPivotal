package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/21/2017.
 */

public class NewCourseItem {
    String courseid;
    String coursename;
    String duration;
    String fastduration;
    String description;
    String courseimage;
    String fee;
    String disfee;
    String type;
    String colorCode;
    String learners;

    public NewCourseItem(String courseid, String coursename, String duration, String fastduration, String description, String courseimage, String fee, String disfee, String type, String colorCode, String learners) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.duration = duration;
        this.fastduration = fastduration;
        this.description = description;
        this.courseimage = courseimage;
        this.fee = fee;
        this.disfee = disfee;
        this.type = type;
        this.colorCode = colorCode;
        this.learners = learners;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFastduration() {
        return fastduration;
    }

    public void setFastduration(String fastduration) {
        this.fastduration = fastduration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseimage() {
        return courseimage;
    }

    public void setCourseimage(String courseimage) {
        this.courseimage = courseimage;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDisfee() {
        return disfee;
    }

    public void setDisfee(String disfee) {
        this.disfee = disfee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getLearners() {
        return learners;
    }

    public void setLearners(String learners) {
        this.learners = learners;
    }
}
