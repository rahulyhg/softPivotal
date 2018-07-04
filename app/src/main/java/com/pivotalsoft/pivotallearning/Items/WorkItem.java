package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 10/12/2017.
 */

public class WorkItem {
    private String workId;
    private String regno;
    private String batchId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String actualScore;
    private String givenScore;

    public WorkItem(String workId, String regno, String batchId, String title, String description, String startDate, String endDate, String actualScore, String givenScore) {
        this.workId = workId;
        this.regno = regno;
        this.batchId = batchId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.actualScore = actualScore;
        this.givenScore = givenScore;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActualScore() {
        return actualScore;
    }

    public void setActualScore(String actualScore) {
        this.actualScore = actualScore;
    }

    public String getGivenScore() {
        return givenScore;
    }

    public void setGivenScore(String givenScore) {
        this.givenScore = givenScore;
    }
}
