package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 10/16/2017.
 */

public class StudenBatchItem {

    private String admid;
    private String regno;
    private String regdate;
    private String batchid;
    private String fee;
    private String batchcode;
    private String coursename;
    private String courseimage;
    private String colorcode;

    public StudenBatchItem(String admid, String regno, String regdate, String batchid, String fee, String batchcode, String coursename, String courseimage, String colorcode) {
        this.admid = admid;
        this.regno = regno;
        this.regdate = regdate;
        this.batchid = batchid;
        this.fee = fee;
        this.batchcode = batchcode;
        this.coursename = coursename;
        this.courseimage = courseimage;
        this.colorcode = colorcode;
    }

    public String getAdmid() {
        return admid;
    }

    public void setAdmid(String admid) {
        this.admid = admid;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseimage() {
        return courseimage;
    }

    public void setCourseimage(String courseimage) {
        this.courseimage = courseimage;
    }

    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }
}
