package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class CoursesDetailsItem {

    String contentid;
    String courseid;
    String chapterno;
    String chaptername;
    String description;

    public CoursesDetailsItem(String contentid, String courseid, String chapterno, String chaptername, String description) {
        this.contentid = contentid;
        this.courseid = courseid;
        this.chapterno = chapterno;
        this.chaptername = chaptername;
        this.description = description;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getChapterno() {
        return chapterno;
    }

    public void setChapterno(String chapterno) {
        this.chapterno = chapterno;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
