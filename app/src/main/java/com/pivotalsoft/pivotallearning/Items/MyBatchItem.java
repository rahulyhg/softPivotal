package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 10/9/2017.
 */

public class MyBatchItem {

    String batchcode;
    String startdate;
    String batchtype;
    String description;
    String coursename;
    String courseid;
    String courseimage;


    public MyBatchItem(String batchcode, String startdate, String batchtype, String description, String coursename, String courseid, String courseimage) {
        this.batchcode = batchcode;
        this.startdate = startdate;
        this.batchtype = batchtype;
        this.description = description;
        this.coursename = coursename;
        this.courseid = courseid;
        this.courseimage = courseimage;
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
}
