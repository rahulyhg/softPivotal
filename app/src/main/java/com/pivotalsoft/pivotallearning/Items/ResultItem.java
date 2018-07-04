package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/23/2017.
 */

public class ResultItem {

    String testName;
    String studyMode;
    String date;
    String time;
    String score;
    String timeTaken;
    String status;

    public ResultItem(String testName, String studyMode, String date, String time, String score, String timeTaken, String status) {
        this.testName = testName;
        this.studyMode = studyMode;
        this.date = date;
        this.time = time;
        this.score = score;
        this.timeTaken = timeTaken;
        this.status = status;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
