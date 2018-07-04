package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by USER on 2/7/2018.
 */

public class WalletItem {

   private String entryid;
    private String useremail;
    private String bonustype;
    private String bonusamount;
    private String status;
    private String description;
    private String bonusdate;

    public WalletItem(String entryid, String useremail, String bonustype, String bonusamount, String status, String description, String bonusdate) {
        this.entryid = entryid;
        this.useremail = useremail;
        this.bonustype = bonustype;
        this.bonusamount = bonusamount;
        this.status = status;
        this.description = description;
        this.bonusdate = bonusdate;
    }

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getBonustype() {
        return bonustype;
    }

    public void setBonustype(String bonustype) {
        this.bonustype = bonustype;
    }

    public String getBonusamount() {
        return bonusamount;
    }

    public void setBonusamount(String bonusamount) {
        this.bonusamount = bonusamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBonusdate() {
        return bonusdate;
    }

    public void setBonusdate(String bonusdate) {
        this.bonusdate = bonusdate;
    }
}
