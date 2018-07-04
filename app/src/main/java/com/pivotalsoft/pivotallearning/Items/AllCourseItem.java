package com.pivotalsoft.pivotallearning.Items;

/**
 * Created by Gangadhar on 9/21/2017.
 */

public class AllCourseItem {

    int image;
    String courseName;
    String noOfLearners;
    String ratingBar;
    String courseFee;
    String DiscountFee;
    String nextBatch;

    public AllCourseItem(int image, String courseName, String noOfLearners, String ratingBar, String courseFee, String discountFee, String nextBatch) {
        this.image = image;
        this.courseName = courseName;
        this.noOfLearners = noOfLearners;
        this.ratingBar = ratingBar;
        this.courseFee = courseFee;
        DiscountFee = discountFee;
        this.nextBatch = nextBatch;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getNoOfLearners() {
        return noOfLearners;
    }

    public void setNoOfLearners(String noOfLearners) {
        this.noOfLearners = noOfLearners;
    }

    public String getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(String courseFee) {
        this.courseFee = courseFee;
    }

    public String getDiscountFee() {
        return DiscountFee;
    }

    public void setDiscountFee(String discountFee) {
        DiscountFee = discountFee;
    }

    public String getNextBatch() {
        return nextBatch;
    }

    public void setNextBatch(String nextBatch) {
        this.nextBatch = nextBatch;
    }
}
