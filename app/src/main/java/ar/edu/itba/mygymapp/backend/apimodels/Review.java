package ar.edu.itba.mygymapp.backend.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("review")
    @Expose
    private String review;

    /**
     * No args constructor for use in serialization
     *
     */
    public Review() {
    }

    /**
     *
     * @param score
     * @param review
     */
    public Review(int score, String review) {
        super();
        this.score = score;
        this.review = review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
