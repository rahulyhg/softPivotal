package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 10/18/2017.
 */

public class AttendenceItem {

    private String attendanceid;
    private String regno;
    private String batchid;
    private String totaldays;
    private String dayspresent;
    private String absentdays;
    private String batchcode;
    private String coursename;
    private String regdate;


    public AttendenceItem(String attendanceid, String regno, String batchid, String totaldays, String dayspresent, String absentdays, String batchcode, String coursename, String regdate) {
        this.attendanceid = attendanceid;
        this.regno = regno;
        this.batchid = batchid;
        this.totaldays = totaldays;
        this.dayspresent = dayspresent;
        this.absentdays = absentdays;
        this.batchcode = batchcode;
        this.coursename = coursename;
        this.regdate = regdate;
    }

    public String getAttendanceid() {
        return attendanceid;
    }

    public void setAttendanceid(String attendanceid) {
        this.attendanceid = attendanceid;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(String totaldays) {
        this.totaldays = totaldays;
    }

    public String getDayspresent() {
        return dayspresent;
    }

    public void setDayspresent(String dayspresent) {
        this.dayspresent = dayspresent;
    }

    public String getAbsentdays() {
        return absentdays;
    }

    public void setAbsentdays(String absentdays) {
        this.absentdays = absentdays;
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

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
