package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/26/2017.
 */

public class EventsItem {

    String eventid;
    String eventname;
    String venue;
    String eventdate;
    String description;
    String tentitiveDate;

    public EventsItem(String eventid, String eventname, String venue, String eventdate, String description, String tentitiveDate) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.venue = venue;
        this.eventdate = eventdate;
        this.description = description;
        this.tentitiveDate = tentitiveDate;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTentitiveDate() {
        return tentitiveDate;
    }

    public void setTentitiveDate(String tentitiveDate) {
        this.tentitiveDate = tentitiveDate;
    }
}
