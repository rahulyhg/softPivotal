package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 10/17/2017.
 */

public class PaymentAnalaticItem {

    private String receiptno;
    private String paiddate;
    private String regno;
    private String batchid;
    private String amountpaid;
    private String amountdue;
    private String duedate;
    private String remarks;
    private String fullname;
    private String mobile;
    private String batchcode;
    private String coursename;
    private String staffId;

    public PaymentAnalaticItem(String receiptno, String paiddate, String regno, String batchid, String amountpaid, String amountdue, String duedate, String remarks, String fullname, String mobile, String batchcode, String coursename, String staffId) {
        this.receiptno = receiptno;
        this.paiddate = paiddate;
        this.regno = regno;
        this.batchid = batchid;
        this.amountpaid = amountpaid;
        this.amountdue = amountdue;
        this.duedate = duedate;
        this.remarks = remarks;
        this.fullname = fullname;
        this.mobile = mobile;
        this.batchcode = batchcode;
        this.coursename = coursename;
        this.staffId = staffId;
    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
    }

    public String getPaiddate() {
        return paiddate;
    }

    public void setPaiddate(String paiddate) {
        this.paiddate = paiddate;
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

    public String getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(String amountpaid) {
        this.amountpaid = amountpaid;
    }

    public String getAmountdue() {
        return amountdue;
    }

    public void setAmountdue(String amountdue) {
        this.amountdue = amountdue;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
