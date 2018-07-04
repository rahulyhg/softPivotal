package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class OffersItem {

    String offerid;
    String offercode;
    String offername;
    String description;
    String startdate;
    String enddate;
    String isreferal;

    public OffersItem(String offerid, String offercode, String offername, String description, String startdate, String enddate, String isreferal) {
        this.offerid = offerid;
        this.offercode = offercode;
        this.offername = offername;
        this.description = description;
        this.startdate = startdate;
        this.enddate = enddate;
        this.isreferal = isreferal;
    }

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    public String getOffercode() {
        return offercode;
    }

    public void setOffercode(String offercode) {
        this.offercode = offercode;
    }

    public String getOffername() {
        return offername;
    }

    public void setOffername(String offername) {
        this.offername = offername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getIsreferal() {
        return isreferal;
    }

    public void setIsreferal(String isreferal) {
        this.isreferal = isreferal;
    }
}
