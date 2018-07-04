package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class UpcomingBatchItem {
    String batchcode;
    String startdate;
    String batchtype;
    String description;
    String coursename;
    String courseid;
    String courseimage;
    String fee;
    String disfee;
    String colorcode;
    String learners;

    public UpcomingBatchItem(String batchcode, String startdate, String batchtype, String description, String coursename, String courseid, String courseimage, String fee, String disfee, String colorcode, String learners) {
        this.batchcode = batchcode;
        this.startdate = startdate;
        this.batchtype = batchtype;
        this.description = description;
        this.coursename = coursename;
        this.courseid = courseid;
        this.courseimage = courseimage;
        this.fee = fee;
        this.disfee = disfee;
        this.colorcode = colorcode;
        this.learners = learners;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getBatchtype() {
        return batchtype;
    }

    public void setBatchtype(String batchtype) {
        this.batchtype = batchtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
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

    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public String getLearners() {
        return learners;
    }

    public void setLearners(String learners) {
        this.learners = learners;
    }
}
