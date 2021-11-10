package ar.edu.itba.mygymapp.backend.models;

public class Review {

    private int score; // int o double? revisar
    private String review;

    public Review(int score, String review) {
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